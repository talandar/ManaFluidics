package derpatiel.manafluidics.block.multiTank;

import derpatiel.manafluidics.block.ITankPart;
import derpatiel.manafluidics.block.multiTank.general.heat.HeatConnectionTileEntity;
import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.enums.TankPartState;
import derpatiel.manafluidics.multiblock.FluidTankData;
import derpatiel.manafluidics.multiblock.MultiblockHandler;
import derpatiel.manafluidics.registry.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class TankFormingTileEntity extends TankPartTileEntity implements ITickable {

    private boolean formed=false;
    private String unformedReason;

    private int minX;
    private int maxX;

    private int minZ;
    private int maxZ;

    private int baseY;
    private int tankHeight;

    protected final List<BlockPos> tankBlocks = new ArrayList<BlockPos>();
    protected final List<BlockPos> tankTiles = new ArrayList<BlockPos>();

    public static final int TANK_CAPACITY_PER_BLOCK=8* Fluid.BUCKET_VOLUME;

    @Override
    public final void update() {
        if(formed){
            doUpdate();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("formed", formed);
        compound.setIntArray("size", new int[]{minX,maxX,minZ,maxZ,baseY,tankHeight});
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        formed = compound.getBoolean("formed");
        int[] size = compound.getIntArray("size");
        if(size.length>0){
            minX=size[0];
            maxX=size[1];
            minZ=size[2];
            maxZ=size[3];
            baseY=size[4];
            tankHeight=size[5];
        }
    }

    public void setTankBlocks(List<BlockPos> blocks){
        this.tankBlocks.clear();
        this.tankBlocks.addAll(blocks);
    }


    public boolean isFormed(){
        return formed;
    }

    public String getUnformedReason(){
        return unformedReason;
    }

    public int getHeight() {
        return this.tankHeight;
    }

    public int getTankBaseY(){
        return this.baseY;
    }

    public int getMaxY(){
        return this.baseY+this.tankHeight;
    }

    public int getMinX(){
        return this.minX;
    }

    public int getMaxX(){
        return this.maxX;
    }

    public int getMinZ(){
        return this.minZ;
    }

    public int getMaxZ(){
        return this.maxZ;
    }

    public int getNumInteriorBlocks(){
        return (maxX-minX) * (maxZ-minZ) * tankHeight;
    }

    public BlockPos getCenterPos(){
        return new BlockPos((maxX+minX)/2,baseY+1,(maxZ+minZ)/2);
    }


    /**
     * called on the tank when it it known to be valid and formed correctly.
     * should do normal processing code assuming that it is valid.
     */
    public abstract void doUpdate();

    /**
     * called to notify the implementation that the tank has formed with specific dimensions
     */
    public abstract void notifyFormed();

    /**
     * called to notify the implementation that the tank has become invalid
     */
    public abstract void notifyUnformed();

    /**
     * returns true if we need/allow heat interfaces
     */
    public abstract boolean needsHeatInterface();

    /**
     * returns true if we need/allow InfusedEnergizedMana interfaces (item storage tank)
     */
    public abstract boolean needsIEMInterface();

    /**
     * returns true if we need to accept items (melting/refinery tank)
     * @return
     */
    public abstract boolean needsItemInterface();

    /**
     * called to alert the tank that the height has changed.
     */
    public abstract void newHeight();

    public void tryFormTank(){
        unformedReason=null;
        //find base
        BlockPos check = this.getPos().down();
        IBlockState checkBlock = worldObj.getBlockState(check);
        while(checkBlock.getBlock() instanceof ITankPart && checkBlock.getBlock()!= ModBlocks.tankBottom){
            check = check.down();
            checkBlock = worldObj.getBlockState(check);
        }
        if(checkBlock.getBlock()!=ModBlocks.tankBottom){
            unformedReason="No tank base beneath controller.";
            return;
        }

        //look in all four directions to find extents of tank bottom

        BlockPos northMost=check;
        BlockPos southMost=check;
        BlockPos eastMost=check;
        BlockPos westMost=check;

        BlockPos lookFrom=check;
        //look north
        check = lookFrom.north();
        checkBlock = worldObj.getBlockState(check);
        while(checkBlock.getBlock()==ModBlocks.tankBottom){
            northMost=check;
            check = check.north();
            checkBlock = worldObj.getBlockState(check);
        }

        //look south
        check = lookFrom.south();
        checkBlock = worldObj.getBlockState(check);
        while(checkBlock.getBlock()==ModBlocks.tankBottom){
            southMost=check;
            check = check.south();
            checkBlock = worldObj.getBlockState(check);
        }

        //look east
        check = lookFrom.east();
        checkBlock = worldObj.getBlockState(check);
        while(checkBlock.getBlock()==ModBlocks.tankBottom){
            eastMost=check;
            check = check.east();
            checkBlock = worldObj.getBlockState(check);
        }

        //look west
        check = lookFrom.west();
        checkBlock = worldObj.getBlockState(check);
        while(checkBlock.getBlock()==ModBlocks.tankBottom){
            westMost=check;
            check = check.west();
            checkBlock = worldObj.getBlockState(check);
        }

        int tmpMinZ=northMost.getZ();
        int tmpMaxZ=southMost.getZ();
        int tmpMinX=westMost.getX();
        int tmpMaxX=eastMost.getX();
        int tmpBaseY=lookFrom.getY();
        if((tmpMaxZ-tmpMinZ)<=1 || (tmpMaxX-tmpMinX)<=1){
            unformedReason = "tank requires at least one interior block.";
            return;
        }

        //controller can't be in corner
        //also figure out which edge it's on
        TankPartState dir = findDirectionFromLocation(this.pos, tmpMinX, tmpMaxX, tmpMinZ, tmpMaxZ);




        //LOG.info("("+minX+", "+minZ+") to ("+maxX+", "+maxZ+")");

        for(int z=tmpMinZ;z<=tmpMaxZ;z++){
            for(int x=tmpMinX;x<=tmpMaxX;x++){
                if(worldObj.getBlockState(new BlockPos(x,tmpBaseY,z)).getBlock()!=ModBlocks.tankBottom){
                    unformedReason = "found invalid block in tank base at ("+x+", "+tmpBaseY+", "+z+")";
                    return;
                }
            }
        }
        //entire bottom is tankbottom blocks

        int tmpTankHeight=0;
        boolean valid=true;
        boolean controllerIncluded=false;
        List<BlockPos> tiles = new ArrayList<BlockPos>();
        while(valid){
            int testHeight=tmpTankHeight+tmpBaseY+1;
            for(int z=tmpMinZ;valid && z<=tmpMaxZ;z++){
                for(int x=tmpMinX;valid && x<=tmpMaxX;x++){
                    BlockPos testPos = new BlockPos(x,testHeight,z);
                    IBlockState tankBlock = worldObj.getBlockState(testPos);
                    if(x==tmpMinX || x==tmpMaxX || z==tmpMinZ || z==tmpMaxZ){
                        //at edge, must be ITankPart.  if controller, must be this block.
                        if(!(tankBlock.getBlock() instanceof ITankPart)){
                            valid=false;
                            //don't set unformed reason here
                            //because it could just be above the top of the tank
                            break;
                        }
                        if(tankBlock.getBlock() instanceof MFTankControllerBlock){
                            //it's a controller, so it has to be this
                            if(!pos.equals(testPos)){
                                valid=false;
                                unformedReason="found other controller at ("+x+", "+testHeight+", "+z+")";
                                break;
                            }else{//it's a controller and this one, so that's fine.
                                controllerIncluded=true;
                            }
                        }else if(tankBlock.getBlock() instanceof MFTankEntityBlock){
                            TankPartTileEntity tankPartTile = (TankPartTileEntity)worldObj.getTileEntity(testPos);

                            valid = checkTankPartValidity(tankPartTile);
                            tiles.add(testPos);
                        }
                    }else{
                        if(tankBlock.getMaterial()!= Material.AIR){
                            valid=false;//not valid in the interior of the tank.
                            break;
                            //don't set unformed reason here
                            //because it could just be above the top of the tank
                        }
                    }
                }
            }
            if(valid){
                tmpTankHeight++;
            }
        }

        if(tmpTankHeight==0){
            unformedReason="could not form tank: there were not enough layers to the tank (minimum 1)";
            return;
        }

        if(!controllerIncluded){
            unformedReason="could not form tank: controller was not found in wall of tank.  Was the top wall unfinished?";
            return;
        }

        //Form the tank!
        this.baseY=tmpBaseY;
        this.tankHeight=tmpTankHeight;

        this.minX=tmpMinX;
        this.maxX=tmpMaxX;

        this.minZ=tmpMinZ;
        this.maxZ=tmpMaxZ;

        this.formed=true;
        this.tankBlocks.addAll(getTankBlocks());

        this.notifyFormed();

        if(!worldObj.isRemote){
            FluidTankData data = new FluidTankData(getPos(), new ArrayList<BlockPos>(tankBlocks));
            MultiblockHandler.registerMultiblock(worldObj, data);
        }

        for(BlockPos pos : tiles){
            addNewTile(pos);
        }

        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(MFTankControllerBlock.STATE,dir));
    }

    public void checkNewBounds(){
        List<BlockPos> foundValidBlocks = new ArrayList<>();
        List<BlockPos> foundValidTiles = new ArrayList<>();
        List<BlockPos> newLevelBlocks = new ArrayList<>();
        List<BlockPos> newLevelTiles = new ArrayList<>();
        boolean wholeLevelValid=true;
        int additionalHeight=0;
        while(wholeLevelValid) {
            newLevelBlocks.clear();
            newLevelTiles.clear();
            int nextY = baseY + tankHeight + additionalHeight + 1;
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, nextY, z);
                    if (pos.getX() == minX || pos.getX() == maxX || pos.getZ() == minZ || pos.getZ() == maxZ) {
                        if (!(worldObj.getBlockState(pos).getBlock() instanceof ITankPart)) {
                            wholeLevelValid=false;
                        }
                        if (worldObj.getBlockState(pos).getBlock() instanceof MFTankEntityBlock) {
                            TankPartTileEntity tankPartTile = (TankPartTileEntity) worldObj.getTileEntity(pos);

                            if (!checkTankPartValidity(tankPartTile)) {
                                wholeLevelValid=false;
                            }
                            newLevelTiles.add(pos);
                        }
                    } else {//not at base level, not a wall, must be air
                        if (worldObj.getBlockState(pos).getMaterial() != Material.AIR) {
                            wholeLevelValid=false;
                        }
                    }
                    newLevelBlocks.add(pos);
                }
            }

            if(wholeLevelValid) {
                foundValidBlocks.addAll(newLevelBlocks);
                foundValidTiles.addAll(newLevelTiles);
                additionalHeight++;
            }
        }
        if(additionalHeight>0) {
            foundValidTiles.forEach(this::addNewTile);
            tankBlocks.addAll(foundValidBlocks);
            tankHeight += additionalHeight;
            this.newHeight();

            if (!worldObj.isRemote) {
                FluidTankData data = new FluidTankData(getPos(), tankBlocks);
                MultiblockHandler.registerMultiblock(worldObj, data);
            }
            markForUpdate();
        }
    }

    private void addNewTile(BlockPos pos){
        this.tankTiles.add(pos);
        TankPartTileEntity te = (TankPartTileEntity)worldObj.getTileEntity(pos);
        te.setParent(this.getPos());
        te.setDirection(findDirectionFromLocation(pos, minX, maxX, minZ, maxZ));
        te.markForUpdate();
    }

    private List<BlockPos> getTankBlocks(){
        List<BlockPos> blocks = new ArrayList<BlockPos>();
        for(int x=minX;x<=maxX;x++){
            for(int y=baseY;y<=getMaxY();y++){
                for(int z=minZ;z<=maxZ;z++){
                    blocks.add(new BlockPos(x,y,z));
                }
            }
        }
        return blocks;
    }

    public void unform() {
        displayParticles(EnumParticleTypes.FLAME);
        for(BlockPos pos : tankTiles){
            TankPartTileEntity tile = (TankPartTileEntity) worldObj.getTileEntity(pos);
            if(tile!=null) {
                tile.clearParent();
            }
        }
        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(MFTankControllerBlock.STATE,TankPartState.UNFORMED));
        notifyUnformed();

        tankTiles.clear();
        tankBlocks.clear();

        formed=false;
        minX=0;
        maxX=0;
        minZ=0;
        maxZ=0;
        baseY=0;
        tankHeight=0;
        markForUpdate();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !(oldState.getBlock() instanceof MFTankControllerBlock && newState.getBlock() instanceof MFTankControllerBlock);
    }

    public static TankPartState findDirectionFromLocation(BlockPos pos, int minX, int maxX, int minZ, int maxZ){
        TankPartState dir=null;
        int adjacencyCount=0;
        if(pos.getX()==minX){
            adjacencyCount++;
            dir=TankPartState.WEST;
        }
        if(pos.getX()==maxX){
            adjacencyCount++;
            dir=TankPartState.EAST;
        }
        if(pos.getZ()==minZ){
            adjacencyCount++;
            dir=TankPartState.NORTH;
        }
        if(pos.getZ()==maxZ){
            adjacencyCount++;
            dir=TankPartState.SOUTH;
        }
        if(adjacencyCount!=1){
            dir=null;
        }
        return dir;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(this.isFormed()){
            AxisAlignedBB bb;
            bb = new AxisAlignedBB(this.getMinX(),this.getTankBaseY(),this.getMinZ(), this.getMaxX()+1,this.getMaxY(),this.getMaxZ()+1);
            return bb;
        }else{
            return super.getRenderBoundingBox();
        }

    }

    public void displayParticles(EnumParticleTypes particle) {
        if(!this.worldObj.isRemote){
            if(tankBlocks.isEmpty()){
                setTankBlocks(MultiblockHandler.getTankLocationInfo(this.worldObj.provider.getDimension(),this.pos));
            }
            WorldServer worldserver = (WorldServer) worldObj;
            for(BlockPos tankPos : tankBlocks){
                worldserver.spawnParticle(particle, false, tankPos.getX()+0.3, tankPos.getY()+0.5, tankPos.getZ()+0.3, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
                worldserver.spawnParticle(particle, false, tankPos.getX()+0.3, tankPos.getY()+0.5, tankPos.getZ()+0.7, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
                worldserver.spawnParticle(particle, false, tankPos.getX()+0.7, tankPos.getY()+0.5, tankPos.getZ()+0.3, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
                worldserver.spawnParticle(particle, false, tankPos.getX()+0.7, tankPos.getY()+0.5, tankPos.getZ()+0.7, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
    }

    private boolean checkTankPartValidity(TankPartTileEntity tankPartTile) {
        boolean valid = true;
        if (tankPartTile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            if (!needsItemInterface()) {
                valid = false;
                unformedReason = "tank does not allow item handler";
            }
        }
        if (tankPartTile instanceof HeatConnectionTileEntity) {
            if (!needsHeatInterface()) {
                valid = false;
                unformedReason = "tank does not allow heat handler";
            }
        }
        //TODO: check IEM capability
        return valid;
    }
}
