package com.example.particletoggle;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Client-side per-particle filters; change config and restart the game. */
public class ParticleToggle implements ClientModInitializer {
    private static final Path CONFIG = Path.of("config", "particle-toggle.properties");
    private static final Properties SETTINGS = new Properties();

    @Override
    public void onInitializeClient() {
        try {
            Files.createDirectories(CONFIG.getParent());
            if (Files.exists(CONFIG)) {
                try (var input = Files.newInputStream(CONFIG)) { SETTINGS.load(input); }
            }
            SETTINGS.putIfAbsent("default", "true");
            SETTINGS.putIfAbsent("totem_of_undying", "true");
            SETTINGS.putIfAbsent("block", "false");
            SETTINGS.putIfAbsent("block_marker", "false");
            SETTINGS.putIfAbsent("dust_pillar", "false");
            // List every registered particle ID in the config so each can be toggled.
            for (var particleType : Registries.PARTICLE_TYPE) {
                Identifier id = Registries.PARTICLE_TYPE.getId(particleType);
                if (id != null) SETTINGS.putIfAbsent(id.toString(),
                    SETTINGS.getProperty(id.getPath(), SETTINGS.getProperty("default", "true")));
            }
            try (var output = Files.newOutputStream(CONFIG)) {
                SETTINGS.store(output, "Particle Toggle: true = show, false = hide; restart Minecraft after edits. IDs are minecraft:... or modded namespaces.");
            }
        } catch (Exception exception) {
            System.err.println("Particle Toggle couldn't read/write its configuration: " + exception);
        }
    }

    public static boolean allowed(ParticleEffect effect) {
        if (effect == null) return true;
        Identifier id = Registries.PARTICLE_TYPE.getId(effect.getType());
        if (id == null) return true;
        String value = SETTINGS.getProperty(id.toString(),
                SETTINGS.getProperty(id.getPath(), SETTINGS.getProperty("default", "true")));
        return Boolean.parseBoolean(value);
    }
}
