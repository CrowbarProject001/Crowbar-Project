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
package cn.lambdacraft.xen.world.gen;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

/**
 * @author mkpoli
 *
 * 	
 */
public class NoiseGeneratorXen extends NoiseGenerator {
    /**
     * Collection of noise generation functions.  Output is combined to produce different octaves of noise.
     */
    private NoiseGeneratorPerlin[] generatorCollection;
    private int xen;

    public NoiseGeneratorXen(Random par1Random, int par2)
    {
        this.xen = par2;
        this.generatorCollection = new NoiseGeneratorPerlin[par2];

        for (int j = 0; j < par2; ++j)
        {
            this.generatorCollection[j] = new NoiseGeneratorPerlin(par1Random);
        }
    }

    /**
     * pars:(par2,3,4=noiseOffset ; so that adjacent noise segments connect) (pars5,6,7=x,y,zArraySize),(pars8,10,12 =
     * x,y,z noiseScale)
     */
    public double[] generateNoiseXens(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7, double par8, double par10, double par12)
    {
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        else
        {
            for (int k1 = 0; k1 < par1ArrayOfDouble.length; ++k1)
            {
                par1ArrayOfDouble[k1] = 0.0D;
            }
        }

        double d3 = 1.0D;

        for (int l1 = 0; l1 < this.xen; ++l1)
        {
            double d4 = (double)par2 * d3 * par8;
            double d5 = (double)par3 * d3 * par10;
            double d6 = (double)par4 * d3 * par12;
            long i2 = MathHelper.floor_double_long(d4);
            long j2 = MathHelper.floor_double_long(d6);
            d4 -= (double)i2;
            d6 -= (double)j2;
            i2 %= 16777216L;
            j2 %= 16777216L;
            d4 += (double)i2;
            d6 += (double)j2;
            this.generatorCollection[l1].populateNoiseArray(par1ArrayOfDouble, d4, d5, d6, par5, par6, par7, par8 * d3, par10 * d3, par12 * d3, d3);
            d3 /= 2.0D;
        }

        return par1ArrayOfDouble;
    }

    /**
     * Bouncer function to the main one with some default arguments.
     */
    public double[] generateNoiseXens(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, double par6, double par8, double par10)
    {
        return this.generateNoiseXens(par1ArrayOfDouble, par2, 10, par3, par4, 1, par5, par6, 1.0D, par8);
    }
}
