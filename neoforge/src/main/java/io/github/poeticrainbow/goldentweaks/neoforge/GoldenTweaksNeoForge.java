package io.github.poeticrainbow.goldentweaks.neoforge;

import io.github.poeticrainbow.goldentweaks.ErrorCollector;
import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import io.github.poeticrainbow.goldentweaks.TweakPlatform;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.config.NeoForgeClientConfig;
import net.neoforged.neoforge.client.event.ClientResourceLoadFinishedEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Optional;

@Mod(GoldenTweaks.MOD_ID)
public final class GoldenTweaksNeoForge implements TweakPlatform {
    public GoldenTweaksNeoForge() {
        // Run our common setup.
        GoldenTweaks.PLATFORM = this;
        // the platform MUST be set before initializing for config loading
        GoldenTweaks.init();

        ErrorCollector.addErrorCheck(() -> {
            if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
                return Optional.of("NeoForge's enhancedLighting is enabled, breaking Per Face Lighting");
            }
            return Optional.empty();
        });

        NeoForge.EVENT_BUS.addListener(GoldenTweaksNeoForge::onResourceReload);
    }

    private static void onResourceReload(ClientResourceLoadFinishedEvent event) {
        if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
            GoldenTweaks.LOGGER.error("NeoForge's enhancedLighting is enabled in your neoforge-client.toml config file. The old lighting calculations will not work!");
        }
    }

    @Override
    public boolean isVanillaAo() {
        // return true when enhancedLighting is NOT enabled
        return !NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean();
    }
}
