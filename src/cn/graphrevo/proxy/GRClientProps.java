package cn.graphrevo.proxy;

import net.minecraft.util.ResourceLocation;

/**
 * 客户端的相关信息，主要用来存放贴图路径之类。
 */
public class GRClientProps {

	public static final ResourceLocation 
		TEX_FROZEN_MAGIC = new ResourceLocation("graphrevo:textures/entities/frozen_magic.png"),
		TEX_TRIPMINE_BEAM = src("graphrevo:textures/blocks/tripmine_beam.png");
	
	public static final ResourceLocation
		TEX_TRIPMINE[] = { 
			src("graphrevo:textures/blocks/tripmine_top.png"),
			src("graphrevo:textures/blocks/tripmine_side.png"),
			src("graphrevo:textures/blocks/tripmine_front.png"),
			src("graphrevo:textures/blocks/tripmine_back.png")
		};
	
	private static ResourceLocation src(String s) {
		return new ResourceLocation(s);
	}

}
