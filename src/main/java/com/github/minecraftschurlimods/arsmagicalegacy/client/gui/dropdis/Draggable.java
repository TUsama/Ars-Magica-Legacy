package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

import java.util.List;

public class Draggable extends AbstractContainerEventHandler implements Widget, NarratableEntry {
    private final Component name;
    private final TextureAtlasSprite sprite;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean visible = true;
    private double dragOffsetX;
    private double dragOffsetY;
    private boolean dragOffsetSet;

    public Draggable(int x, int y, int width, int height, TextureAtlasSprite sprite, Component name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.name = name;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!dragOffsetSet) {
            dragOffsetX = mouseX - x;
            dragOffsetY = mouseY - y;
            dragOffsetSet = true;
        }
        this.x = (int) (mouseX - this.dragOffsetX);
        this.y = (int) (mouseY - this.dragOffsetY);
        return true;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (!isVisible()) return;
        poseStack.pushPose();
        if (RenderSystem.getShaderTexture(0) != Minecraft.getInstance().getTextureManager().getTexture(sprite.atlas().location()).getId()) {
            RenderSystem.setShaderTexture(0, sprite.atlas().location());
        }
        blit(poseStack, x, y, 10, width, height, sprite);
        poseStack.popPose();
    }

    /**
     * Renders the tooltip of the given pose stack.
     *
     * @param poseStack The pose stack to render the tooltip of.
     * @param x         The x to render the tooltip at.
     * @param y         The y to render the tooltip at.
     */
    public void renderTooltip(PoseStack poseStack, int x, int y) {
        Minecraft.getInstance().screen.renderTooltip(poseStack, name, x, y);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if (mouseX < x || mouseY < y) return false;
        int w = x + width - 1;
        int h = y + height - 1;
        return w < x && h < y || w > mouseX && h < y || w < x && h > mouseY || w > mouseX && h > mouseY;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, name);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
