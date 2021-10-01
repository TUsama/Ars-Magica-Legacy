package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.AMSpawnEggItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.ColoredRuneItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.WizardsChalkItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ITEMS;

@NonExtendable
public interface AMItems {
    CreativeModeTab GROUP = new CreativeModeTab(ArsMagicaAPI.MOD_ID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };
    Item.Properties ITEM_64 = new Item.Properties().stacksTo(64).tab(GROUP);
    Item.Properties ITEM_1 = new Item.Properties().stacksTo(1).tab(GROUP);

    RegistryObject<BlockItem> CHIMERITE_ORE = ITEMS.register("chimerite_ore", () -> new BlockItem(AMBlocks.CHIMERITE_ORE.get(), ITEM_64));
    RegistryObject<BlockItem> DEEPSLATE_CHIMERITE_ORE = ITEMS.register("deepslate_chimerite_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), ITEM_64));
    RegistryObject<Item> CHIMERITE = ITEMS.register("chimerite", () -> new Item(ITEM_64));
    RegistryObject<BlockItem> CHIMERITE_BLOCK = ITEMS.register("chimerite_block", () -> new BlockItem(AMBlocks.CHIMERITE_BLOCK.get(), ITEM_64));
    RegistryObject<BlockItem> TOPAZ_ORE = ITEMS.register("topaz_ore", () -> new BlockItem(AMBlocks.TOPAZ_ORE.get(), ITEM_64));
    RegistryObject<BlockItem> DEEPSLATE_TOPAZ_ORE = ITEMS.register("deepslate_topaz_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), ITEM_64));
    RegistryObject<Item> TOPAZ = ITEMS.register("topaz", () -> new Item(ITEM_64));
    RegistryObject<BlockItem> TOPAZ_BLOCK = ITEMS.register("topaz_block", () -> new BlockItem(AMBlocks.TOPAZ_BLOCK.get(), ITEM_64));
    RegistryObject<BlockItem> VINTEUM_ORE = ITEMS.register("vinteum_ore", () -> new BlockItem(AMBlocks.VINTEUM_ORE.get(), ITEM_64));
    RegistryObject<BlockItem> DEEPSLATE_VINTEUM_ORE = ITEMS.register("deepslate_vinteum_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), ITEM_64));
    RegistryObject<Item> VINTEUM_DUST = ITEMS.register("vinteum_dust", () -> new Item(ITEM_64));
    RegistryObject<BlockItem> VINTEUM_BLOCK = ITEMS.register("vinteum_block", () -> new BlockItem(AMBlocks.VINTEUM_BLOCK.get(), ITEM_64));
    RegistryObject<BlockItem> MOONSTONE_ORE = ITEMS.register("moonstone_ore", () -> new BlockItem(AMBlocks.MOONSTONE_ORE.get(), ITEM_64));
    RegistryObject<BlockItem> DEEPSLATE_MOONSTONE_ORE = ITEMS.register("deepslate_moonstone_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), ITEM_64));
    RegistryObject<Item> MOONSTONE = ITEMS.register("moonstone", () -> new Item(ITEM_64));
    RegistryObject<BlockItem> MOONSTONE_BLOCK = ITEMS.register("moonstone_block", () -> new BlockItem(AMBlocks.MOONSTONE_BLOCK.get(), ITEM_64));
    RegistryObject<BlockItem> SUNSTONE_ORE = ITEMS.register("sunstone_ore", () -> new BlockItem(AMBlocks.SUNSTONE_ORE.get(), ITEM_64));
    RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone", () -> new Item(ITEM_64));
    RegistryObject<BlockItem> SUNSTONE_BLOCK = ITEMS.register("sunstone_block", () -> new BlockItem(AMBlocks.SUNSTONE_BLOCK.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_LOG = ITEMS.register("witchwood_log", () -> new BlockItem(AMBlocks.WITCHWOOD_LOG.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD = ITEMS.register("witchwood", () -> new BlockItem(AMBlocks.WITCHWOOD.get(), ITEM_64));
    RegistryObject<BlockItem> STRIPPED_WITCHWOOD_LOG = ITEMS.register("stripped_witchwood_log", () -> new BlockItem(AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), ITEM_64));
    RegistryObject<BlockItem> STRIPPED_WITCHWOOD = ITEMS.register("stripped_witchwood", () -> new BlockItem(AMBlocks.STRIPPED_WITCHWOOD.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_LEAVES = ITEMS.register("witchwood_leaves", () -> new BlockItem(AMBlocks.WITCHWOOD_LEAVES.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_SAPLING = ITEMS.register("witchwood_sapling", () -> new BlockItem(AMBlocks.WITCHWOOD_SAPLING.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_PLANKS = ITEMS.register("witchwood_planks", () -> new BlockItem(AMBlocks.WITCHWOOD_PLANKS.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_SLAB = ITEMS.register("witchwood_slab", () -> new BlockItem(AMBlocks.WITCHWOOD_SLAB.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_STAIRS = ITEMS.register("witchwood_stairs", () -> new BlockItem(AMBlocks.WITCHWOOD_STAIRS.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_FENCE = ITEMS.register("witchwood_fence", () -> new BlockItem(AMBlocks.WITCHWOOD_FENCE.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_FENCE_GATE = ITEMS.register("witchwood_fence_gate", () -> new BlockItem(AMBlocks.WITCHWOOD_FENCE_GATE.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", () -> new DoubleHighBlockItem(AMBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_TRAPDOOR = ITEMS.register("witchwood_trapdoor", () -> new BlockItem(AMBlocks.WITCHWOOD_TRAPDOOR.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_BUTTON = ITEMS.register("witchwood_button", () -> new BlockItem(AMBlocks.WITCHWOOD_BUTTON.get(), ITEM_64));
    RegistryObject<BlockItem> WITCHWOOD_PRESSURE_PLATE = ITEMS.register("witchwood_pressure_plate", () -> new BlockItem(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get(), ITEM_64));
    RegistryObject<Item> BLANK_RUNE = ITEMS.register("blank_rune", () -> new Item(ITEM_64));
    RegistryObject<ColoredRuneItem> WHITE_RUNE = ITEMS.register("white_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.WHITE));
    RegistryObject<ColoredRuneItem> ORANGE_RUNE = ITEMS.register("orange_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.ORANGE));
    RegistryObject<ColoredRuneItem> MAGENTA_RUNE = ITEMS.register("magenta_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.MAGENTA));
    RegistryObject<ColoredRuneItem> LIGHT_BLUE_RUNE = ITEMS.register("light_blue_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.LIGHT_BLUE));
    RegistryObject<ColoredRuneItem> YELLOW_RUNE = ITEMS.register("yellow_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.YELLOW));
    RegistryObject<ColoredRuneItem> LIME_RUNE = ITEMS.register("lime_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.LIME));
    RegistryObject<ColoredRuneItem> PINK_RUNE = ITEMS.register("pink_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.PINK));
    RegistryObject<ColoredRuneItem> GRAY_RUNE = ITEMS.register("gray_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.GRAY));
    RegistryObject<ColoredRuneItem> LIGHT_GRAY_RUNE = ITEMS.register("light_gray_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.LIGHT_GRAY));
    RegistryObject<ColoredRuneItem> CYAN_RUNE = ITEMS.register("cyan_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.CYAN));
    RegistryObject<ColoredRuneItem> PURPLE_RUNE = ITEMS.register("purple_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.PURPLE));
    RegistryObject<ColoredRuneItem> BLUE_RUNE = ITEMS.register("blue_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.BLUE));
    RegistryObject<ColoredRuneItem> BROWN_RUNE = ITEMS.register("brown_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.BROWN));
    RegistryObject<ColoredRuneItem> GREEN_RUNE = ITEMS.register("green_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.GREEN));
    RegistryObject<ColoredRuneItem> RED_RUNE = ITEMS.register("red_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.RED));
    RegistryObject<ColoredRuneItem> BLACK_RUNE = ITEMS.register("black_rune", () -> new ColoredRuneItem(ITEM_64, DyeColor.BLACK));
    RegistryObject<RuneBagItem> RUNE_BAG = ITEMS.register("rune_bag", () -> new RuneBagItem(ITEM_1));
    RegistryObject<Item> ARCANE_COMPOUND = ITEMS.register("arcane_compound", () -> new Item(ITEM_64));
    RegistryObject<Item> ARCANE_ASH = ITEMS.register("arcane_ash", () -> new Item(ITEM_64));
    RegistryObject<Item> PURIFIED_VINTEUM_DUST = ITEMS.register("purified_vinteum_dust", () -> new Item(ITEM_64));
    RegistryObject<BlockItem> AUM = ITEMS.register("aum", () -> new BlockItem(AMBlocks.AUM.get(), ITEM_64));
    RegistryObject<BlockItem> CERUBLOSSOM = ITEMS.register("cerublossom", () -> new BlockItem(AMBlocks.CERUBLOSSOM.get(), ITEM_64));
    RegistryObject<BlockItem> DESERT_NOVA = ITEMS.register("desert_nova", () -> new BlockItem(AMBlocks.DESERT_NOVA.get(), ITEM_64));
    RegistryObject<BlockItem> TARMA_ROOT = ITEMS.register("tarma_root", () -> new BlockItem(AMBlocks.TARMA_ROOT.get(), ITEM_64));
    RegistryObject<BlockItem> WAKEBLOOM = ITEMS.register("wakebloom", () -> new BlockItem(AMBlocks.WAKEBLOOM.get(), ITEM_64));
    RegistryObject<StandingAndWallBlockItem> VINTEUM_TORCH = ITEMS.register("vinteum_torch", () -> new StandingAndWallBlockItem(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64));
    RegistryObject<WizardsChalkItem> WIZARDS_CHALK = ITEMS.register("wizards_chalk", () -> new WizardsChalkItem(ITEM_64.durability(100)));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}

    /**
     * Initializes the spawn eggs
     */
    static void initSpawnEggs() {
        ITEMS.getEntries()
                .stream()
                .flatMap(RegistryObject::stream)
                .filter(AMSpawnEggItem.class::isInstance)
                .map(AMSpawnEggItem.class::cast)
                .forEach(AMSpawnEggItem::init);
    }
}