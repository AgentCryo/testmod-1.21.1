package com.agentcryo.block;

import com.agentcryo.Testmod;
import com.agentcryo.block.custom.*;
import com.agentcryo.world.tree.ModSaplingGenerators;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block SHINY_BLOCK = registerBlock("shiny_block",
    new Block(AbstractBlock.Settings.create()
            .strength(1.5f)
            .requiresTool()
            .sounds(BlockSoundGroup.AMETHYST_BLOCK)
    ));

    public static final Block SHINY_ORE = registerBlock("shiny_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.STONE)));

    public static final Block SHINY_DEEPSLATE_ORE = registerBlock("shiny_deepslate_ore",
            new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)));

    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
    new MagicBlock(AbstractBlock.Settings.create()
            .strength(2f)
            .requiresTool()
            .sounds(BlockSoundGroup.AMETHYST_BLOCK)
    ));

    public static final Block COUCH = registerBlock("couch",
            new CouchBlock(AbstractBlock.Settings.copy(Blocks.RED_WOOL).nonOpaque()));

    public static final Block DRILL_BLOCK = registerBlock("drill",
            new DrillBlock(AbstractBlock.Settings.create()
                    .strength(1.5f, 6.0f)
                    .requiresTool()));

    public static final Block FORGE_BLOCK = registerBlock("forge_block",
            new ForgeBlock(AbstractBlock.Settings.create()
                    .strength(1.5f, 6.0f)
                    .requiresTool()));

    public static final Block REFINERY_BLOCK = registerBlock("refiner_block",
            new RefineryBlock(AbstractBlock.Settings.create()
                    .strength(1.5f, 6.0f)
                    .requiresTool()));

    public static final Block FRAME = registerBlock("frame",
            new Block(AbstractBlock.Settings.create()
                    .strength(1.5f, 6.0f)
                    .requiresTool()
                    .nonOpaque()
            ));

    public static final Block GULM_WOOD_LOG = registerBlock("gulm_wood_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                    .strength(3.5f)
                    .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block GULM_WOOD_WOOD = registerBlock("gulm_wood_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                    .strength(3.5f)
                    .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block STRIPPED_GULM_WOOD_LOG = registerBlock("stripped_gulm_wood_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                    .strength(3.5f)
                    .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block STRIPPED_GULM_WOOD_WOOD = registerBlock("stripped_gulm_wood_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                    .strength(3.5f)
                    .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block GULM_WOOD_SAPLING = registerBlock("gulm_wood_sapling",
            new SaplingBlock(ModSaplingGenerators.GULM_WOOD, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

    public static final Block GULM_WOOD_LEAVES = registerBlock("gulm_wood_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
            ));

    public static final Block GULM_WOOD_PLANKS = registerBlock("gulm_wood_planks",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block GULM_WOOD_STAIRS = registerBlock("gulm_wood_stairs",
            new StairsBlock(ModBlocks.GULM_WOOD_PLANKS.getDefaultState(),
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block GULM_WOOD_SLAB = registerBlock("gulm_wood_slab",
            new SlabBlock(
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block GULM_WOOD_BUTTON = registerBlock("gulm_wood_button",
            new ButtonBlock(BlockSetType.OAK, 2,
                    AbstractBlock.Settings.create()
                            .strength(0.2f)
                            .noCollision()
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block GULM_WOOD_PRESSURE_PLATE = registerBlock("gulm_wood_pressure_plate",
            new PressurePlateBlock(BlockSetType.IRON,
                    AbstractBlock.Settings.create()
                            .strength(0.5f)
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block GULM_WOOD_FENCE= registerBlock("gulm_wood_fence",
            new FenceBlock(
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block GULM_WOOD_FENCE_GATE = registerBlock("gulm_wood_fence_gate",
            new FenceGateBlock(WoodType.OAK,
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block GULM_WOOD_WALL = registerBlock("gulm_wood_wall",
            new WallBlock(
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block GULM_WOOD_DOOR = registerBlock("gulm_wood_door",
            new DoorBlock(BlockSetType.OAK,
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .nonOpaque()
                            .sounds(BlockSoundGroup.WOOD)
            ));
    public static final Block GULM_WOOD_TRAP_DOOR = registerBlock("gulm_wood_trap_door",
            new TrapdoorBlock(BlockSetType.OAK,
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .nonOpaque()
                            .sounds(BlockSoundGroup.WOOD)
            ));

    public static final Block SHINY_LAMP = registerBlock("shiny_lamp",
            new ShinyLampBlock(AbstractBlock.Settings.create()
                    .strength(1f)
                    .requiresTool()
                    .luminance(state -> state.get(ShinyLampBlock.CLICKED) ? 15 : 0 )
            ));

    public static final Block AMARANTH_CROP = registerBlockWithOutItem("amaranth_crop",
            new AmaranthCropBlock(AbstractBlock.Settings.create()
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .pistonBehavior(PistonBehavior.DESTROY)
            ));

    public static final Block CLOUD_BERRY_BUSH = registerBlockWithOutItem("cloud_berry_bush",
            new CloudBerryBushBlock(AbstractBlock.Settings.create()
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .pistonBehavior(PistonBehavior.DESTROY)
            ));

    private static Block registerBlockWithOutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(Testmod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Testmod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Testmod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Testmod.LOGGER.info("Registering Mod Blocks for " + Testmod.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
        //    entries.add(ModBlocks.SHINY_BLOCK);
        //});
    }
}
