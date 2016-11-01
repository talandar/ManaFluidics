package derpatiel.manafluidics.block;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModDamageSources;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class MFBlockFluid extends BlockFluidClassic {

    private final String bareUnlocalizedName;

    public MFBlockFluid(Fluid fluid, Material material, String name, int quantaPerBlock) {
        super(fluid, material);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.bareUnlocalizedName = name;
        this.setQuantaPerBlock(quantaPerBlock);
        this.setCreativeTab(ModItems.tabFluidics);
    }

    public void registerItemModel(ItemBlock itemBlock){
        ManaFluidics.proxy.registerItemRenderer(itemBlock,0,bareUnlocalizedName);
    }

    public String getBareUnlocalizedName(){
        return bareUnlocalizedName;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        Fluid fluid = this.getFluid();
        if(entityIn instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            if (fluid == ModFluids.energizedMana || fluid == ModFluids.reactiveMana) {
                entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING,5));
            } else if (fluid == ModFluids.moltenIron || fluid == ModFluids.moltenGold || fluid == ModFluids.moltenCrystal) {
                entity.attackEntityFrom(ModDamageSources.moltenLiquid, 5);
            }
        }
    }

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) {
        return super.canDisplace(world, pos);
    }

    /**
     * Attempt to displace the block at (pos), return true if it was displaced.
     */
    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
        /*
        if (this.getFluid() == ModFluids.reactiveMana) {
            if (world.isAirBlock(pos)) {
                return true;
            }

            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == this) {
                return false;
            }

            Material material = state.getMaterial();
            if (block == Blocks.OBSIDIAN || material == Material.PORTAL) {
                return false;
            }

            return true;

        } else {
        */
        return super.displaceIfPossible(world, pos);
    }


}
