package org.destplay.renttable.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.destplay.renttable.ConfigHelper;

public final class ExampleListener implements Listener {

    @org.bukkit.event.EventHandler
    public  void  onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        player.sendMessage(ConfigHelper.GetConfig().getString("join-mess").replace("@p", player.getDisplayName()));

    }

    @org.bukkit.event.EventHandler
    public  void  onJoin(BlockBreakEvent e){

        Player player = e.getPlayer();

        player.sendMessage("[RentTable] Block damage Player " + player.getDisplayName());

    }


}