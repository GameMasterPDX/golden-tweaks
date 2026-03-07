package io.github.poeticrainbow.goldentweaks.tweak;

public class Tweak<T> {
    private final String key;
    private final T defaultValue;
    private T value;

    public Tweak(String key, T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public T get() {
        return this.value;
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
        return "tweak." + this.key();
    }
}
