package com.agentcryo;

import com.agentcryo.block.ModBlockEntityType;
import com.agentcryo.block.ModBlocks;
import com.agentcryo.block.custom.entity.DrillEntity;
import com.agentcryo.component.ModDataComponentTypes;
import com.agentcryo.effect.ModEffects;
import com.agentcryo.enchantment.ModEnchantmentEffects;
import com.agentcryo.entity.ModEntities;
import com.agentcryo.item.ModItemGroups;
import com.agentcryo.item.ModItems;
import com.agentcryo.potion.ModPotions;
import com.agentcryo.recipe.ModRecipes;
import com.agentcryo.screenhandler.type.ModScreenHandlerType;
import com.agentcryo.util.HammerUseageEvent;
import com.agentcryo.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.*;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Testmod implements ModInitializer {
	public static final String MOD_ID = "testmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();
		ModPotions.registerPotions();
		ModEnchantmentEffects.registerEnchantmentEffects();
		ModWorldGeneration.generateModWorldGem();
		ModEntities.registerModEntities();
		ModBlockEntityType.registerBlockEntities();
		ModScreenHandlerType.registerScreenHandlerType();
		ModRecipes.registerRecipes();

		ModDataComponentTypes.registerDataComponentTypes();

		FuelRegistry.INSTANCE.add(ModItems.STAR_DUST, 2000);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUseageEvent());
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(entity instanceof SheepEntity sheepEntity) {
				if(player.getMainHandStack().getItem() == Items.SHEARS) {
					player.sendMessage(Text.literal("Wrong button!"));
					sheepEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 20));
				}

				return ActionResult.PASS;
			}

			return ActionResult.PASS;
		});

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMY_POTION);
		});

		CompostingChanceRegistry.INSTANCE.add(ModItems.AMARANTH, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.AMARANTH_GRAIN, 0.40f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.CLOUD_BERRIES, 0.40f);
		CompostingChanceRegistry.INSTANCE.add(ModBlocks.GULM_WOOD_SAPLING, 0.40f);

		StrippableBlockRegistry.register(ModBlocks.GULM_WOOD_LOG, ModBlocks.STRIPPED_GULM_WOOD_LOG);
		StrippableBlockRegistry.register(ModBlocks.GULM_WOOD_WOOD, ModBlocks.STRIPPED_GULM_WOOD_WOOD);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GULM_WOOD_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GULM_WOOD_WOOD,5,5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_GULM_WOOD_LOG,5,5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_GULM_WOOD_WOOD,5,5);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GULM_WOOD_PLANKS,5,5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GULM_WOOD_LEAVES,30,60);

		ItemStorage.SIDED.registerForBlockEntity(DrillEntity::getInventoryStorage, ModBlockEntityType.DRILL_BLOCK_ENTITY);

		LOGGER.info("Hello Fabric world!");
	}
}