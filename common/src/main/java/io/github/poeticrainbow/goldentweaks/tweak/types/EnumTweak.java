package io.github.poeticrainbow.goldentweaks.tweak.types;

import io.github.poeticrainbow.goldentweaks.tweak.Tweak;

import java.util.List;

public class EnumTweak<T extends Enum<T>> extends Tweak<Enum<T>> {
    private final List<T> values;

    public EnumTweak(String key, Enum<T> defaultValue) {
        super(key, defaultValue);
        this.values = List.of(defaultValue.getDeclaringClass().getEnumConstants());
    }

    public void next() {
        var index = values.indexOf(this.get());
        var newValue = values.get((index + 1) % values.size());
        this.set(newValue);
    }
}
