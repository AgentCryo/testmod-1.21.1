package com.agentcryo.datagen;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.block.custom.AmaranthCropBlock;
import com.agentcryo.block.custom.CloudBerryBushBlock;
import com.agentcryo.block.custom.ShinyLampBlock;
import com.agentcryo.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHINY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGIC_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHINY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHINY_DEEPSLATE_ORE);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.COUCH);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.DRILL_BLOCK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.FORGE_BLOCK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.REFINERY_BLOCK);

        blockStateModelGenerator.registerLog(ModBlocks.GULM_WOOD_LOG).log(ModBlocks.GULM_WOOD_LOG).wood(ModBlocks.GULM_WOOD_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_GULM_WOOD_LOG).log(ModBlocks.STRIPPED_GULM_WOOD_LOG).wood(ModBlocks.STRIPPED_GULM_WOOD_WOOD);
        blockStateModelGenerator.registerSingleton(ModBlocks.GULM_WOOD_LEAVES, TexturedModel.LEAVES);
        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.GULM_WOOD_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        BlockStateModelGenerator.BlockTexturePool gulmWoodPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.GULM_WOOD_PLANKS);
        gulmWoodPool.stairs(ModBlocks.GULM_WOOD_STAIRS);
        gulmWoodPool.slab(ModBlocks.GULM_WOOD_SLAB);
        gulmWoodPool.button(ModBlocks.GULM_WOOD_BUTTON);
        gulmWoodPool.pressurePlate(ModBlocks.GULM_WOOD_PRESSURE_PLATE);
        gulmWoodPool.fence(ModBlocks.GULM_WOOD_FENCE);
        gulmWoodPool.fenceGate(ModBlocks.GULM_WOOD_FENCE_GATE);
        gulmWoodPool.wall(ModBlocks.GULM_WOOD_WALL);

        blockStateModelGenerator.registerDoor(ModBlocks.GULM_WOOD_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.GULM_WOOD_TRAP_DOOR);

        Identifier lampOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.SHINY_LAMP, blockStateModelGenerator.modelCollector);
        Identifier lampOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.SHINY_LAMP, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.SHINY_LAMP)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(ShinyLampBlock.CLICKED, lampOnIdentifier, lampOffIdentifier)));

        blockStateModelGenerator.registerCrop(ModBlocks.AMARANTH_CROP, AmaranthCropBlock.AGE, 0,1,2,3,4,5,6);
        blockStateModelGenerator.registerTintableCrossBlockStateWithStages(ModBlocks.CLOUD_BERRY_BUSH, BlockStateModelGenerator.TintType.NOT_TINTED,
                CloudBerryBushBlock.AGE, 0,1,2,3);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SHINY, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNREFINED_SHINY, Models.GENERATED);

        itemModelGenerator.register(ModItems.SPACE_ICECREAM, Models.GENERATED);
        itemModelGenerator.register(ModItems.STAR_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.STAR_FORGED_INGOT, Models.GENERATED);

        itemModelGenerator.register(ModItems.HAMMER, Models.HANDHELD);

        itemModelGenerator.register(ModItems.STAR_FORGED_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STAR_FORGED_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STAR_FORGED_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STAR_FORGED_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STAR_FORGED_HOE, Models.HANDHELD);

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.STAR_FORGED_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.STAR_FORGED_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.STAR_FORGED_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.STAR_FORGED_BOOTS));

        itemModelGenerator.register(ModItems.AMARANTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.CLOUD_BERRY_JAM, Models.GENERATED);
        itemModelGenerator.register(ModBlocks.GULM_WOOD_SAPLING.asItem(), Models.GENERATED);
    }
}
