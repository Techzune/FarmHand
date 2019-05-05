package com.jordanstremming.farmhand;

import org.bukkit.plugin.java.JavaPlugin;

public class FarmHand extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("FarmHand is ready to go!");
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
	}

	@Override
	public void onDisable() {
		getLogger().info("FarmHand says goodbye...");
	}
}
