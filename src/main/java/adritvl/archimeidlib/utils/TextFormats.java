package adritvl.archimeidlib.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormats {
    public static String color(String text){
        String[] versions = {"1.16", "1.17", "1.18", "1.19", "1.20"};
        boolean use = false;
        for(String ver : versions){
            if(Bukkit.getServer().getVersion().contains(ver)) {
                use = true;
                break;
            }
        }
        if(use) return text;
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]");
        Matcher match = pattern.matcher(text);
        while (match.find()){
            String color = text.substring(match.start(), match.end());
            text = text.replace(color, ChatColor.of(color)+"");

            match = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendAction(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(message)));
    }
    public static void sendMsg(Player player, String message){
        player.sendMessage(color(message));
    }

    /**Return a number formatted (100,000,000.000)*/
    public static String formatNumber(double number){
        DecimalFormat df = new DecimalFormat("###,###,###.##");
        return df.format(number);
    }

    public static int randomNum(int min, int max){
        Random r = new Random();
        int value = (int) (min + (Math.random() * (max - min) + 1));
        return r.nextInt(value);
    }
}
