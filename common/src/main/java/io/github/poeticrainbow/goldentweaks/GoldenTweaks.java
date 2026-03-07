package io.github.poeticrainbow.goldentweaks;

import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import io.github.poeticrainbow.goldentweaks.config.Config;
import io.github.poeticrainbow.goldentweaks.config.screen.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.literal;

public final class GoldenTweaks {
    public static final String MOD_ID = "goldentweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.

        Config.load();
        Config.save();

        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> {
            dispatcher.register(
                literal("goldentweaks")
                    .executes(ctx -> {
                        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new ConfigScreen()));
                        return 1;
                    })
            );
        });
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
