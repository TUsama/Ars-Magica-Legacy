package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;

public record BEClientSyncPacket(BlockPos pos, CompoundTag tag) implements IPacket {
    public BEClientSyncPacket(BlockEntity blockEntity) {
        this(blockEntity.getBlockPos(), blockEntity.getUpdateTag());
    }

    public BEClientSyncPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readNbt());
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> Optional.ofNullable(Minecraft.getInstance().level).map(level -> level.getBlockEntity(pos)).ifPresent(blockEntity -> blockEntity.load(tag())));
    }
}
