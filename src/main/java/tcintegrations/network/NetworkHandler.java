package tcintegrations.network;

import java.util.function.Function;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;

public final class NetworkHandler {

    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(TCIntegrations.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;

        if (ModList.get().isLoaded(ModIntegration.BOTANIA_MODID)) {
            registerMessage(id++, BotaniaSetData.class, BotaniaSetData::new);
        }
    }

    private static <T extends IData> void registerMessage(int idx, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        INSTANCE.registerMessage(idx, type, IData::toBytes, decoder, (msg, ctx) -> {
            msg.process(ctx);
            ctx.get().setPacketHandled(true);
        });
    }

}