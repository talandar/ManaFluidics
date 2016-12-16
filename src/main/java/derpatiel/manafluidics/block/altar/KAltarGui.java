package derpatiel.manafluidics.block.altar;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.spell.SpellSelectionPage;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KAltarGui extends PagedGui {

    private KnowledgeAltarTileEntity tile;
    private Random rand;
    private EntityPlayer player;

    public KAltarGui(KnowledgeAltarTileEntity tileEntity, KAltarContainer container, EntityPlayer player) {
        super(container);
        tile = tileEntity;
        this.player=player;
        rand = new Random();
    }

    @Override
    protected void addPages() {

        for(int i=0;i<5;i++) {
            addPage(new SpellSelectionPage(new ItemStack(ModItems.manaCrystal), i, player,this));
        }
    }
}
