package com.yosoyvillaa.vutilities.objects;

import com.yosoyvillaa.vutilities.objects.enums.MsgStatus;
import com.yosoyvillaa.vutilities.objects.enums.StaffChatStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface User {

    UUID getUuid();
    void setUuid(UUID uuid);

    String getName();
    void setName(String name);

    MsgStatus getMsgStatus();
    void setMsgStatus(MsgStatus msgStatus);

    UUID getReplayTo();
    void setReplayTo(UUID replayTo);

    StaffChatStatus getStaffChatStatus();
    void setStaffChatStatus(StaffChatStatus staffChatStatus);

    LocalDateTime getNextLive();
    void setNextLive(LocalDateTime nextLive);

    boolean isStaffChatEnabled();
    void setStaffChatEnabled(boolean staffChatEnabled);

    boolean isAdminChatEnabled();
    void setAdminChatEnabled(boolean adminChatEnabled);

}
