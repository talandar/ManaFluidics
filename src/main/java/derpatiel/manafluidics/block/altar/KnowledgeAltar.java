package derpatiel.manafluidics.block.altar;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.enums.KnowledgeCategory;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class KnowledgeAltar extends MFTileBlock {

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public KnowledgeAltar(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
        this.setLightOpacity(0);
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                if (i > -2 && i < 2 && j == -1)
                {
                    j = 2;
                }

                if (rand.nextInt(16) == 0)
                {
                    for (int k = 0; k <= 1; ++k)
                    {
                        worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
                                (double)pos.getX() + 0.5D,                      (double)pos.getY() + 2.0D,                      (double)pos.getZ() + 0.5D,
                                (double)((float)i + rand.nextFloat()) - 0.5D,   (double)((float)k - rand.nextFloat() - 1.0F),   (double)((float)j + rand.nextFloat()) - 0.5D, new int[0]);
                    }
                }
            }
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new KnowledgeAltarTileEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(hand==EnumHand.MAIN_HAND) {
            if (heldItem != null && heldItem.getItem() == ModItems.debug_wand || heldItem.getItem()==ModItems.admin_altar_wand)
                return false;
            if (worldIn.isRemote) {
                return true;
            } else {
                MFPlayerKnowledge playerKnowledge = PlayerKnowledgeHandler.getPlayerKnowledge(playerIn);
                if (playerKnowledge.hasKnowledge(KnowledgeCategory.ALTAR_CRAFTED)) {
                    TileEntity tileentity = worldIn.getTileEntity(pos);
                    if (tileentity instanceof KnowledgeAltarTileEntity) {
                        playerIn.openGui(ManaFluidics.instance, ModGUIs.KNOWLEDGE_ALTAR_ID,worldIn,pos.getX(),pos.getY(), pos.getZ());
                        KnowledgeAltarTileEntity te = ((KnowledgeAltarTileEntity) tileentity);
                        if (te == null) {
                            ChatUtil.sendNoSpam(playerIn, "altar tile not right type?");
                        } else {
                            int level = te.getAltarValidLevel(worldIn);
                            AltarType type = te.type;
                            if (type != null) {
                                ChatUtil.sendNoSpam(playerIn, "Altar of type " + type.name() + ", valid at level " + level);
                            } else {
                                ChatUtil.sendNoSpam(playerIn, "Null altar type");
                            }
                        }
                    }
                } else {
                    ChatUtil.sendNoSpam(playerIn, TextHelper.localize("altar.noKnowledge.message"));
                }

                return true;
            }
        }else{
            return false;
        }
    }

}
