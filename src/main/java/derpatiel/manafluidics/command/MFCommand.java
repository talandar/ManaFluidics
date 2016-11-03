package derpatiel.manafluidics.command;

import derpatiel.manafluidics.util.LOG;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class MFCommand extends CommandBase{

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
        EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
        LOG.info("Sender: "+player.getName());
        //self: a command like /manafluidics help has 'help' as args[0].  no args if no text past manafluidics
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
        return super.isUsernameIndex(args, index);
    }
}
