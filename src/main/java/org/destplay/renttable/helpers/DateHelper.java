package org.destplay.renttable.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.HashMap;

public class DateHelper {
    public static String calculateDateDifference(Date startDate, Date endDate) {
        long milliseconds = endDate.getTime() - startDate.getTime();
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        return days + " Дней " + hours + " часа " + minutes + " мин " + seconds + " сек";
    }
}
