package com.github.minecraftschurli.arsmagicalegacy.common.magic;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurli.codeclib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public final class MagicHelper implements IMagicHelper {
    private static final Lazy<MagicHelper> INSTANCE = Lazy.concurrentOf(MagicHelper::new);

    private MagicHelper() {
    }

    public static MagicHelper instance() {
        return INSTANCE.get();
    }

    private static final Capability<MagicHolder> MAGIC = CapabilityManager.get(new CapabilityToken<>() {});
    private static final Capability<ManaHolder> MANA = CapabilityManager.get(new CapabilityToken<>() {});
    private static final Capability<BurnoutHolder> BURNOUT = CapabilityManager.get(new CapabilityToken<>() {});

    public static void init() {
        ArsMagicaLegacy.NETWORK_HANDLER.register(ManaSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        ArsMagicaLegacy.NETWORK_HANDLER.register(BurnoutSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        ArsMagicaLegacy.NETWORK_HANDLER.register(MagicSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static Capability<MagicHolder> getMagicCapability() {
        return MAGIC;
    }

    public static Capability<ManaHolder> getManaCapability() {
        return MANA;
    }

    public static Capability<BurnoutHolder> getBurnoutCapability() {
        return BURNOUT;
    }

    private MagicHolder getMagicHolder(Player player) {
        return player.getCapability(MAGIC)
                     .orElseThrow(() -> new RuntimeException("Could not retrieve magic capability for player %s{%s}".formatted(
                             player.getDisplayName().getString(),
                             player.getUUID())));
    }

    private ManaHolder getManaHolder(LivingEntity livingEntity) {
        return livingEntity.getCapability(MANA).orElseThrow(
                () -> new RuntimeException("Could not retrieve mana capability for LivingEntity %s{%s}".formatted(
                        livingEntity.getDisplayName().getString(),
                        livingEntity.getUUID())));
    }

    private BurnoutHolder getBurnoutHolder(LivingEntity livingEntity) {
        return livingEntity.getCapability(BURNOUT).orElseThrow(
                () -> new RuntimeException(("Could not retrieve burnout capability for LivingEntity %s{%s}").formatted(
                        livingEntity.getDisplayName().getString(),
                        livingEntity.getUUID())));
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
    public void awardXp(Player player, float amount) {
        var magicHolder = getMagicHolder(player);
        var n = magicHolder.getXp() + amount;
        var l = magicHolder.getLevel();
        while (true) {
            var xpForNextLevel = getXpForNextLevel(l);
            if (n < xpForNextLevel) {
                break;
            }
            n -= xpForNextLevel;
            l++;
            MinecraftForge.EVENT_BUS.post(new PlayerLevelUpEvent(player, l));
        }
        magicHolder.setXp(n);
        magicHolder.setLevel(l);
        syncMagic(player);
    }

    private float getXpForNextLevel(int level) {
        if (level == 0) return 0;
        // TODO change
        return Byte.MAX_VALUE;
    }

    @Override
    public float getMana(LivingEntity livingEntity) {
        return getManaHolder(livingEntity).getMana();
    }

    @Override
    public float getMaxMana(LivingEntity livingEntity) {
        return (float) livingEntity.getAttributeValue(AMAttributes.MAX_MANA.get());
    }

    @Override
    public boolean increaseMana(LivingEntity livingEntity, float amount) {
        if (amount < 0) {
            return false;
        }
        var max = getMaxMana(livingEntity);
        var magicHolder = getManaHolder(livingEntity);
        var current = magicHolder.getMana();
        magicHolder.setMana(Math.min(current + amount, max));
        if (livingEntity instanceof Player player) {
            syncMana(player);
        }
        return true;
    }

    @Override
    public boolean decreaseMana(LivingEntity livingEntity, float amount) {
        if (amount < 0) {
            return false;
        }
        var magicHolder = getManaHolder(livingEntity);
        var current = magicHolder.getMana();
        if (current - amount < 0) {
            return false;
        }
        magicHolder.setMana(current - amount);
        if (livingEntity instanceof Player player) {
            syncMana(player);
        }
        return true;
    }

    @Override
    public float getBurnout(LivingEntity livingEntity) {
        return getBurnoutHolder(livingEntity).getBurnout();
    }

    @Override
    public float getMaxBurnout(LivingEntity livingEntity) {
        return (float) livingEntity.getAttributeValue(AMAttributes.MAX_BURNOUT.get());
    }

    @Override
    public boolean increaseBurnout(LivingEntity livingEntity, float amount) {
        if (amount < 0) {
            return false;
        }
        var max = getMaxBurnout(livingEntity);
        var magicHolder = getBurnoutHolder(livingEntity);
        var current = magicHolder.getBurnout();
        magicHolder.setBurnout(Math.min(current + amount, max));
        if (livingEntity instanceof Player player){
            syncBurnout(player);
        }
        return true;
    }

    @Override
    public boolean decreaseBurnout(LivingEntity livingEntity, float amount) {
        if (amount < 0) {
            return false;
        }
        var magicHolder = getBurnoutHolder(livingEntity);
        var current = magicHolder.getBurnout();
        if (current - amount < 0) {
            return false;
        }
        magicHolder.setBurnout(current - amount);
        if (livingEntity instanceof Player player){
            syncBurnout(player);
        }
        return true;
    }

    @Override
    public boolean knowsMagic(Player player) {
        boolean b = player.isCreative() || player.isSpectator();
        if (player.isDeadOrDying()) return b;
        return b || getLevel(player) > 0;
    }

    public void syncOnDeath(Player original, Player player) {
        original.getCapability(MANA).ifPresent(manaHolder -> player.getCapability(MANA).ifPresent(holder -> holder.onSync(manaHolder)));
        original.getCapability(BURNOUT).ifPresent(burnoutHolder -> player.getCapability(BURNOUT).ifPresent(holder -> holder.onSync(burnoutHolder)));
        original.getCapability(MAGIC).ifPresent(magicHolder -> player.getCapability(MAGIC).ifPresent(holder -> holder.onSync(magicHolder)));
    }

    private void syncBurnout(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new BurnoutSyncPacket(getBurnoutHolder(player)), player);
    }

    public static final class BurnoutSyncPacket extends CodecPacket<BurnoutHolder> {

        public BurnoutSyncPacket(BurnoutHolder data) {
            super(data);
        }

        public BurnoutSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            MagicHelper.handleBurnoutSync(this.data, context);
        }

        @Override
        protected Codec<BurnoutHolder> getCodec() {
            return BurnoutHolder.CODEC;
        }
    }

    private static void handleBurnoutSync(BurnoutHolder data, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(BURNOUT)
                                                                .ifPresent(holder -> holder.onSync(data)));
    }

    private void syncMana(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new ManaSyncPacket(getManaHolder(player)), player);
    }

    public static final class ManaSyncPacket extends CodecPacket<ManaHolder> {

        public ManaSyncPacket(ManaHolder data) {
            super(data);
        }

        public ManaSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            MagicHelper.handleManaSync(this.data, context);
        }

        @Override
        protected Codec<ManaHolder> getCodec() {
            return ManaHolder.CODEC;
        }
    }

    private static void handleManaSync(ManaHolder data, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(MANA)
                                                                .ifPresent(holder -> holder.onSync(data)));
    }

    private void syncMagic(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new MagicSyncPacket(getMagicHolder(player)), player);
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
            MagicHelper.handleMagicSync(this.data, context);
        }

        @Override
        protected Codec<MagicHolder> getCodec() {
            return MagicHolder.CODEC;
        }
    }

    private static void handleMagicSync(MagicHolder data, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(MAGIC)
                                                                .ifPresent(holder -> holder.onSync(data)));
    }

    public void syncAllToPlayer(Player player) {
        syncMagic(player);
        syncMana(player);
        syncBurnout(player);
    }

    public static final class ManaHolder {
        public static final Codec<ManaHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.fieldOf("mana").forGetter(ManaHolder::getMana)
        ).apply(inst, mana -> {
            var manaHolder = new ManaHolder();
            manaHolder.setMana(mana);
            return manaHolder;
        }));

        private float mana;

        public float getMana() {
            return mana;
        }

        public void setMana(float amount) {
            this.mana = amount;
        }

        public void onSync(ManaHolder data) {
            this.mana = data.mana;
        }
    }

    public static final class BurnoutHolder {
        public static final Codec<BurnoutHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.fieldOf("burnout").forGetter(BurnoutHolder::getBurnout)
        ).apply(inst, burnout -> {
            var manaHolder = new BurnoutHolder();
            manaHolder.setBurnout(burnout);
            return manaHolder;
        }));

        private float burnout;

        public float getBurnout() {
            return burnout;
        }

        public void setBurnout(float amount) {
            this.burnout = amount;
        }

        public void onSync(BurnoutHolder data) {
            this.burnout = data.burnout;
        }
    }

    public static class MagicHolder {
        public static final Codec<MagicHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.fieldOf("xp")
                           .forGetter(MagicHolder::getXp),
                Codec.INT.fieldOf("level")
                         .forGetter(MagicHolder::getLevel)
        ).apply(inst, (xp, level) -> {
            var magicHolder = new MagicHolder();
            magicHolder.setXp(xp);
            magicHolder.setLevel(level);
            return magicHolder;
        }));

        private float xp;
        private int   level;

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

        public void onSync(MagicHolder data) {
            this.xp = data.xp;
            this.level = data.level;
        }
    }
}
