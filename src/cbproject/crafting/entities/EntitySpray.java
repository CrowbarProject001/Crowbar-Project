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
package cbproject.crafting.entities;

import java.util.List;

import cbproject.crafting.register.CBCItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author Administrator
 * 
 */
public class EntitySpray extends Entity {

	public final static int[] GRIDS_HEIGHTS = {2, 2};
	public final static int[] GRIDS_WIDTHS = {10, 2};

	public int hanging_direction;
	public int block_pos_x;
	public int block_pos_y;
	public int block_pos_z;
	public int title_id;

	// 被框架自动调用
	public EntitySpray(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}

	// 被 Item 调用
	public EntitySpray(World world, int x, int y, int z, int direction,
			int title_id) {
		this(world);

		this.block_pos_x = x;
		this.block_pos_y = y;
		this.block_pos_z = z;
		this.hanging_direction = direction;
		this.title_id = title_id;

		this.save_params();
		this.init_params();
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(18, 0);
		this.dataWatcher.addObject(19, 0);
		this.dataWatcher.addObject(20, 0);
		this.dataWatcher.addObject(21, 0);
		this.dataWatcher.addObject(22, 0);
	}

	private void save_params() {
		this.dataWatcher.updateObject(18, this.hanging_direction);
		this.dataWatcher.updateObject(19, this.block_pos_x);
		this.dataWatcher.updateObject(20, this.block_pos_y);
		this.dataWatcher.updateObject(21, this.block_pos_z);
		this.dataWatcher.updateObject(22, this.title_id);
	}

	//为渲染器载入方向， x， y， z， title_id 等参数
	public void load_params() {
		this.hanging_direction = this.dataWatcher.getWatchableObjectInt(18);
		this.block_pos_x = this.dataWatcher.getWatchableObjectInt(19);
		this.block_pos_y = this.dataWatcher.getWatchableObjectInt(20);
		this.block_pos_z = this.dataWatcher.getWatchableObjectInt(21);
		this.title_id = this.dataWatcher.getWatchableObjectInt(22);

		this.init_params();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		this.hanging_direction = var1.getInteger("hanging_direction");
		this.block_pos_x = var1.getInteger("block_pos_x");
		this.block_pos_y = var1.getInteger("block_pos_y");
		this.block_pos_z = var1.getInteger("block_pos_z");
		this.title_id = var1.getInteger("title_id");

		init_params();
		save_params();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		var1.setInteger("hanging_direction", this.hanging_direction);
		var1.setInteger("block_pos_x", this.block_pos_x);
		var1.setInteger("block_pos_y", this.block_pos_y);
		var1.setInteger("block_pos_z", this.block_pos_z);
		var1.setInteger("title_id", this.title_id);
	}

	public void init_params() {
		int direction = this.hanging_direction;

		float half_width = GRIDS_WIDTHS[this.title_id] / 2.0F;
		float half_height = GRIDS_HEIGHTS[this.title_id] / 2.0F;

		float entity_pos_x;
		float entity_pos_y = this.block_pos_y + half_height;
		float entity_pos_z;

		float horizontal_off = 0.0625F; // 1/16

		if (direction == 2) {
			entity_pos_x = this.block_pos_x + 1.0F - half_width;
			entity_pos_z = this.block_pos_z - horizontal_off;

			this.rotationYaw = this.prevRotationYaw = 180;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - half_width, entity_pos_y
					- half_height, entity_pos_z - horizontal_off, entity_pos_x
					+ half_width, entity_pos_y + half_height, entity_pos_z
					+ horizontal_off);
			return;
		}

		if (direction == 1) {
			entity_pos_x = this.block_pos_x - horizontal_off;
			entity_pos_z = this.block_pos_z + half_width;

			this.rotationYaw = this.prevRotationYaw = 270;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - horizontal_off,
					entity_pos_y - half_height, entity_pos_z - half_width,
					entity_pos_x + horizontal_off, entity_pos_y + half_height,
					entity_pos_z + half_width);
			return;
		}

		if (direction == 0) {
			entity_pos_x = this.block_pos_x + half_width;
			entity_pos_z = this.block_pos_z + 1.0F + horizontal_off;

			this.rotationYaw = this.prevRotationYaw = 0;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - half_width, entity_pos_y
					- half_height, entity_pos_z - horizontal_off, entity_pos_x
					+ half_width, entity_pos_y + half_height, entity_pos_z
					+ horizontal_off);
			return;
		}

		if (direction == 3) {
			entity_pos_x = this.block_pos_x + 1.0F + horizontal_off;
			entity_pos_z = this.block_pos_z + 1.0F - half_width;

			this.rotationYaw = this.prevRotationYaw = 90;
			this.setPosition(entity_pos_x, entity_pos_y, entity_pos_z);
			this.boundingBox.setBounds(entity_pos_x - horizontal_off,
					entity_pos_y - half_height, entity_pos_z - half_width,
					entity_pos_x + horizontal_off, entity_pos_y + half_height,
					entity_pos_z + half_width);
			return;
		}
	}

	//判断被放置界面是否可用
	public boolean onValidSurface() {
		//如果碰撞箱不是空的返回false
		if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
				.isEmpty()) {
			return false;
		}

		int x = this.block_pos_x;
		int y = this.block_pos_y;
		int z = this.block_pos_z;
		int direction = this.hanging_direction;

		float half_width = GRIDS_WIDTHS[this.title_id] / 2.0F; //宽度一半
		float half_height = GRIDS_HEIGHTS[this.title_id] / 2.0F; //高度一半

		if (direction == 2) {
			x = MathHelper.floor_double(this.posX - half_width);
		}

		if (direction == 1) {
			z = MathHelper.floor_double(this.posZ - half_width);
		}

		if (direction == 0) {
			x = MathHelper.floor_double(this.posX - half_width);
		}

		if (direction == 3) {
			z = MathHelper.floor_double(this.posZ - half_width);
		}

		y = MathHelper.floor_double(this.posY - half_height);

		for (int i = 0; i < GRIDS_WIDTHS[this.title_id]; ++i) {
			for (int j = 0; j < GRIDS_HEIGHTS[this.title_id]; ++j) {
				Material material, material2;

				if (direction == 1 || direction == 3) {
					material = this.worldObj.getBlockMaterial(this.block_pos_x,
							y + j, z + i);
					material2 = this.worldObj.getBlockMaterial(
							this.block_pos_x + 1, y + j, z + i);
				} else {
					material = this.worldObj.getBlockMaterial(x + i, y + j,
							this.block_pos_z + 1);
					material2 = this.worldObj.getBlockMaterial(x + i,
							y + j, this.block_pos_z);
				}
				if (!material.isSolid()) {
					clientPrintLn("放置面面积不够，不允许放置。");
					return false;
				}
				if (material2.isLiquid()) {
					clientPrintLn("放置面上有液体，不允许放置。");
					return false;
				}
			}
		
			
		}

		//判断碰装箱内是否出现EntitySpray的实例
		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox);
		for (Object entity : list) {
			if (!(entity instanceof EntitySpray))
				continue;
			clientPrintLn("被 " + entity + " 阻挡，不允许放置。");
			return false;
		}

		return true;
	}

	// 让玩家可以击落画像为Item-----------------
	@Override
	public boolean func_85031_j(Entity par1Entity) {
		return par1Entity instanceof EntityPlayer ? this.attackEntityFrom(
				DamageSource.causePlayerDamage((EntityPlayer) par1Entity), 0)
				: false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (this.isEntityInvulnerable())
			return false;

		if (!this.isDead && !this.worldObj.isRemote) {
			this.setDead();
			this.setBeenAttacked();
		}
		return true;
	}
	
	private void clientPrintLn(String str) {
		if(!this.worldObj.isRemote) {
			System.out.println(str);
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}