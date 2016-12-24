package derpatiel.manafluidics.network;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.util.ChatUtil;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MFPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(ManaFluidics.MODID);

    public static void init(){
        int ID=0;
        INSTANCE.registerMessage(ChatUtil.PacketNoSpamChat.Handler.class, ChatUtil.PacketNoSpamChat.class, ID++, Side.CLIENT);
        INSTANCE.registerMessage(PacketFluidClick.Handler.class,PacketFluidClick.class,ID++,Side.SERVER);
        INSTANCE.registerMessage(FluidChangedPacket.FluidChangedPacketMessageHandler.class,FluidChangedPacket.class,ID++,Side.CLIENT);
        INSTANCE.registerMessage(PacketFluidAlloy.Handler.class, PacketFluidAlloy.class,ID++,Side.SERVER);
        INSTANCE.registerMessage(DrawNozzleUpdatePacket.DrawNozzleUpdatePacketHandler.class,DrawNozzleUpdatePacket.class,ID++,Side.CLIENT);
        INSTANCE.registerMessage(PacketKnowledgeSync.Handler.class, PacketKnowledgeSync.class, ID++, Side.CLIENT);
        INSTANCE.registerMessage(PacketChangePrep.Handler.class, PacketChangePrep.class, ID++, Side.SERVER);
        INSTANCE.registerMessage(PacketAltarTypeChange.ClientHandler.class, PacketAltarTypeChange.class, ID++, Side.CLIENT);
        INSTANCE.registerMessage(PacketAltarTypeChange.ServerHandler.class, PacketAltarTypeChange.class, ID++, Side.SERVER);

    }

}
