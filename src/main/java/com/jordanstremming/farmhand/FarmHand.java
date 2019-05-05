package com.jordanstremming.farmhand;

import org.bukkit.plugin.java.JavaPlugin;

public class FarmHand extends JavaPlugin {

	// keep an instance on hand
	private static FarmHand instance;

	@Override
	public void onEnable() {
		instance = this;

		// register events
		getServer().getPluginManager().registerEvents(new BlockListener(), this);

		// load configuration
		loadConfig();

		// yay!
		getLogger().info("FarmHand is ready to go!");
	}

	@Override
	public void onDisable() {
		getLogger().info("FarmHand says goodbye...");
	}

	private void loadConfig() {
		saveDefaultConfig();
	}

	public static FarmHand getPlugin() {
		return instance;
	}
}
