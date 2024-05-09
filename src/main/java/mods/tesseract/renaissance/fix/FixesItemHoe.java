package mods.tesseract.renaissance.fix;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;


public class FixesItemHoe {
    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static int getItemEnchantability(ItemHoe c) {
        return c.theToolMaterial.getEnchantability();
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static Multimap<String, AttributeModifier> getItemAttributeModifiers(ItemHoe c) {
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Tool modifier", c.theToolMaterial.getDamageVsEntity() + 1, 0));
        return multimap;
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static boolean getIsRepairable(ItemHoe c, ItemStack k, ItemStack l) {
        ItemStack mat = c.theToolMaterial.getRepairItemStack();
        return mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, l, false);
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static boolean hitEntity(ItemHoe c, ItemStack stack, EntityLivingBase a, EntityLivingBase b) {
        stack.damageItem(2, b);
        return true;
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static boolean onBlockDestroyed(ItemHoe c, ItemStack stack, World worldIn, Block blockIn, int x, int y, int z, EntityLivingBase p) {
        if ((double) blockIn.getBlockHardness(worldIn, x, y, z) != 0.0D) {
            stack.damageItem(1, p);
        }

        return true;
    }

    @Fix(returnSetting = EnumReturnSetting.ALWAYS, createNewMethod = true)
    public static float func_150893_a(ItemHoe c, ItemStack k, Block b) {
        Material m = b.getMaterial();
        return (m == Material.grass || m == Material.ground || m == Material.plants || m == Material.cactus|| m == Material.leaves || m == Material.vine || m == Material.sponge) ? (float) (c.theToolMaterial.getEfficiencyOnProperMaterial() * 0.8) : 1;
    }
}
