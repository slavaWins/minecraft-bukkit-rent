package org.destplay.renttable.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.destplay.renttable.ConfigHelper;

import java.util.HashMap;

public class VaultHelper {
    public static String ValuteName() {
        return ConfigHelper.GetConfig().getString("valute-name");
    }
    public static Material ValuteId() {
        return Material.valueOf(ConfigHelper.GetConfig().getString("valute"));
    }

    public static String GetDefualtValute() {
        return  Material.GOLD_INGOT.name();
    }

    public static int getGoldIngotCount(Player player) {
        ItemStack[] items = player.getInventory().getContents();
        int count = 0;

        for (ItemStack item : items) {
            if (item != null && item.getType() == ValuteId()) {
                count += item.getAmount();
            }
        }

        return count;
    }


    public static void takeGoldIngots(Player player, int amount) {
        ItemStack goldIngot = new ItemStack(ValuteId(), amount);
        HashMap<Integer, ItemStack> remainingItems = player.getInventory().removeItem(goldIngot);

        if (!remainingItems.isEmpty()) {
            for (ItemStack item : remainingItems.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }
}
