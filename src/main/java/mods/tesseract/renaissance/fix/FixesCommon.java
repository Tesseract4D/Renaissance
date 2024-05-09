package mods.tesseract.renaissance.fix;

import mods.tesseract.renaissance.Renaissance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

import java.lang.reflect.Method;

public class FixesCommon {
    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static ItemStack onEaten(ItemSoup c, ItemStack stack, World world, EntityPlayer player) throws Exception {
        ItemStack b = new ItemStack(Items.bowl);
        Method m = ItemFood.class.getDeclaredMethod("onFoodEaten", ItemStack.class, World.class, EntityPlayer.class);
        m.setAccessible(true);
        m.invoke(c, stack, world, player);
        if (!player.capabilities.isCreativeMode) {
            --stack.stackSize;
            if (stack.stackSize <= 0) {
                return b;
            }
            player.inventory.addItemStackToInventory(b);
        }
        return stack;
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static float blockStrength(ForgeHooks c, Block block, EntityPlayer player, World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        float hardness = block.getBlockHardness(world, x, y, z);
        if (hardness < 0.0F) {
            return 0.0F;
        }

        if (ForgeHooks.canHarvestBlock(block, player, metadata)) {
            return player.getBreakSpeed(block, false, metadata, x, y, z) / hardness / 30F;
        } else {
            return 0;
        }
    }

    @Fix(returnSetting = EnumReturnSetting.ON_TRUE)
    public static boolean canHarvestBlock(ForgeHooks c, Block block, EntityPlayer player, int metadata) {
        if (block.getMaterial() == Material.wood && !(block instanceof BlockLog))
            return true;
        return false;
    }
}
