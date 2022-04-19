package me.tyranzx.staffcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class StellarCore extends JavaPlugin implements CommandExecutor, Listener {

    private final String permiso = "permiso.permiso";
    private final ArrayList<CommandSender> staffchat = new ArrayList<>();
    private final Server server = Bukkit.getServer();

    public String c(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    @EventHandler
    public void staffchatActivated(AsyncPlayerChatEvent e) {
        if (!staffchat.contains(e.getPlayer())) return;

        e.setCancelled(true);
        e.getRecipients().clear();

        for (Player staff : server.getOnlinePlayers()) {
            Bukkit.getConsoleSender().sendMessage(c("&8[&aStaffChat&8] &f")+e.getPlayer().getName()+c(": &b")+e.getMessage());
            staff.sendMessage(c("&8[&aStaffChat&8] &f") + e.getPlayer().getName() + c(": &b") + e.getMessage());
        }
    }
    @Override
    public void onEnable() {
        this.getCommand("sc").setExecutor(this);
        server.getPluginManager().registerEvents(this, this);
    }
    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {
        if (!p.hasPermission(permiso)) {
            p.sendMessage(c("&cDebes ser un staff para usar este comando."));
            return true;
        }
        if (command.getName().equalsIgnoreCase("sc")){
            if (args.length == 0){
                if (!staffchat.contains(p)){
                    if (p instanceof Player) {
                        staffchat.add(p);
                        p.sendMessage(c("&7Staffchat &aactivado"));
                        return true;
                    } else {
                        p.sendMessage(c("&cNo puedes activar el modo staffchat por la consola."));
                    }
                } else {
                    staffchat.remove(p); p.sendMessage(c("&7Staffchat &cdesactivado"));
                }
            }
            if (args.length >= 1) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]);
                    if (i < args.length) {
                        sb.append(" ");
                    }
                }
                for (Player staff : server.getOnlinePlayers()){
                    if (staff.hasPermission("essentials.staff")){
                        if (p instanceof Player){
                            Bukkit.getConsoleSender().sendMessage(c("&8[&aStaffChat&8] &f")+p.getName()+c(": &b")+sb.toString());
                            staff.sendMessage(c("&8[&aStaffChat&8] &f")+p.getName()+c(": &b")+sb.toString());
                        }
                        else if (p instanceof ConsoleCommandSender){
                            Bukkit.getConsoleSender().sendMessage(c("&8[&aStaffChat&8] &e")+p.getName()+c(": &b")+sb.toString());
                            staff.sendMessage(c("&8[&aStaffChat&8] &e")+p.getName()+c(": &b")+sb.toString());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
