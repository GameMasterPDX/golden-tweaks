package io.github.poeticrainbow.goldentweaks.tweak.types;

import io.github.poeticrainbow.goldentweaks.tweak.Tweak;

public class BooleanTweak extends Tweak<Boolean> {
    public BooleanTweak(String key, Boolean defaultValue) {
        super(key, defaultValue);
    }

    public void toggle() {
        this.set(!this.get());
    }
}
