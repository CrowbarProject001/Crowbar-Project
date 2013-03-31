package cbproject.utils;

public class BlockPos {

	public int x, y, z, blockID;
	
	public BlockPos(int par1, int par2, int par3, int bID) {
		
		x = par1;
		y = par2;
		z = par3;
		blockID = bID;
		
	}
	
	public Boolean equals(BlockPos b){
		return (x == b.x && y == b.y && z == b.z) && blockID == b.blockID;
	}
	
	public BlockPos copy(){
		return new BlockPos(x,y,z,blockID);
	}

}
