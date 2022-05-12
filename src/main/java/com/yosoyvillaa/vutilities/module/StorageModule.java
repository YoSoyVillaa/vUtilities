package com.yosoyvillaa.vutilities.module;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yosoyvillaa.vutilities.file.YAMLFile;
import com.yosoyvillaa.vutilities.storage.SQLiteStorageProvider;
import com.yosoyvillaa.vutilities.storage.StorageProvider;

import java.nio.file.Path;

public class StorageModule extends AbstractModule {

    @Inject @Named("config") private YAMLFile yamlFile;
    private final Path dataDirectory;

    public StorageModule(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    @Override
    public void configure() {
        SQLiteStorageProvider sqLiteStorageProvider = new SQLiteStorageProvider(dataDirectory);
        sqLiteStorageProvider.init();
        bind(StorageProvider.class).toInstance(sqLiteStorageProvider);
    }
}
