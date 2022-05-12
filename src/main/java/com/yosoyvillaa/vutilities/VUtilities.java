package com.yosoyvillaa.vutilities;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.yosoyvillaa.vutilities.module.MainModule;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "vutilities",
        name = "VUtilities",
        version = "1.0.0",
        description = "Essentials utilities plugin for Velocity",
        url = "https://twitter.com/YoSoyVillaa",
        authors = {"YoSoyVillaa"}
)
public class VUtilities {

    private final Logger logger;
    private final ProxyServer proxyServer;
    private final Path path;

    @Inject
    public VUtilities(Logger logger, ProxyServer proxyServer, @DataDirectory Path path) {
        this.logger = logger;
        this.proxyServer = proxyServer;
        this.path = path;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        loadDependencies();
        Injector injector = Guice.createInjector(new MainModule(this, path, proxyServer));
        injector.injectMembers(this);
        injector.injectMembers(path);
    }

    private void loadDependencies() {
        final VelocityLibraryManager<VUtilities> libraryManager =
                new VelocityLibraryManager<>(
                        logger,
                        path,
                        proxyServer.getPluginManager(),
                        this,
                        "libs");

        final Library yaml = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-yaml")
                .version("4.1.2")
                .id("configurate-yaml")
                .build();

        final Library confCore = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-core")
                .version("4.1.2")
                .id("configurate-core")
                .build();

        final Library geantyref = Library.builder()
                .groupId("io{}leangen{}geantyref")
                .artifactId("geantyref")
                .version("1.3.13")
                .id("geantyref")
                .build();

        final Library sqliteJdbc = Library.builder()
                .groupId("org{}xerial")
                .artifactId("sqlite-jdbc")
                .version("3.15.1")
                .id("sqlite-jdbc")
                .build();

        libraryManager.addMavenCentral();
        libraryManager.loadLibrary(yaml);
        libraryManager.loadLibrary(confCore);
        libraryManager.loadLibrary(geantyref);
        libraryManager.loadLibrary(sqliteJdbc);
    }
}
