package org.destplay.renttable.handles;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.destplay.renttable.ConfigHelper;
import org.destplay.renttable.contracts.RentModel;
import org.destplay.renttable.helpers.VaultHelper;
import org.destplay.renttable.repositories.RegionsRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SignCreateHandle {


    public static void CreateRentTable(Player player, String[] lines, SignChangeEvent signChangeEvent)  {

        Sign sign = (Sign) signChangeEvent.getBlock().getState();

        if (lines.length < 3) {
            player.sendMessage("В табличке должно быть указано [RENT] \n 10 \n RegionName");
            return;
        }


        List<RentModel> regions = RegionsRepository.Get();


        for (int i = 0; i < regions.size(); i++) {
            RentModel map = regions.get(i);

            if (map.region.equalsIgnoreCase(lines[2])) {
                player.sendMessage(ChatColor.DARK_RED + " Такой регион уже зареган в рентах");
                return;
            }
        }


        int amountGolds = 0;
        try {
            amountGolds = Integer.parseInt(lines[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.DARK_RED + "Не удалось считать сумму таблички");
            System.out.println("Неверный формат числа");
            return;
        }



        RentModel reg = new RentModel();
        reg.currentRentLogin = player.getDisplayName();
        reg.region = lines[2];
        reg.price = amountGolds;
        reg.currentRentTo =  new Date();
        regions.add(reg);

        try {
            RegionsRepository.Save(regions);
        } catch (IOException e) {
            e.printStackTrace();
        }




        signChangeEvent.setLine(0, ChatColor.AQUA + "[АРЕНДА]"); // Записываем ник игрока во вторую строку таблички
        signChangeEvent.setLine(1, (amountGolds * ConfigHelper.GetConfig().getInt("valute-view-coficient", 1)) + " " + VaultHelper.ValuteName()+  "/час");
        signChangeEvent.setLine(2, reg.region); // Записываем ник игрока во вторую строку таблички
        //signChangeEvent.setLine(3, "Свободно"); // Записываем ник игрока во вторую строку таблички
        sign.update(); // Обновляем табличку, чтобы изменения вступили в силу
        player.sendMessage("Вы успешно поставили табличку аренды");

    }
}
