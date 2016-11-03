package derpatiel.manafluidics.command;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
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
        return "manafluidics <command>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        LOG.info("command!");

    }
}
