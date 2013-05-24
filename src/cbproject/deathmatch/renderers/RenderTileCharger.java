/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.deathmatch.renderers;

import org.lwjgl.opengl.GL11;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderUtils;
import cbproject.deathmatch.blocks.BlockArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;
import cbproject.deathmatch.register.DMBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

/**
 * @author Administrator
 *
 */
public class RenderTileCharger extends TileEntitySpecialRenderer {

	/** The minimum X value for rendering (default 0.0). */
	public double minX;

	/** The maximum X value for rendering (default 1.0). */
	public double maxX;

	/** The minimum Y value for rendering (default 0.0). */
	public double minY;

	/** The maximum Y value for rendering (default 1.0). */
	public double maxY;

	/** The minimum Z value for rendering (default 0.0). */
	public double minZ;

	/** The maximum Z value for rendering (default 1.0). */
	public double maxZ;

	public static void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	protected void setBound(Block block) {
		minX = block.getBlockBoundsMinX();
		minY = block.getBlockBoundsMinY();
		minZ = block.getBlockBoundsMinZ();
		maxX = block.getBlockBoundsMaxX();
		maxY = block.getBlockBoundsMaxY();
		maxZ = block.getBlockBoundsMaxZ();
	}
	
	protected void setBlockBounds(double par1, double par3, double par5,
			double par7, double par9, double par11) {
		this.minX = par1;
		this.maxX = par7;
		this.minY = par3;
		this.maxY = par9;
		this.minZ = par5;
		this.maxZ = par11;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#renderTileEntityAt(net.minecraft.tileentity.TileEntity, double, double, double, float)
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		
		Tessellator tessellator = Tessellator.instance;
        int var5 = tileentity.getBlockMetadata();
        BlockArmorCharger block = DMBlocks.armorCharger;
        TileEntityArmorCharger tileEntity = (TileEntityArmorCharger) tileentity;
        
        block.setBlockBoundsBasedOnState(tileentity.worldObj, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        setBound(block);

        Vec3 v1, v2, v3, v4, v5, v6, v7, v8;
        switch(var5){
        case 1:
        case 3:
        	v1 = RenderUtils.newV3(minX, minY, minZ);
        	v2 = RenderUtils.newV3(minX, minY, maxZ);
        	v3 = RenderUtils.newV3(minX, maxY, maxZ);
        	v4 = RenderUtils.newV3(minX, maxY, minZ);
        	
        	v5 = RenderUtils.newV3(maxX, minY, minZ);
        	v6 = RenderUtils.newV3(maxX, minY, maxZ);
        	v7 = RenderUtils.newV3(maxX, maxY, maxZ);
        	v8 = RenderUtils.newV3(maxX, maxY, minZ);
        	
        	break;
        	
        default:
        	v1 = RenderUtils.newV3(minX, minY, minZ);
        	v2 = RenderUtils.newV3(maxX, minY, minZ);
        	v3 = RenderUtils.newV3(maxX, maxY, minZ);
        	v4 = RenderUtils.newV3(minX, maxY, minZ);
        	
        	v5 = RenderUtils.newV3(minX, minY, maxZ);
        	v6 = RenderUtils.newV3(maxX, minY, maxZ);
        	v7 = RenderUtils.newV3(maxX, maxY, maxZ);
        	v8 = RenderUtils.newV3(minX, maxY, maxZ);
        	
        	break;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        this.bindTextureByName(ClientProps.HEVCHARGER_SIDE);
        tessellator.startDrawingQuads();
        if(var5 == 1 || var5 == 3){
        	
        	addVertex(v4 , 0, 0);
            addVertex(v8 , 1, 0);
            addVertex(v5 , 1, 1);
            addVertex(v1 , 0, 1);
             
            addVertex(v7 , 1, 0);
            addVertex(v3 , 0, 0);
            addVertex(v2 , 0, 1);
            addVertex(v6 , 1, 1);
            
        } else {
        	 	
            addVertex(v8 , 1, 0); 
            addVertex(v4 , 0, 0);
            addVertex(v1 , 0, 1);
            addVertex(v5 , 1, 1);
            
             
            addVertex(v7 , 1, 0); 
            addVertex(v6 , 1, 1);
            addVertex(v2 , 0, 1);
            addVertex(v3 , 0, 0);
            
        }
        tessellator.draw();
        
        this.bindTextureByName(ClientProps.HEVCHARGER_MAIN);
        tessellator.startDrawingQuads();
        if( var5 == 0 ){
        	
        	addVertex(v5, 0, 1);
            addVertex(v6, 1, 1);
            addVertex(v7, 1, 0);
            addVertex(v8, 0, 0);
            
        } else if(var5 == 1){
 
        	addVertex(v1, 0, 1);
            addVertex(v2, 1, 1);
            addVertex(v3, 1, 0);
            addVertex(v4, 0, 0);
            
        } else if(var5 == 2 ){
        	
        	addVertex(v4, 1, 0);
            addVertex(v3, 0, 0);
            addVertex(v2, 0, 1);
            addVertex(v1, 1, 1); 
            
        } else {

            addVertex(v7, 0, 0);
            addVertex(v6, 0, 1);
            addVertex(v5, 1, 1);
            addVertex(v8, 1, 0);
            
        }
        tessellator.draw();
        
        this.bindTextureByName(ClientProps.HEVCHARGER_BACK);
        tessellator.startDrawingQuads();
        if( var5 == 0 ){
        	
        	addVertex(v1, 0, 1);
            addVertex(v2, 1, 1);
            addVertex(v3, 1, 0);
            addVertex(v4, 0, 0);
            
        } else if(var5 == 1){
 
        	addVertex(v5, 0, 1);
            addVertex(v6, 1, 1);
            addVertex(v7, 1, 0);
            addVertex(v8, 0, 0);
            
        } else if(var5 == 2 ){
        	
        	addVertex(v5, 1, 1);
            addVertex(v8, 0, 1);
            addVertex(v7, 0, 0);
            addVertex(v6, 1, 0); 
            
        } else {

            addVertex(v3, 1, 0);
            addVertex(v2, 1, 1);
            addVertex(v1, 0, 1);
            addVertex(v4, 0, 0);
            
        }
        tessellator.draw();
        
        this.bindTextureByName(ClientProps.HEVCHARGER_TD);
        tessellator.startDrawingQuads();
        if(var5 == 1 || var5 == 3){
        	addVertex(v4 , 0, 0);
        	addVertex(v3 , 1, 0);
        	addVertex(v7 , 1, 1);
        	addVertex(v8 , 0, 1);
        	
        	addVertex(v6 , 1, 1); 
            addVertex(v2 , 1, 0);
            addVertex(v1 , 0, 0);
            addVertex(v5 , 0, 1); 
        } else {
        	addVertex(v7 , 1, 1);
        	addVertex(v3 , 1, 0);
        	addVertex(v4 , 0, 0);
        	addVertex(v8 , 0, 1);
        	          
            addVertex(v1 , 0, 0); 
            addVertex(v2 , 1, 0);
            addVertex(v6 , 1, 1); 
            addVertex(v5 , 0, 1); 
        }

        tessellator.draw();
        GL11.glPopMatrix();
	}

}
