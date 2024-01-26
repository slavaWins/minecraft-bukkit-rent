package org.destplay.renttable.views;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.helpers.DateHelper;

import java.util.Date;

public class RentChatView {

    public static void ElementList(Player player, RentModel rent) {

        String msg = "";

        if(rent.IsLocked()) {
            msg += ChatColor.GRAY + "[ЗАНЯТО] "+   ChatColor.RESET;
        }else {
            msg += ChatColor.AQUA + "[СВОБДНО] "+   ChatColor.RESET;
        }


        msg += rent.region;

        if (rent.IsLocked()) {
            msg += " Владелец: " + org.bukkit.ChatColor.GREEN + rent.currentRentLogin + "  Истекает через: " +   ChatColor.GOLD + DateHelper.calculateDateDifference(new Date(), rent.currentRentTo);
        }

        TextComponent message = new TextComponent(msg);


        if(player.isOp()) {
            TextComponent btnTp = new TextComponent("  [TP]");
            btnTp.setColor(ChatColor.AQUA);
            btnTp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Телепортироваться к зданию")));
            btnTp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/rent tp " + rent.region));
            message.addExtra(btnTp);
        }

        player.spigot().sendMessage(message);


    }
}
