package derpatiel.manafluidics.network;

import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketKnowledgeSync implements IMessage {
    private MFPlayerKnowledge knowledge;

    //used by receiving side to construct before deserialization
    public PacketKnowledgeSync() {
    }


    public PacketKnowledgeSync(MFPlayerKnowledge knowledge) {
        this.knowledge = knowledge;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, MFPlayerKnowledge.toNbt(knowledge));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        knowledge = MFPlayerKnowledge.fromNbt(ByteBufUtils.readTag(buf));
    }

    public static class Handler implements IMessageHandler<PacketKnowledgeSync, IMessage> {
        @Override
        public IMessage onMessage(PacketKnowledgeSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new PacketKnowledgeSync.MessageRunnable(message));
            return null;
        }
    }

    private static class MessageRunnable implements Runnable {

        PacketKnowledgeSync message;

        public MessageRunnable(PacketKnowledgeSync message) {
            this.message = message;
        }

        @Override
        public void run() {
            PlayerKnowledgeHandler.syncFromServer(message.knowledge);
        }
    }
}
