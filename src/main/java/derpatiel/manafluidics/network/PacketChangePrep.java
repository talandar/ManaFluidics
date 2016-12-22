package derpatiel.manafluidics.network;

import derpatiel.manafluidics.player.MFPlayerKnowledge;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketChangePrep implements IMessage
{
    private String spellReg;

    public PacketChangePrep(){};

    public PacketChangePrep(String spellRegName){
        this.spellReg = spellRegName;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf,spellReg);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        spellReg = ByteBufUtils.readUTF8String(buf);
    }

    public static class Handler implements IMessageHandler<PacketChangePrep, IMessage> {
        @Override
        public IMessage onMessage(PacketChangePrep message, MessageContext ctx) {

           //TODO
            return null;
        }
    }
}
