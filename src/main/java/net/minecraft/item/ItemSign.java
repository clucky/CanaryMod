package net.minecraft.item;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {

    public ItemSign() {
        this.h = 16;
        this.a(CreativeTabs.c);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (enumfacing == EnumFacing.DOWN) {
            return false;
        }
        else if (!world.p(blockpos).c().r().a()) {
            return false;
        }
        else {
            blockpos = blockpos.a(enumfacing);
            // CanaryMod: BlockPlaceHook
            CanaryBlock clicked = CanaryBlock.getPooledBlock(world.p(blockpos), blockpos, world); // Store Clicked
            clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
            //

            if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
                return false;
            }
            else if (!Blocks.an.c(world, blockpos)) {
                return false;
            }
            else if (world.D) {
                return true;
            }
            else {
                // CanaryMod: Create and call
                int i0 = MathHelper.c((double)((entityplayer.y + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                IBlockState iblockstate = Blocks.an.P().a(BlockStandingSign.a, i0);
                if (enumfacing != EnumFacing.UP) {
                    iblockstate = Blocks.ax.P().a(BlockWallSign.a, enumfacing);
                }

                if (new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, CanaryBlock.getPooledBlock(iblockstate, blockpos, world)).call().isCanceled()) {
                    return false;
                }
                world.a(blockpos, iblockstate, 3);
                //

                /* CanaryMod: Moved all but the world calls above
                if (enumfacing == EnumFacing.UP) {
                    int i0 = MathHelper.c((double)((entityplayer.y + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;

                    world.a(blockpos, Blocks.an.P().a(BlockStandingSign.a, Integer.valueOf(i0)), 3);
                }
                else {
                    world.a(blockpos, Blocks.ax.P().a(BlockWallSign.a, enumfacing), 3);
                }
                */
                //

                --itemstack.b;
                TileEntity tileentity = world.s(blockpos);

                if (tileentity instanceof TileEntitySign && !ItemBlock.a(world, blockpos, itemstack)) {
                    entityplayer.a((TileEntitySign)tileentity);
                }

                return true;
            }
        }
    }
}
