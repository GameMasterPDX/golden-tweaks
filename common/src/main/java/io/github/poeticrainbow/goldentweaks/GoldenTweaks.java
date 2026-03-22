package io.github.poeticrainbow.goldentweaks;

import com.google.common.util.concurrent.ClosingFuture;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import io.github.poeticrainbow.goldentweaks.config.Config;
import io.github.poeticrainbow.goldentweaks.config.screen.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.literal;

public final class GoldenTweaks {
    public static final String MOD_ID = "goldentweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static TweakPlatform PLATFORM;
    public static final DeferredRegister<GameRule<?>> GAME_RULE = DeferredRegister.create(MOD_ID, Registries.GAME_RULE);

    // defining gamerules
    public static final DeferredSupplier<GameRule<Boolean>> FULLBLOCK_CHESTS = GAME_RULE.register("fullblock_chests", () -> new GameRule<>(
            GameRuleCategory.MISC,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            gameRuleValue -> gameRuleValue ? 1 : 0,
            false,
            FeatureFlagSet.of()
    ));
    public static final DeferredSupplier<GameRule<Boolean>> FULLBLOCK_FENCES = GAME_RULE.register("fullblock_fences", () -> new GameRule<>(
            GameRuleCategory.MISC,
            GameRuleType.BOOL,
            BoolArgumentType.bool(),
            GameRuleTypeVisitor::visitBoolean,
            Codec.BOOL,
            gameRuleValue -> gameRuleValue ? 1 : 0,
            false,
            FeatureFlagSet.of()
    ));

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

        GAME_RULE.register();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
