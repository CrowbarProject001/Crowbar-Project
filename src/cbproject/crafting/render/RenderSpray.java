package cbproject.crafting.render;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cbproject.core.props.ClientProps;
import cbproject.crafting.entities.EntitySpray;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpray extends Render {

	@Override
	public void doRender(Entity entity, double pos_x, double pos_y,
			double pos_z, float rotation_yaw, float partial_tick_time) {
		this.renderThePainting((EntitySpray) entity, pos_x, pos_y, pos_z);
	}

	// 载入Texture
	private void load_texture(int title_id) {
		loadTexture(ClientProps.SPRY_PATH[title_id]);
	}

	public void renderThePainting(EntitySpray entity_art, double pos_x,
			double pos_y, double pos_z) {

		// 载入参数
		entity_art.load_params();

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		GL11.glTranslatef((float) pos_x, (float) pos_y, (float) pos_z);
		GL11.glRotatef(entity_art.rotationYaw, 0.0F, 1.0F, 0.0F);

		load_texture(entity_art.title_id);
		draw(entity_art);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private void draw(EntitySpray entity) {
		float start_x = -EntitySpray.GRIDS_WIDTHS[entity.title_id] / 2.0F; // x的起点(宽度的一半)
		float start_y = EntitySpray.GRIDS_HEIGHTS[entity.title_id] / 2.0F; // Ditto.
		float half_thickness = 0.03125F; // 画的厚度 1/16，一半的厚度为 1/32

		for (float i = 0; i < EntitySpray.GRIDS_WIDTHS[entity.title_id]; i++) { // i为从0到宽度的所有值
			for (float j = 0; j < EntitySpray.GRIDS_HEIGHTS[entity.title_id]; j++) { // j为从0到高度的所有值

				float left = start_x + i;
				float right = left + 1;
				float top = start_y - j;
				float bottom = top - 1;

				this.render_light(entity, (right + left) / 2,
						(bottom + top) / 2);

				float texture_left = i
						/ EntitySpray.GRIDS_WIDTHS[entity.title_id];
				float texture_right = (i + 1)
						/ EntitySpray.GRIDS_WIDTHS[entity.title_id];
				float texture_top = j
						/ EntitySpray.GRIDS_HEIGHTS[entity.title_id];
				float texture_bottom = (j + 1)
						/ EntitySpray.GRIDS_HEIGHTS[entity.title_id];

				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();

				// 正对玩家的一面，设置四个贴图顶点
				// setNormal 函数是设置贴图法线向量，关系到光照等
				// addVertexWithUV 函数，前三个参数是顶点的空间坐标，后两个参数是贴图上对应位置的偏移量（以比例表示）
				// 左上，右上，右下，左下。顺时针转一圈
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(right, top, half_thickness,
						texture_right, texture_top);
				tessellator.addVertexWithUV(left, top, half_thickness,
						texture_left, texture_top);
				tessellator.addVertexWithUV(left, bottom, half_thickness,
						texture_left, texture_bottom);
				tessellator.addVertexWithUV(right, bottom, half_thickness,
						texture_right, texture_bottom);

				// 背对玩家的一面
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0,
						0);
				tessellator
						.addVertexWithUV(left, bottom, -half_thickness, 0, 0);

				// 上边框
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);

				// 下边框
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				tessellator
						.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);
				tessellator
						.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0,
						0);

				// 左表面
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator
						.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);

				// 右表面
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);
				tessellator
						.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0,
						0);

				tessellator.draw();
			}
		}
	}

	private void render_light(EntitySpray entity, float center_x, float center_y) {
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY + center_y);
		int z = MathHelper.floor_double(entity.posZ);

		if (entity.hanging_direction == 2) {
			x = MathHelper.floor_double(entity.posX - center_x);
		}

		if (entity.hanging_direction == 1) {
			z = MathHelper.floor_double(entity.posZ + center_x);
		}

		if (entity.hanging_direction == 0) {
			x = MathHelper.floor_double(entity.posX + center_x);
		}

		if (entity.hanging_direction == 3) {
			z = MathHelper.floor_double(entity.posZ - center_x);
		}

		int light = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(
				x, y, z, 0);
		int var8 = light % 65536;
		int var9 = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				var8, var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}
}