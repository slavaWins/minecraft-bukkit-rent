package org.destplay.renttable.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.destplay.renttable.handles.SignRegisterHandle;
import org.destplay.renttable.handles.SignRentHandle;


public class SignListener implements Listener {






    @org.bukkit.event.EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.OAK_SIGN) {
            Sign sign = (Sign) block.getState();
            String[] lines = sign.getLines();

            if (lines.length > 0 && lines[0].equalsIgnoreCase(ChatColor.AQUA + "[АРЕНДА]")) {
                SignRentHandle.InteractRentTable(player, lines);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public  void  onSignBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.OAK_SIGN) {

            Sign sign = (Sign) block.getState();
            String[] lines = sign.getLines();

            if (lines.length > 0 && lines[0].equalsIgnoreCase(ChatColor.AQUA + "[АРЕНДА]")) {
                SignRegisterHandle.UnRegister(player, lines, sign);
            }
        }

    }

    @org.bukkit.event.EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();


        if (block.getType() == Material.OAK_SIGN) {

            Sign sign = (Sign) block.getState();
            String[] lines = event.getLines();

            if (lines.length > 0 && ( lines[0].equalsIgnoreCase("[RENT]")  ||  lines[0].equalsIgnoreCase("[R]")  ||  lines[0].equalsIgnoreCase("[r]")  )) {
           SignRegisterHandle.Register(player, lines, event);
            }
        }
    }
}