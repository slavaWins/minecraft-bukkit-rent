package org.destplay.renttable.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class VaultHelper {
    public static int getGoldIngotCount(Player player) {
        ItemStack[] items = player.getInventory().getContents();
        int count = 0;

        for (ItemStack item : items) {
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                count += item.getAmount();
            }
        }

        return count;
    }


    public static void takeGoldIngots(Player player, int amount) {
        ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT, amount);
        HashMap<Integer, ItemStack> remainingItems = player.getInventory().removeItem(goldIngot);

        if (!remainingItems.isEmpty()) {
            for (ItemStack item : remainingItems.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }
}
