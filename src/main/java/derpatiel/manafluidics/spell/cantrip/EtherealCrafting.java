package derpatiel.manafluidics.spell.cantrip;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Jim on 12/15/2016.
 */
public class EtherealCrafting extends SpellBase {
    public EtherealCrafting() {
        super("etherealcrafting", 0, 10, SpellAttribute.CONJURATION, SpellAttribute.TRANSMUTATION);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters) {
        castingPlayer.displayGui(new InterfaceEtherealCraftingTable(worldIn));
        castingPlayer.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        return true;
    }

    public class InterfaceEtherealCraftingTable extends BlockWorkbench.InterfaceCraftingTable {
        private World theWorld;

        public InterfaceEtherealCraftingTable(World worldIn) {
            super(worldIn, null);
            this.theWorld=worldIn;
        }

        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
        {
            return new ContainerEtherealWorkbench(playerInventory, theWorld, null);
        }
    }

    public class ContainerEtherealWorkbench extends ContainerWorkbench{


        public ContainerEtherealWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
            super(playerInventory, worldIn, posIn);
        }

        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return true;
        }
    }
}
