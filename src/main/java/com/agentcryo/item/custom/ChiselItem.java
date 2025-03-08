package com.agentcryo.item.custom;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.component.ModDataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {

    public static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    ModBlocks.SHINY_BLOCK, Blocks.DIAMOND_BLOCK
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(CHISEL_MAP.containsKey((clickedBlock))) {
            if(!world.isClient()) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND)
                );

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);
                //((ServerWorld) world).addParticle(ParticleTypes.DUST_PLUME, context.getBlockPos().getX(), context.getBlockPos().getY() + 1.0, context.getBlockPos().getZ(), 0.0, 0.0, 0.0);

                context.getStack().set(ModDataComponentTypes.COORDS, context.getBlockPos());
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.testmod.chisel_tooltip_shiftdown"));
            if(stack.get(ModDataComponentTypes.COORDS) != null) {
                tooltip.add(Text.translatable("tooltip.testmod.chisel_coord_tooltip"));
                tooltip.add(Text.literal("" + stack.get(ModDataComponentTypes.COORDS)));
            }
        } else {
            tooltip.add(Text.translatable("tooltip.testmod.chisel_tooltip"));
        }

        super.appendTooltip(stack, context, tooltip, options);
    }
}
