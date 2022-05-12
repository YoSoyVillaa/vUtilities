package com.yosoyvillaa.vutilities.connection;

import java.io.File;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteConnection implements Connection {

    private final Path dataDirectory;

    private java.sql.Connection connection;

    public SQLiteConnection(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    @Override
    public void connect() {
        File database = new File(dataDirectory.toFile(), "database.db");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        createTables();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public java.sql.Connection get() {
        return connection;
    }

    public void createTables() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `uuid` VARCHAR(36) NOT NULL," +
                "  `name` VARCHAR(16) NOT NULL," +
                "  `msg_status` VARCHAR(8) NOT NULL," +
                "  `replay_to` VARCHAR(36) DEFAULT NULL," +
                "  `staff_chat_status` VARCHAR(10) NOT NULL," +
                "  `next_live` TIMESTAMP NOT NULL," +
                "  `staff_chat_enabled` BOOLEAN NOT NULL," +
                "  `admin_chat_enabled` BOOLEAN NOT NULL" +
                ");"
        )) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
