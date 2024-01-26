package org.destplay.renttable.helpers;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatHelper {

    public static String PREFIX = ChatColor.YELLOW + "[АРЕНДА] " +ChatColor.RESET;

    public static String ClickCable(String cmd, String btn){
        return cmd;
    }

    public static String CoppyCmd(Player player, String cmd){

        TextComponent message = new TextComponent("Need help?");
        message.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("CLick MEE!!")));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd));

        player.spigot().sendMessage(message);

        return cmd;
    }

}
