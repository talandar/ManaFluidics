package derpatiel.manafluidics.network;

import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.registry.SpellRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketChangeSelection implements IMessage
{
    private String spellReg;
    private UUID playerId;

    public PacketChangeSelection(){};

    public PacketChangeSelection(String spellRegName, UUID playerId){
        this.spellReg = spellRegName;
        this.playerId = playerId;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf,spellReg);
        ByteBufUtils.writeUTF8String(buf,playerId.toString());
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        spellReg = ByteBufUtils.readUTF8String(buf);
        playerId = UUID.fromString(ByteBufUtils.readUTF8String(buf));
    }

    public static class Handler implements IMessageHandler<PacketChangeSelection, IMessage> {
        @Override
        public IMessage onMessage(PacketChangeSelection message, MessageContext ctx) {

            EntityPlayer clickedPlayer = ctx.getServerHandler().playerEntity.getServerWorld().getPlayerEntityByUUID(message.playerId);
            PlayerKnowledgeHandler.getPlayerKnowledge(clickedPlayer).selectPreparedSpell(SpellRegistry.getSpellByRegName(message.spellReg));
            return null;
        }
    }
}
