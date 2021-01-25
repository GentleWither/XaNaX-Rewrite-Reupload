package cat.yoink.xanax.internal.module.main;

import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.setting.Setting;

import java.util.List;

/**
 * @author yoink
 */
public interface IModule
{
    String[] getAliases();

    ModuleCategory getCategory();

    int getBind();

    void setBind(int bind);

    boolean isHidden();

    boolean noSave();

    List<Setting<?>> getSettings();
}
