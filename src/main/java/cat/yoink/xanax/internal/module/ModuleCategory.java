package cat.yoink.xanax.internal.module;

/**
 * @author yoink
 */
public enum ModuleCategory
{
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    WORLD("World"),
    RENDER("Render"),
    MISC("Misc"),
    CLIENT("Client");

    private final String name;

    ModuleCategory(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
