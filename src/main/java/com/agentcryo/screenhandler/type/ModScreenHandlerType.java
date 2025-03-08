package com.agentcryo.screenhandler.type;

import com.agentcryo.Testmod;
import com.agentcryo.network.BlockPosPayload;
import com.agentcryo.screenhandler.DrillScreenHandler;
import com.agentcryo.screenhandler.ForgeScreenHandler;
import com.agentcryo.screenhandler.RefineryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerType {

    public static final ScreenHandlerType<DrillScreenHandler> DRILL = register("drill",
                DrillScreenHandler::new, BlockPosPayload.PACKET_CODEC);

    public static final ScreenHandlerType<ForgeScreenHandler> FORGE = register("forge",
            ForgeScreenHandler::new, BlockPosPayload.PACKET_CODEC);

    public static final ScreenHandlerType<RefineryScreenHandler> REFINERY = register("refinery",
            RefineryScreenHandler::new, BlockPosPayload.PACKET_CODEC);

    public static <T extends ScreenHandler,  D extends CustomPayload> ExtendedScreenHandlerType<T, D>
        register(String name,
                 ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
                 PacketCodec<? super RegistryByteBuf, D> codec) {
            return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Testmod.MOD_ID, name), new ExtendedScreenHandlerType<>(factory, codec));
        };

    public static void registerScreenHandlerType() {
        Testmod.LOGGER.info("Registering Mod Screen Handler Types for " + Testmod.MOD_ID);
    }
 }
