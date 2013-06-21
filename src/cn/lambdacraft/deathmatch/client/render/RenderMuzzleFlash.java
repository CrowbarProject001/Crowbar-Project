package cn.lambdacraft.deathmatch.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.proxy.ClientProps;

import static cn.lambdacraft.core.client.RenderUtils.addVertex;
import static cn.lambdacraft.core.client.RenderUtils.newV3;

public class RenderMuzzleFlash {

	public static void renderItemIn2d(Tessellator t, double tx, double ty,
			double tz) {

		Vec3 a1 = newV3(1.6, 0.5, -0.5), a2 = newV3(1.6, 1.5, -0.5), a3 = newV3(
				1, 1.5, 0.5), a4 = newV3(1, 0.5, 0.5);

		float u1 = 0.0F, v1 = 0.0F, u2 = 1.0F, v2 = 1.0F;

		t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				RenderUtils.getTexture(ClientProps.getRandomMuzzleFlash()));
		GL11.glTranslated(tx, ty + 0.1F, tz + 0.1F);
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, 1.0F);
		addVertex(a1, u2, v2);
		addVertex(a2, u1, v2);
		addVertex(a3, u1, v1);
		addVertex(a4, u2, v1);

		addVertex(a4, u2, v1);
		addVertex(a3, u1, v1);
		addVertex(a2, u1, v2);
		addVertex(a1, u2, v2);
		t.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

	}
}
