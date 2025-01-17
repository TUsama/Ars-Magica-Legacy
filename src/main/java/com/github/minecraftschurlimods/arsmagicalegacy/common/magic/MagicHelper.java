package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkEvent;

public final class MagicHelper implements IMagicHelper {
    private static final Lazy<MagicHelper> INSTANCE = Lazy.concurrentOf(MagicHelper::new);
    private static final Capability<MagicHolder> MAGIC = CapabilityManager.get(new CapabilityToken<>() {
    });

    private MagicHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static MagicHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The magic capability.
     */
    public static Capability<MagicHolder> getMagicCapability() {
        return MAGIC;
    }

    private static void handleMagicSync(MagicHolder holder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(MAGIC).ifPresent(cap -> cap.onSync(holder)));
    }

    @Override
    public int getLevel(Player player) {
        return getMagicHolder(player).getLevel();
    }

    @Override
    public float getXp(Player player) {
        return getMagicHolder(player).getXp();
    }

    @Override
    public float getXpForNextLevel(int level) {
        return level == 0 ? 0 : Config.SERVER.LEVELING_MULTIPLIER.get().floatValue() * (float) Math.pow(Config.SERVER.LEVELING_BASE.get().floatValue(), level);
    }

    @Override
    public void awardXp(Player player, float amount) {
        setXp(player, getXp(player) + amount);
    }

    @Override
    public void setXp(Player player, float amount) {
        MagicHolder magicHolder = getMagicHolder(player);
        int level = magicHolder.getLevel();
        float xp = Math.max(0, amount);
        float xpForNextLevel = getXpForNextLevel(level);
        while (xp >= xpForNextLevel) {
            xp -= xpForNextLevel;
            level++;
            MinecraftForge.EVENT_BUS.post(new PlayerLevelUpEvent(player, level));
            xpForNextLevel = getXpForNextLevel(level);
        }
        magicHolder.setXp(xp);
        magicHolder.setLevel(level);
        syncMagic(player);
    }

    @Override
    public void awardLevel(Player player, int level) {
        setLevel(player, getLevel(player) + level);
    }

    @Override
    public void setLevel(Player player, int level) {
        MagicHolder magicHolder = getMagicHolder(player);
        int oldLevel = magicHolder.getLevel();
        magicHolder.setLevel(level);
        if (level > oldLevel && level > 0) {
            for (int i = oldLevel + 1; i <= level; i++) {
                MinecraftForge.EVENT_BUS.post(new PlayerLevelUpEvent(player, i));
            }
        }
        syncMagic(player);
    }

    @Override
    public boolean knowsMagic(Player player) {
        return player.isCreative() || player.isSpectator() || getLevel(player) > 0;
    }

    /**
     * Called on player death, syncs the capability.
     *
     * @param original The now-dead player.
     * @param player   The respawning player.
     */
    public void syncOnDeath(Player original, Player player) {
        original.getCapability(MAGIC).ifPresent(magicHolder -> player.getCapability(MAGIC).ifPresent(holder -> holder.onSync(magicHolder)));
        syncMagic(player);
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncMagic(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new MagicSyncPacket(getMagicHolder(player)), player);
    }

    private MagicHolder getMagicHolder(Player player) {
        if (player.isDeadOrDying()) {
            player.reviveCaps();
        }
        MagicHolder magicHolder = player.getCapability(MAGIC).orElseThrow(() -> new RuntimeException("Could not retrieve magic capability for player %s{%s}".formatted(player.getDisplayName().getString(), player.getUUID())));
        if (player.isDeadOrDying()) {
            player.invalidateCaps();
        }
        return magicHolder;
    }

    public static final class MagicSyncPacket extends CodecPacket<MagicHolder> {
        public MagicSyncPacket(MagicHolder data) {
            super(data);
        }

        public MagicSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            MagicHelper.handleMagicSync(data, context);
        }

        @Override
        protected Codec<MagicHolder> getCodec() {
            return MagicHolder.CODEC;
        }
    }

    public static class MagicHolder {
        public static final Codec<MagicHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.FLOAT.fieldOf("xp").forGetter(MagicHolder::getXp), Codec.INT.fieldOf("level").forGetter(MagicHolder::getLevel)).apply(inst, (xp, level) -> {
            MagicHolder magicHolder = new MagicHolder();
            magicHolder.setXp(xp);
            magicHolder.setLevel(level);
            return magicHolder;
        }));
        private float xp;
        private int level;

        public float getXp() {
            return xp;
        }

        public void setXp(float xp) {
            this.xp = xp;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        /**
         * Syncs the values with the given data object.
         *
         * @param data The data object to sync with.
         */
        public void onSync(MagicHolder data) {
            xp = data.xp;
            level = data.level;
        }
    }
}
