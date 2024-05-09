package mods.tesseract.renaissance;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean disableWoodPunch;
    public static int[] xpExpression;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        disableWoodPunch = configuration.getBoolean("disableWoodPunch", Configuration.CATEGORY_GENERAL, false, "禁止空手砍树");

        xpExpression = configuration.get("experience", "xpExpression", new int[]{30, 9, -158, 15, 5, -38, 0, 2, 7}, "经验计算公式，可分为多段，每段三个数字。每段中玩家升级所需的经验为 y=kx+b ，x 为玩家等级，则第一个数字代表从哪一级开始应用这个公式，第二个数字为 k 的值，第三个数字为 b 的值。经验等级更大的公式应该写在前面。如果某个等级没有计算公式，则默认为 47 点经验，约为从一级升到三十级的平均经验。你可以把这个列表里的所有数字都删除，这样你总是只要 47 点经验来升级。").getIntList();
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
