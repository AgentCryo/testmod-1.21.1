package com.agentcryo;

import com.agentcryo.block.ModBlocks;
import com.agentcryo.entity.ModEntities;
import com.agentcryo.entity.client.ChairRender;
import com.agentcryo.screen.DrillScreen;
import com.agentcryo.screen.ForgeScreen;
import com.agentcryo.screen.RefineryScreen;
import com.agentcryo.screenhandler.RefineryScreenHandler;
import com.agentcryo.screenhandler.type.ModScreenHandlerType;
import com.agentcryo.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class TestModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GULM_WOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMARANTH_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOUD_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GULM_WOOD_SAPLING, RenderLayer.getCutout());

        ModModelPredicates.registerModelPredicates();

        EntityRendererRegistry.register(ModEntities.COUCH, ChairRender::new);

        HandledScreens.register(ModScreenHandlerType.DRILL, DrillScreen::new);
        HandledScreens.register(ModScreenHandlerType.FORGE, ForgeScreen::new);
        HandledScreens.register(ModScreenHandlerType.REFINERY, RefineryScreen::new);
    }
}
