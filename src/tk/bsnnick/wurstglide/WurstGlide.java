package tk.bsnnick.wurstglide;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WurstGlide extends JavaPlugin implements Listener
{
    private String _prefix = ChatColor.RED + "[WurstGlide] " + ChatColor.GRAY;
    
    private List<Player> _staff;
    
    public void onEnable()
    {
        _staff = new ArrayList<Player>();
        
        this.getServer().getPluginManager().registerEvents(this, this);
    }
    
    public void onDisable()
    {
        _staff.stream().forEach(players -> players.sendMessage(_prefix + ""));
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        if (event.getPlayer().hasPermission("wurstglide.staff"))
        {
            _staff.add(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        if (_staff.contains(event.getPlayer()))
        {
            _staff.remove(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onWurstGlide(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        
        if (player.isOnGround())
        {
            return;
        }
        
        if (player.isInsideVehicle())
        {
            return;
        }
        
        if (player.getAllowFlight())
        {
            return;
        }
        
        if ((event.getTo().getY() - event.getFrom().getY()) == -0.125D)
        {
            player.setVelocity(new Vector(0, -2, 0));
            
            _staff.stream().forEach(players -> players.sendMessage(_prefix + event.getPlayer().getName() + " may be using glide hacks!"));
        }
    }
}
