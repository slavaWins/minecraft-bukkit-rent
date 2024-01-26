package org.destplay.renttable.helpers;

import static org.bukkit.Bukkit.getServer;

public class RgClaimHelper {
    public static void AddMember(String region, String player) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "rg addmember -w \"world\" "+region+" "+player);
    }

    public static void RemoveMember(String region, String player) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "rg removemember -w \"world\" "+region+" "+player);
    }


    public static void FixToRent(String region) {
        getServer().dispatchCommand(getServer().getConsoleSender(),  "/rg flag -w \"world\" -h 5 "+region+" game-mode survival");
    }
}
