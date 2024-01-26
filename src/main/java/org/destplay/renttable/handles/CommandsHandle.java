package org.destplay.renttable.handles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.destplay.renttable.ConfigHelper;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.helpers.ChatHelper;
import org.destplay.renttable.repositories.RegionsRepository;
import org.destplay.renttable.views.RentChatView;

import java.util.List;

public class CommandsHandle {
    public static void TpTo(CommandSender sender, String region) {



        RentModel rentModel = RegionsRepository.FindByRegion(region);
        if(rentModel==null){
            sender.sendMessage(ChatHelper.PREFIX + "Не найден такой регион" );
            return;
        }

        Player player = (Player) sender;

        World tWorld = Bukkit.getServer().getWorld(rentModel.world);
        Location loc = new Location(tWorld, rentModel.x, rentModel.y, rentModel.z);


        if(ConfigHelper.IsDebug()) {
            System.out.println(ChatColor.YELLOW + ChatHelper.PREFIX + " to ПОЗ:" + loc.getX());
        }


        player.teleport(loc);

    }

    public static void ListCommand(CommandSender sender, String login) {

        ChatHelper.CoppyCmd((Player) sender, "GAVNO");

        List<RentModel> regions = RegionsRepository.Get();

        if (login.isEmpty()) {
            sender.sendMessage(ChatHelper.PREFIX + " Все зареганные аренды на серве: " + regions.size()+" шт.");
        }else {
            sender.sendMessage(ChatHelper.PREFIX + " Ваши аренды: " );
        }

        for (int i = 0; i < regions.size(); i++) {
            RentModel map = regions.get(i);

            if (!login.isEmpty()) {
                if (!map.currentRentLogin.equalsIgnoreCase(login)) continue;
            }

            RentChatView.ElementList((Player) sender, map);
/*
            String msg = "";
            if (map.IsLocked()) {
                msg += ChatColor.RED + "[Занято] ";
            } else {
                msg += ChatColor.GRAY + "[СВОБОДНО] ";
            }

            msg += ChatColor.RESET + map.region + ChatColor.RESET + " цена " + map.price + ChatColor.GRAY;

            if (map.IsLocked()) {
                msg += " Владелец: " + ChatColor.GREEN + map.currentRentLogin + " \n Истекает через: " + DateHelper.calculateDateDifference(new Date(), map.currentRentTo);
            }


            sender.sendMessage(msg);*/
        }

    }
}
