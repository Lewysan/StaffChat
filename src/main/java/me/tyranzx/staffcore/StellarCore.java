package me.tyranzx.staffcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class StellarCore extends JavaPlugin implements CommandExecutor {

    String permiso = "permiso.permiso";

    @Override
    public void onEnable() {
        this.getCommand("sc").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!p.hasPermission(permiso)) {
            p.sendMessage(c("&cDebes ser un staff para usar este comando."));
            return true;
        }
        if (command.getName().equalsIgnoreCase("sc")){
            if (args.length == 0){
                p.sendMessage(c("&cDebes especificar un mensaje."));
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if(i < args.length) {
                    sb.append(" ");
                }
            }
            for (Player online : Bukkit.getServer().getOnlinePlayers()){
                if (online.hasPermission(permiso)){
                    online.sendMessage(c("&e[StaffChat] &c")+p.getName()+c(": &b")+sb.toString());
                    return true;
                }
            }
        }
        return false;
    }
    public String c(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
