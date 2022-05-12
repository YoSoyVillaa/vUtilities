package com.yosoyvillaa.vutilities.storage;

import com.yosoyvillaa.vutilities.connection.SQLiteConnection;
import com.yosoyvillaa.vutilities.objects.DefaultUser;
import com.yosoyvillaa.vutilities.objects.User;
import com.yosoyvillaa.vutilities.objects.enums.MsgStatus;
import com.yosoyvillaa.vutilities.objects.enums.StaffChatStatus;

import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.UUID;

public class SQLiteStorageProvider implements StorageProvider {

    private final Path dataDirectory;
    private SQLiteConnection sqLiteConnection;

    public SQLiteStorageProvider(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    @Override
    public void init() {
        sqLiteConnection = new SQLiteConnection(dataDirectory);
        sqLiteConnection.connect();
        System.out.println("Connected to SQLite database");
    }

    @Override
    public void save(User user) {
        try (PreparedStatement preparedStatement = sqLiteConnection.get().prepareStatement(
                "INSERT INTO users " +
                        "(uuid, name, msg_status, staff_chat_status, next_live, staff_chat_enabled, admin_chat_enabled) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)"
        )){
            preparedStatement.setString(1, user.getUuid().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setObject(3, user.getMsgStatus());
            preparedStatement.setObject(4, user.getStaffChatStatus());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(user.getNextLive()));
            preparedStatement.setBoolean(6, user.isStaffChatEnabled());
            preparedStatement.setBoolean(7, user.isAdminChatEnabled());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (PreparedStatement preparedStatement = sqLiteConnection.get().prepareStatement(
                "UPDATE users " +
                        "SET msg_status = ?, staff_chat_status = ?, next_live = ?, staff_chat_enabled = ?, admin_chat_enabled = ? " +
                        "WHERE uuid = ?")) {
            preparedStatement.setObject(1, user.getMsgStatus());
            preparedStatement.setObject(2, user.getStaffChatStatus());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(user.getNextLive()));
            preparedStatement.setBoolean(4, user.isStaffChatEnabled());
            preparedStatement.setBoolean(5, user.isAdminChatEnabled());
            preparedStatement.setString(6, user.getUuid().toString());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(UUID uuid) {
        try (PreparedStatement preparedStatement = sqLiteConnection.get().prepareStatement(
                "SELECT * FROM users WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());

            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User get(UUID uuid) {
        try (PreparedStatement preparedStatement = sqLiteConnection.get().prepareStatement(
                "SELECT * FROM users WHERE uuid = ?")) {
            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new DefaultUser(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getObject("msg_status", MsgStatus.class),
                        UUID.fromString(resultSet.getString("replay_to")),
                        resultSet.getObject("staff_chat_status", StaffChatStatus.class),
                        resultSet.getTimestamp("next_live").toLocalDateTime(),
                        resultSet.getBoolean("staff_chat_enabled"),
                        resultSet.getBoolean("admin_chat_enabled"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
