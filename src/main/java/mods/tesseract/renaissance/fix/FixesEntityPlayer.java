package mods.tesseract.renaissance.fix;

import mods.tesseract.renaissance.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;

public class FixesEntityPlayer {
    @Fix(returnSetting = EnumReturnSetting.ALWAYS)
    public static int xpBarCap(EntityPlayer c) {
        for (int i = 0; i < Config.xpExpression.length; i += 3) {
            if (c.experienceLevel >= Config.xpExpression[i])
                return c.experienceLevel * Config.xpExpression[i + 1] + Config.xpExpression[i + 2];
        }
        return 47;
    }
}
