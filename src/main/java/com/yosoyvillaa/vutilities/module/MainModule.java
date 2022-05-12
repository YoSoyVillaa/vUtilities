package com.yosoyvillaa.vutilities.module;

import com.google.inject.AbstractModule;
import com.velocitypowered.api.proxy.ProxyServer;
import com.yosoyvillaa.vutilities.VUtilities;
import com.yosoyvillaa.vutilities.file.FileMatcher;
import com.yosoyvillaa.vutilities.file.YAMLFile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

import java.nio.file.Path;
import java.util.Objects;

public class MainModule extends AbstractModule {

    private final VUtilities vUtilities;
    private final Path path;
    private final ProxyServer proxyServer;

    public MainModule(VUtilities vUtilities, Path path, ProxyServer proxyServer) {
        this.vUtilities = vUtilities;
        this.path = path;
        this.proxyServer = proxyServer;
    }


    @Override
    public void configure() {
        bind(VUtilities.class).toInstance(vUtilities);

        FileMatcher fileMatcher = new FileMatcher()
                .bind("config", new YAMLFile(vUtilities.getClass(), "config", ".yml", path))
                .bind("messages", new YAMLFile(vUtilities.getClass(), "messages", ".yml", path));
        install(fileMatcher.build());

        TagResolver prefixTagResolver = TagResolver.resolver(
                Placeholder.parsed(
                        "prefix",
                        Objects.requireNonNull(fileMatcher.get("messages").get().get("prefix").getString())));

        MiniMessage miniMessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(prefixTagResolver)
                        .resolver(StandardTags.defaults())
                        .build())
                .build();

        bind(MiniMessage.class).toInstance(miniMessage);
        install(new StorageModule(path));
    }
}
