package io.github.poeticrainbow.retrotweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;

import java.io.*;
import java.nio.file.Path;

public class Config {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final Path COMMON_CONFIG_PATH = Platform.getConfigFolder().resolve("retrotweaks-common.json");
    public static final Path CLIENT_CONFIG_PATH = Platform.getConfigFolder().resolve("retrotweaks-client.json");

    public static void init() {
        load(COMMON_CONFIG_PATH);
        if (Platform.getEnvironment() == Env.CLIENT) {
            load(CLIENT_CONFIG_PATH);
        }
        saveAll();
    }

    public static void saveAll() {
        save(COMMON_CONFIG_PATH, Env.SERVER);
        if (Platform.getEnvironment() == Env.CLIENT) {
            save(CLIENT_CONFIG_PATH, Env.CLIENT);
        }
    }

    public static void save(Path configPath, Env logicalSide) {
        JsonObject config = new JsonObject();
        Tweaks.values().forEach(tweak -> {
            if (tweak.logicalSide().equals(logicalSide)) {
                config.add(tweak.key(), tweak.encode().getOrThrow());
            }
        });
        try (Writer writer = new FileWriter(configPath.toFile())) {
            GSON.toJson(config, writer);
            RetroTweaks.LOGGER.info("Successfully saved config to {}", configPath);
        } catch (IOException e) {
            RetroTweaks.LOGGER.error("Could not save config file to path {}", configPath);
        }
    }

    public static void load(Path configPath) {
        try {
            try (Reader reader = new FileReader(configPath.toFile())) {
                JsonObject obj = GSON.fromJson(reader, JsonObject.class);

                for (String key : obj.keySet()) {
                    var tweak = Tweaks.get(key);
                    if (tweak == null) continue;

                    var value = obj.get(key);
                    tweak.decode(value);
                }

                RetroTweaks.LOGGER.info("Successfully loaded config from {}", configPath);
            }
        } catch (IOException e) {
            RetroTweaks.LOGGER.error("Could not read config file from path {}", configPath);
        }
    }
}
