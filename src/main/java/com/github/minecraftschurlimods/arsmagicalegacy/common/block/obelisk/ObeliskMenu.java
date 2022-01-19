package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;

public class ObeliskMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;

    public ObeliskMenu(int id, Inventory inv) {
        this(id, inv, new SimpleContainer(1), new SimpleContainerData(4));
    }

    public ObeliskMenu(int containerId, Inventory inventory, Container container, ContainerData data) {
        super(AMMenuTypes.OBELISK.get(), containerId);
        this.container = container;
        this.data = data;

        addDataSlots(data);
        addSlot(new ObeliskFuelSlot(container, 79, 47));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public int getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}