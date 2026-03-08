package io.github.poeticrainbow.goldentweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.architectury.platform.Platform;
import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import io.github.poeticrainbow.goldentweaks.tweak.Tweak;
import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;

import java.io.*;
import java.nio.file.Path;

public class Config {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final Path CONFIG_PATH = Platform.getConfigFolder().resolve("goldentweaks.json");

    public static void save() {
        JsonObject obj = new JsonObject();

        for (Tweak<?> value : Tweaks.values()) {
            obj.add(value.key(), GSON.toJsonTree(value.get()));
        }

        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(obj, writer);
            GoldenTweaks.LOGGER.info("Successfully saved config to {}", CONFIG_PATH);
        } catch (IOException e) {
            GoldenTweaks.LOGGER.error("Could not save config file to path {}", CONFIG_PATH);
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        try {
            // todo: change this to load a default config from the resources in the jar
            try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                JsonObject obj = GSON.fromJson(reader, JsonObject.class);

                for (String key : obj.keySet()) {
                    Tweak<?> value = Tweaks.get(key);
                    if (value == null) continue;

                    try {
                        Object parsed = GSON.fromJson(obj.get(key), value.defaultValue().getClass());
                        ((Tweak<Object>) value).set(parsed);
                    } catch (JsonSyntaxException e) {
                        GoldenTweaks.LOGGER.error("Failed to create tweak for tweak config {}", key);
                    } catch (ExceptionInInitializerError e) {
                        GoldenTweaks.LOGGER.error("A severe issue occured while loading tweak {}:\n{}", key, e.getMessage());
                    }
                }

                GoldenTweaks.LOGGER.info("Successfully loaded config from {}", CONFIG_PATH);
            }
        } catch (IOException e) {
            GoldenTweaks.LOGGER.error("Could not read config file from path {}", CONFIG_PATH);
        }
    }
}
