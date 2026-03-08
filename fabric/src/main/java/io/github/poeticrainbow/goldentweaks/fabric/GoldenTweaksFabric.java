package io.github.poeticrainbow.goldentweaks.fabric;

import io.github.poeticrainbow.goldentweaks.ErrorCollector;
import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import io.github.poeticrainbow.goldentweaks.TweakPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.client.indigo.Indigo;
import net.fabricmc.fabric.impl.client.indigo.renderer.aocalc.AoConfig;

import java.util.Optional;

public final class GoldenTweaksFabric implements ModInitializer, TweakPlatform {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        GoldenTweaks.PLATFORM = this;
        // the platform MUST be set before initializing for config loading
        GoldenTweaks.init();

        ErrorCollector.addErrorCheck(() -> {
            if (Indigo.AMBIENT_OCCLUSION_MODE != AoConfig.VANILLA) {
                return Optional.of("ambient-occlusion-mode in fabric/indigo-renderer.properties is not set to vanilla");
            }
            return Optional.empty();
        });

        if (Indigo.AMBIENT_OCCLUSION_MODE != AoConfig.VANILLA) {
            GoldenTweaks.LOGGER.error("ambient-occlusion-mode in config/fabric/indigo-renderer.properties is set to {}! It should be set to \"vanilla\" in order to have old-school lighting!", Indigo.AMBIENT_OCCLUSION_MODE);
        }
    }

    @Override
    public boolean isVanillaAo() {
        return Indigo.AMBIENT_OCCLUSION_MODE == AoConfig.VANILLA;
    }
}
