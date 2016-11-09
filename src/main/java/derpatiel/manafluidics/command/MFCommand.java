package derpatiel.manafluidics.command;

import derpatiel.manafluidics.enums.KnowledgeCategory;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class MFCommand extends CommandBase{

    private static String[] helpLines;

    static{
        helpLines = new String[]{
                "Commands:",
                "manafluidics <playerName> clear",
                "    Clears all player knowledge",
                "manafluidics attributes",
                "    list attributes that can be added or removed",
                "manafluidics <playername> <give|remove> <attribute>",
                "    give or remove attributes from the user"

        };
    }

    @Override
    public String getCommandName() {
        return "manafluidics";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "manafluidics <username> <command> or manafluidics help";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer senderPlayer = (EntityPlayer)sender.getCommandSenderEntity();
        LOG.info("Sender: "+senderPlayer.getName());
        if(args.length==0){
            sendHelp(senderPlayer);
        }else if(args.length==1){
            if(args[0].equalsIgnoreCase("help")){
                sendHelp(senderPlayer);
            }else if(args[0].equalsIgnoreCase("attributes")){
                ChatUtil.sendChat(senderPlayer,buildAttributes());
            }
        }else if(args.length>1){
            boolean foundPlayer=false;
            for(EntityPlayerMP player : server.getPlayerList().getPlayerList()) {
                if(player.getName().equalsIgnoreCase(args[0])) {
                    foundPlayer=true;
                    handlePlayerCall(senderPlayer, player, Arrays.copyOfRange(args, 1,args.length));
                }
            }
            if(!foundPlayer){
                ChatUtil.sendChat(senderPlayer,"No player by name \""+args[0]+"\"");
            }
        }
    }

    private void sendHelp(EntityPlayer player){
        ChatUtil.sendChat(player,helpLines);
    }

    private void handlePlayerCall(EntityPlayer sender, EntityPlayer player, String[] args){
        if(args.length==0){
            sendHelp(sender);
        }else if(args.length==1){
            if(args[0].equalsIgnoreCase("clear")){
                PlayerKnowledgeHandler.getPlayerKnowledge(player).clearKnowledge();
            }//other no-extra-arg stuff
        }else if(args.length==2){
            if(args[0].equalsIgnoreCase("give")){
                String catString = args[1];
                KnowledgeCategory cat=null;
                try{ cat = KnowledgeCategory.valueOf(catString);}catch(Exception ignored){}
                if (cat == null) {
                    ChatUtil.sendChat(sender,"Unknown attribute \""+catString+"\" use /manafluidics attributes to see a list of attributes");
                }else{
                    PlayerKnowledgeHandler.getPlayerKnowledge(player).addKnowledge(cat);
                    ChatUtil.sendChat(sender,"Gave player "+player.getName()+" attribute \""+cat.name()+"\"");
                    if(sender!=player){
                        ChatUtil.sendChat(player,sender.getName()+" gave you manafluidics attribute \""+cat.name()+"\"");
                    }
                }
            }else if(args[0].equalsIgnoreCase("remove")){
                String catString = args[1];
                KnowledgeCategory cat=null;
                try{ cat = KnowledgeCategory.valueOf(catString);}catch(Exception ignored){}
                if (cat == null) {
                    ChatUtil.sendChat(sender,"Unknown attribute \""+catString+"\" use /manafluidics attributes to see a list of attributes");
                }else{
                    PlayerKnowledgeHandler.getPlayerKnowledge(player).removeKnowledge(cat);
                    ChatUtil.sendChat(sender,"Removed attribute \""+cat.name()+"\" from player \""+player.getName());
                    if(sender!=player){
                        ChatUtil.sendChat(player,sender.getName()+" removed your manafluidics attribute \""+cat.name()+"\"");
                    }
                }
            }
        }

    }

    private String buildAttributes(){
        StringBuilder builder = new StringBuilder();
        String sep = "";
        for(KnowledgeCategory cat : KnowledgeCategory.VALUES){
            builder.append(sep).append(cat.name());
            sep = ", ";
        }
        return builder.toString();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return super.getRequiredPermissionLevel();
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return super.getTabCompletionOptions(server, sender, args, pos);
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index==0;
    }
}
