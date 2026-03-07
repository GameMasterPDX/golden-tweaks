package io.github.poeticrainbow.goldentweaks.config.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ConfigScreen extends Screen {
    //private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 40, 40);
    private TweakButtonList list;

    public ConfigScreen() {
        super(Component.translatable("gui.goldentweaks.config"));
    }

    @Override
    protected void init() {
        list = new TweakButtonList(minecraft, width, height);

        //layout.addTitleHeader(getTitle(), getFont());
        //layout.addToContents(list);
        //layout.addToFooter(new Button.Builder(CommonComponents.GUI_DONE, button -> {
        //    Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(null));
        //}).build());
        //layout.arrangeElements();

        addRenderableWidget(list);
        addRenderableWidget(new StringWidget(getTitle(), getFont()));
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int i, int j, float f) {
        //renderMenuBackground(graphics);

        //layout.visitChildren(layoutElement -> layoutElement.visitWidgets(abstractWidget -> abstractWidget.render(graphics, i, j, f)));

        super.render(graphics, i, j, f);
    }
}
