package io.github.poeticrainbow.retrotweaks.tweak.types;

import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;

import java.util.function.Supplier;

public class Tweak<T> {
    private final String key;
    private final T defaultValue;
    private T value;
    private final Supplier<Boolean> isFunctional;
    private final Env logicalSide;

    public static final String ERROR_TOOLTIP = "retrotweaks.error_tooltip";

    public Tweak(String key, T defaultValue) {
        this(key, Env.SERVER, defaultValue, () -> Boolean.TRUE); // default true for all tweaks, functional
    }

    public Tweak(String key, Env logicalSide, T defaultValue) {
        this(key, logicalSide, defaultValue, () -> Boolean.TRUE); // default true for all tweaks, functional
    }

    public Tweak(String key, T defaultValue, Supplier<Boolean> isFunctional) {
        this(key, Env.SERVER, defaultValue, isFunctional);
    }

    public Tweak(String key, Env logicalSide, T defaultValue, Supplier<Boolean> isFunctional) {
        this.key = key;
        this.logicalSide = logicalSide;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.isFunctional = isFunctional;
    }

    public T get() {
        return switch (logicalSide) {
            // clientside tweaks are always controlled by the client
            case CLIENT -> this.value;
            // singleplayer or dedicated server (tweak host) : get the value from the current dedicated server (tweak client/guest)
            case SERVER -> RetroTweaks.isLogicalSide() ? this.value : defaultValue;
        };
    }

    public void set(T value) {
        this.value = value;
    }

    public String key() {
        return this.key;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

    public String translationKey() {
        return "retrotweaks.tweak." + this.key();
    }

    public String tooltip() {
        return isFunctional() ? this.translationKey() + ".tooltip" : ERROR_TOOLTIP;
    }

    public boolean isFunctional() {
        return isFunctional.get();
    }
}
