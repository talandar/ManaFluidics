package derpatiel.manafluidics.network;

import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.registry.SpellRegistry;
import derpatiel.manafluidics.spell.parameters.SpellParameter;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketChangeParam implements IMessage
{
    private String spellReg;
    private SpellParameterChoices choice;
    private UUID playerId;

    public PacketChangeParam(){};

    public PacketChangeParam(String spellRegName, SpellParameterChoices choice, UUID playerId){
        this.spellReg = spellRegName;
        this.choice = choice;
        this.playerId = playerId;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf,spellReg);
        ByteBufUtils.writeUTF8String(buf,playerId.toString());
        buf.writeInt(choice.options.ordinal());
        buf.writeInt(choice.selectedOption);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        spellReg = ByteBufUtils.readUTF8String(buf);
        playerId = UUID.fromString(ByteBufUtils.readUTF8String(buf));
        SpellParameter param = SpellParameter.VALUES[buf.readInt()];
        int selection = buf.readInt();
        this.choice = new SpellParameterChoices(param,selection);
    }

    public static class Handler implements IMessageHandler<PacketChangeParam, IMessage> {
        @Override
        public IMessage onMessage(PacketChangeParam message, MessageContext ctx) {

            EntityPlayer clickedPlayer = ctx.getServerHandler().playerEntity.getServerWorld().getPlayerEntityByUUID(message.playerId);
            PlayerKnowledgeHandler.getPlayerKnowledge(clickedPlayer).setSpellParameter(message.spellReg,message.choice);
            return null;
        }
    }
}
