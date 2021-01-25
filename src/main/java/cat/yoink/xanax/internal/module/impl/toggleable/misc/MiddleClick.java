package cat.yoink.xanax.internal.module.impl.toggleable.misc;

import cat.yoink.xanax.internal.other.FriendManager;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.annotation.Setting;
import cat.yoink.xanax.internal.setting.annotation.types.List;
import cat.yoink.xanax.internal.util.ChatUtil;
import cat.yoink.xanax.internal.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

/**
 * @author yoink
 */
@ModuleData(
        name = "MiddleClick",
        aliases = {"MiddleClick", "Scroll"},
        category = ModuleCategory.MISC,
        description = "Changes how your scroll button functions"
)
public final class MiddleClick extends StateModule
{
    @Setting(name = "Mode", list = @List({"Smart", "Friend", "Pearl"}))
    public String mode = "Smart";

    @SubscribeEvent
    public void onInputMouseInput(InputEvent.MouseInputEvent event)
    {
        if (isSafe() && Mouse.getEventButtonState() && Mouse.getEventButton() == 2)
        {
            if (mode.equals("Friend") && mc.objectMouseOver.entityHit instanceof EntityPlayer) clickFriend();
            else if (mode.equals("Pearl")) throwPearl();
            else if (mode.equals("Smart"))
            {
                if (mc.objectMouseOver.entityHit instanceof EntityPlayer) clickFriend();
                else throwPearl();
            }
        }
    }

    private void clickFriend()
    {
        String name = mc.objectMouseOver.entityHit.getName();

        if (FriendManager.INSTANCE.isFriend(name))
        {
            FriendManager.INSTANCE.remove(name);
            ChatUtil.sendPrivateMessage("Removed " + name + " as a friend.");
        }
        else
        {
            FriendManager.INSTANCE.add(name);
            ChatUtil.sendPrivateMessage("Added " + name + " as a friend.");
        }
    }

    private void throwPearl()
    {
        int pearlSlot = InventoryUtil.getHotbarSlot(Items.ENDER_PEARL);
        if (pearlSlot != -1)
        {
            int slot = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = pearlSlot;
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
            mc.player.inventory.currentItem = slot;
        }
    }
}
