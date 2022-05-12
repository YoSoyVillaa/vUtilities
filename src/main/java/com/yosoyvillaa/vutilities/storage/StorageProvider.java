package com.yosoyvillaa.vutilities.storage;

import com.yosoyvillaa.vutilities.objects.User;

import java.util.UUID;

public interface StorageProvider {

    void init();

    void save(User user);

    void update(User user);

    boolean exists(UUID uuid);

    User get(UUID uuid);
}
