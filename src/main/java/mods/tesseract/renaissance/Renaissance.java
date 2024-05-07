package mods.tesseract.renaissance;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.BlockCake;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

@Mod(modid = Renaissance.MODID, version = Tags.VERSION, name = "MyMod", acceptedMinecraftVersions = "[1.7.10]")
public class Renaissance {

    public static final String MODID = "renaissance";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        Config.synchronizeConfiguration(e.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        Material.wood.requiresNoTool = !Config.disableWoodPunch;
        Material.snow.requiresNoTool = true;
        Material.craftedSnow.requiresNoTool = true;
        Material.web.requiresNoTool = true;
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        for (Object j : GameData.getItemRegistry()) {
            if (j instanceof ItemFood k) {
                if (k.maxStackSize == 64) {
                    k.setMaxStackSize(16);
                } else {
                    k.setMaxStackSize(4);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderGUI(RenderGameOverlayEvent.Pre e) {
        GuiIngameForge.renderFood = false;
    }

    @SubscribeEvent
    public void onUseItemFinish(PlayerUseItemEvent.Finish e) {
        ItemStack k = e.item;
        Item i = k.getItem();
        if (i instanceof ItemFood) {
            int a;
            if (i == Items.rotten_flesh) {
                a = 1;
            } else {
                a = ((ItemFood) i).healAmount;
            }
            e.entityPlayer.heal(a);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(LivingEvent.LivingUpdateEvent e) {
        EntityLivingBase p = e.entityLiving;
        if (p instanceof EntityPlayer) {
            FoodStats s = ((EntityPlayer) p).getFoodStats();
            if (s.getFoodLevel() != 6)
                s.setFoodLevel(6);
        }
    }

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start e) {
        EntityPlayer p = e.entityPlayer;
        Item i = e.item.getItem();
        if (i instanceof ItemFood && !((ItemFood) i).alwaysEdible && !canPlayerEat(p))
            e.setCanceled(true);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        EntityPlayer p = e.entityPlayer;
        if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && e.world.getBlock(e.x, e.y, e.z) instanceof BlockCake) {
            if (canPlayerEat(p))
                e.entityPlayer.heal(2);
            else
                e.setCanceled(true);
        }
    }

    public static boolean canPlayerEat(EntityPlayer p) {
        return p.getHealth() < p.getMaxHealth() && p.getActivePotionEffect(Potion.hunger) == null;
    }
}
