package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class DragPane extends AbstractContainerEventHandler implements NarratableEntry, Widget, DragHandler {
    private final List<DropArea> dropAreas = new ArrayList<>();
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private Draggable dragged;
    private DropArea dragSource;

    public DragPane(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, SkillIconAtlas.SKILL_ICON_ATLAS);
        for (DropArea dropArea : dropAreas) {
            dropArea.render(poseStack, mouseX, mouseY, partialTicks);
        }
        if (getDragged() != null) {
            getDragged().render(poseStack, mouseX, mouseY, partialTicks);
        }
        poseStack.popPose();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
        setDragged(null, null);
        return true;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if (mouseX < x || mouseY < y) return false;
        int w = x + width;
        int h = y + height;
        return w < x && h < y || w > mouseX && h < y || w < x && h > mouseY || w > mouseX && h > mouseY;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return dropAreas;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public GuiEventListener getFocused() {
        return getDragged();
    }

    /**
     * Adds a new drop area to this pane.
     *
     * @param dropArea The drop area to add.
     * @param <T>      The type of the drop area-
     * @return The given drop area, with the required drag handlers set on ít.
     */
    public <T extends DropArea> T addDropArea(T dropArea) {
        dropAreas.add(dropArea);
        dropArea.setDragHandler(this);
        return dropArea;
    }

    @Override
    public Draggable getDragged() {
        return dragged;
    }

    @Override
    public void setDragged(@Nullable Draggable draggable, @Nullable DropArea source) {
        dragged = draggable;
        dragSource = source;
    }

    @Override
    public void returnToSource() {
        dragSource.add(dragged);
        dragged.setVisible(false);
        dragged = null;
    }
}
