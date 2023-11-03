package adritvl.archimeidlib.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    public static void spawnHologram(String text, Location location, double duration){

    }
}
