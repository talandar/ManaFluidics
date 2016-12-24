package derpatiel.manafluidics.block.altar;

import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class KAltarGui extends PagedGui {

    public KnowledgeAltarTileEntity tile;
    private Random rand;
    private EntityPlayer player;

    private ItemStack[] iconsByLevel = new ItemStack[]{
            new ItemStack(ModItems.manaCrystal),
            new ItemStack(ModItems.crystal_iron_ingot),
            new ItemStack(ModItems.redcrystal_gem),
            new ItemStack(ModBlocks.knowledgeAltar),
            new ItemStack(ModBlocks.knowledgeAltar)
    };

    public KAltarGui(KnowledgeAltarTileEntity tileEntity, KAltarContainer container, EntityPlayer player) {
        super(container);
        tile = tileEntity;
        this.player=player;
        rand = new Random();
    }

    @Override
    protected void addPages() {
        addPage(new AltarTypeSelectionPage(this));
        for(int i=0;i<5;i++) {
            addPage(new SpellPrepSelectionPage(iconsByLevel[i], i, player,this));
        }
    }
}
