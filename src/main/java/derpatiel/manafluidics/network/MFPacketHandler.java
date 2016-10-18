package derpatiel.manafluidics.network;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.util.ChatUtil;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MFPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(ManaFluidics.MODID);

    public static void init(){
        INSTANCE.registerMessage(ChatUtil.PacketNoSpamChat.Handler.class, ChatUtil.PacketNoSpamChat.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(SmeltingTankTileEntity.PacketFluidClick.Handler.class,SmeltingTankTileEntity.PacketFluidClick.class,1,Side.SERVER);
    }

}
