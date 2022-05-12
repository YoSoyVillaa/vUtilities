package com.yosoyvillaa.vutilities.objects;

import com.velocitypowered.api.proxy.ProxyServer;
import com.yosoyvillaa.vutilities.objects.enums.MsgStatus;
import com.yosoyvillaa.vutilities.objects.enums.StaffChatStatus;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.UUID;

public class DefaultUser implements User {

    private UUID uuid;
    private String name;
    private MsgStatus msgStatus;
    private UUID replayTo;
    private StaffChatStatus staffChatStatus;
    private LocalDateTime nextLive;
    private boolean staffChatEnabled;
    private boolean adminChatEnabled;

    @ConstructorProperties({"uuid", "name", "msgStatus", "replayTo", "staffChatStatus",
                    "nextLive", "staffChatEnabled", "adminChatEnabled"})
    public DefaultUser(UUID uuid, String name, MsgStatus msgStatus, UUID replayTo,
                       StaffChatStatus staffChatStatus, LocalDateTime nextLive,
                       boolean staffChatEnabled, boolean adminChatEnabled) {
        this.uuid = uuid;
        this.name = name;
        this.msgStatus = msgStatus;
        this.replayTo = replayTo;
        this.staffChatStatus = staffChatStatus;
        this.nextLive = nextLive;
        this.staffChatEnabled = staffChatEnabled;
        this.adminChatEnabled = adminChatEnabled;
    }

    public DefaultUser(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.msgStatus = MsgStatus.ENABLED;
        this.replayTo = null;
        this.staffChatStatus = StaffChatStatus.NONE;
        this.nextLive = LocalDateTime.now();
        this.staffChatEnabled = true;
        this.adminChatEnabled = true;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public MsgStatus getMsgStatus() {
        return msgStatus;
    }

    @Override
    public void setMsgStatus(MsgStatus msgStatus) {
        this.msgStatus = msgStatus;
    }

    @Override
    public UUID getReplayTo() {
        return replayTo;
    }

    @Override
    public void setReplayTo(UUID replayTo) {
        this.replayTo = replayTo;
    }

    @Override
    public StaffChatStatus getStaffChatStatus() {
        return staffChatStatus;
    }

    @Override
    public void setStaffChatStatus(StaffChatStatus staffChatStatus) {
        this.staffChatStatus = staffChatStatus;
    }

    @Override
    public LocalDateTime getNextLive() {
        return nextLive;
    }

    @Override
    public void setNextLive(LocalDateTime nextLive) {
        this.nextLive = nextLive;
    }

    @Override
    public boolean isStaffChatEnabled() {
        return staffChatEnabled;
    }

    @Override
    public void setStaffChatEnabled(boolean staffChatEnabled) {
        this.staffChatEnabled = staffChatEnabled;
    }

    @Override
    public boolean isAdminChatEnabled() {
        return adminChatEnabled;
    }

    @Override
    public void setAdminChatEnabled(boolean adminChatEnabled) {
        this.adminChatEnabled = adminChatEnabled;
    }
}
