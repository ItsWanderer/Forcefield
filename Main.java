package me.divemc.forcefield;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;



public class Main extends JavaPlugin implements Listener{

	public Main plugin;
	
	
	HashMap<Player, BukkitRunnable> run = new HashMap<>();
	
	
	

	public void onEnable(){
		System.out.println("[ForceField] Plugin version" + this.getDescription().getVersion() + " by" + this.getDescription().getAuthors() + " loaded!");
		
	
}
	@EventHandler
public void onInteract(PlayerInteractEvent e) {
	final Player p = e.getPlayer();
	if(e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK)  {
		if(e.getItem().getType() == Material.EYE_OF_ENDER) {
			e.setCancelled(true);
		
		if(run.containsKey(p)) {
			p.sendMessage(ChatColor.RED + "Protection deactivated!");
			run.get(p).cancel();
			run.remove(p);
		} else if(!run.containsKey(p))  {
			run.put(p, new BukkitRunnable() {
			  
				@Override
				public void run() {
					p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 3);
				}
			});
			run.get(p).runTaskTimer((Plugin) plugin, 20, 20);
			p.sendMessage(ChatColor.GREEN + "Protection activated!");
		    }
		}
	}
}

   @EventHandler
   public void onQuit(PlayerQuitEvent e) {
	if(run.containsKey(e.getPlayer())) {
		run.get(e.getPlayer()).cancel();
		run.remove(e.getPlayer());
	}
}
@EventHandler
public void onMove(PlayerMoveEvent e) {
	Player p = e.getPlayer();
	
	for(Player players :  run.keySet()) {
	    if(p != players) {
	    	if(p.getLocation().distance(players.getLocation()) <= 3) {
	    		
	    		
	    		double Ax = p.getLocation().getX();
	            double Ay = p.getLocation().getY();
	    		double Az = p.getLocation().getZ();
	    		
	    		double Bx = players.getLocation().getX();
	    		double By = players.getLocation().getY();
	    		double Bz = players.getLocation().getZ();
	    		
	    		double x = Ax - Bx;
	    		double y = Ay - By;
	    		double z = Az - Bz;
	    		Vector v = new Vector(x, y, z).normalize().multiply(1D).setY(1);
	            p.setVelocity(v);
	     }
	 }
}
	

    if(run.containsKey(p)) {
	for(Entity entity : p.getNearbyEntities(4, 4, 4)) {
		if(entity instanceof Player) {
			Player target = (Player) entity;
			if(p != target) {
				
				
				
			
			
				double Ax = p.getLocation().getX();
	            double Ay = p.getLocation().getY();
	    		double Az = p.getLocation().getZ();
	    		
	    		double Bx = target.getLocation().getX();
	    		double By = target.getLocation().getY();
	    		double Bz = target.getLocation().getZ();
	    		
	    		double x = Bx - Ax;
	    		double y = By - Ay;
	    		double z = Bz - Az;
	    		Vector v = new Vector(x, y, z).normalize().multiply(1D).setY(0.1D);
	            target.setVelocity(v);
				
		
			}
	    }
		}
	 }
    }
}
