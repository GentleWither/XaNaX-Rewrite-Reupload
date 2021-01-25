package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 */
public final class InventoryUtil implements Minecraft
{
    public static int getHotbarSlot(Item item)
    {
        for (int i = 0; i < 9; i++)
        {
            Item item1 = mc.player.inventory.getStackInSlot(i).getItem();

            if (item.equals(item1)) return i;
        }
        return -1;
    }

    public static int getHotbarSlot(Block block)
    {
        for (int i = 0; i < 9; i++)
        {
            Item item = mc.player.inventory.getStackInSlot(i).getItem();

            if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().equals(block)) return i;
        }

        return -1;
    }

    public static List<Integer> getInventorySlots(Item item)
    {
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < 36; i++)
        {
            Item item1 = mc.player.inventory.getStackInSlot(i).getItem();

            if (item.equals(item1)) ints.add(i);
        }
        return ints;
    }

    public static List<Integer> getInventorySlots(Block block)
    {
        List<Integer> ints = new ArrayList<>();
        for (int i = 9; i < 36; i++)
        {
            Item item = mc.player.inventory.getStackInSlot(i).getItem();

            if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().equals(block)) ints.add(i);
        }

        return ints;
    }

    public static void swapSlots(int slot1, int slot2)
    {
        if (slot1 == -1 || slot2 == -1) return;
        mc.playerController.windowClick(0, slot1, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, slot2, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, slot1, 0, ClickType.PICKUP, mc.player);
    }
}
