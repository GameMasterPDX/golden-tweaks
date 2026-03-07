package io.github.poeticrainbow.goldentweaks.config.screen;

import io.github.poeticrainbow.goldentweaks.tweak.Tweak;
import io.github.poeticrainbow.goldentweaks.tweak.types.BooleanTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class TweakButtonEntry extends ObjectSelectionList.Entry<@NotNull TweakButtonEntry> {
    public final Tweak<?> tweak;

    private final Button child;

    public TweakButtonEntry(Tweak<?> tweak) {
        this.tweak = tweak;
        this.child = Button.builder(
            getMessage(), button -> {
                if (tweak instanceof BooleanTweak bTweak) bTweak.toggle();
                updateMessage();
                Minecraft.getInstance().levelRenderer.allChanged();
            }
        ).bounds(0, 0, 220, 20).build();
    }

    @Override
    public boolean mouseClicked(@NotNull MouseButtonEvent mouseButtonEvent, boolean bl) {
        return child.mouseClicked(mouseButtonEvent, bl);
    }

    public Component getMessage() {
        return Component.literal("")
                        .append(getNarration())
                        .append(": " + tweak.get());
    }

    public void updateMessage() {
        child.setMessage(getMessage());
    }

    @Override
    public @NotNull Component getNarration() {
        return Component.translatable(tweak.translationKey());
    }

    @Override
    public void renderContent(@NotNull GuiGraphics graphics, int i, int j, boolean bl, float f) {
        child.setPosition(getX(), getY());
        child.render(graphics, i, j, f);
        //graphics.textRenderer().accept(TextAlignment.LEFT, i, j, this.getNarration());
    }
}
