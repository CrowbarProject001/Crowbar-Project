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
package cbproject.mob.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author WeAthFolD
 *
 */


public class ModelSnark extends ModelBase
{
  //fields
    ModelRenderer head;
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
  
  public ModelSnark()
  {
      textureWidth = 64;
      textureHeight = 32;
    
      head = new ModelRenderer(this, 0, 0);
      head.addBox(0F, 0F, -5F, 2, 2, 2);
      head.setRotationPoint(0F, 1F, 0F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0.1396263F, 0F, 0F);
      body1 = new ModelRenderer(this, 0, 16);
      body1.addBox(-2F, -1F, 0F, 4, 2, 3);
      body1.setRotationPoint(1F, 2F, -4F);
      body1.setTextureSize(64, 32);
      body1.mirror = true;
      setRotation(body1, 0.2094395F, 0F, 0F);
      body2 = new ModelRenderer(this, 0, 16);
      body2.addBox(-2F, 0F, 0F, 4, 3, 4);
      body2.setRotationPoint(1F, 0F, -2F);
      body2.setTextureSize(64, 32);
      body2.mirror = true;
      setRotation(body2, -0.0523599F, 0F, 0F);
      body3 = new ModelRenderer(this, 0, 16);
      body3.addBox(-1F, 0F, -3F, 2, 2, 5);
      body3.setRotationPoint(1F, 2F, 0F);
      body3.setTextureSize(64, 32);
      body3.mirror = true;
      setRotation(body3, 0F, 0F, 0F);
      leg1 = new ModelRenderer(this, 0, 16);
      leg1.addBox(-1F, 0F, 0F, 1, 3, 1);
      leg1.setRotationPoint(1F, 4F, 0F);
      leg1.setTextureSize(64, 32);
      leg1.mirror = true;
      setRotation(leg1, 0F, 0F, 1.117011F);
      leg2 = new ModelRenderer(this, 0, 0);
      leg2.addBox(0F, 0F, 0F, 1, 3, 1);
      leg2.setRotationPoint(1F, 4F, 0F);
      leg2.setTextureSize(64, 32);
      leg2.mirror = true;
      setRotation(leg2, 0F, 0F, -1.117011F);
      leg3 = new ModelRenderer(this, 0, 0);
      leg3.addBox(-1F, 0F, 0F, 1, 3, 1);
      leg3.setRotationPoint(1F, 4F, -2F);
      leg3.setTextureSize(64, 32);
      leg3.mirror = true;
      setRotation(leg3, 0F, 0F, 1.117011F);
      leg4 = new ModelRenderer(this, 0, 0);
      leg4.addBox(0F, 0F, 0F, 1, 3, 1);
      leg4.setRotationPoint(1F, 4F, -2F);
      leg4.setTextureSize(64, 32);
      leg4.mirror = true;
      setRotation(leg4, 0F, 0F, -1.117011F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body1.render(f5);
    body2.render(f5);
    body3.render(f5);
    leg1.render(f5);
    leg2.render(f5);
    leg3.render(f5);
    leg4.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
  }

}

