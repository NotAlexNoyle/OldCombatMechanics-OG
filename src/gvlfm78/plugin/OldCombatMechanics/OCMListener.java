package kernitus.plugin.OldCombatMechanics;


import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class OCMListener implements Listener{

	private OCMMain plugin;
	public OCMListener(OCMMain instance){
		this.plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent e){
		OCMUpdateChecker updateChecker = new OCMUpdateChecker(plugin);
		Player p = e.getPlayer();

		//Checking for updates
		if(p.isOp()){
			if(plugin.getConfig().getBoolean("update-checker")){
				updateChecker.updateNeeded();
				if(plugin.getConfig().getBoolean("settings.checkForUpdates")){
					if(updateChecker.updateNeeded()){
						p.sendMessage("An update for OldCombatMechanics to version " + updateChecker.getVersion()+"is available!");
						p.sendMessage("Click here to download it:"+updateChecker.getLink());
					}
				}
			}
		}

		AttributeInstance attribute = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
		double baseValue= attribute.getBaseValue();
		if(baseValue!=1024){
			attribute.setBaseValue(1024);
			p.saveData();
		}
	}
}