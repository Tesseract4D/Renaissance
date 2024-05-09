package mods.tesseract.renaissance.fix;

import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.FirstClassTransformer;

public class FixLoader extends CustomLoadingPlugin {
    public String[] getASMTransformerClass() {
        return new String[]{FirstClassTransformer.class.getName()};
    }

    public void registerFixes() {
        registerClassWithFixes("mods.tesseract.renaissance.fix.FixesCommon");
        registerClassWithFixes("mods.tesseract.renaissance.fix.FixesItemHoe");
        registerClassWithFixes("mods.tesseract.renaissance.fix.FixesEntityPlayer");
        registerClassWithFixes("mods.tesseract.renaissance.fix.FixesRepair");
    }
}
