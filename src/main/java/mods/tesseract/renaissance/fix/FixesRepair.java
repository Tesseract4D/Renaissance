package mods.tesseract.renaissance.fix;

import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

public class FixesRepair {
    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static boolean getIsRepairable(ItemBow c, ItemStack k, ItemStack l) {
        return OreDictionary.itemMatches(new ItemStack(Items.string), l, false);
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static boolean getIsRepairable(ItemFishingRod c, ItemStack k, ItemStack l) {
        return OreDictionary.itemMatches(new ItemStack(Items.string), l, false);
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static int getRepairCost(ItemStack c) {
        return -1;
    }
}
