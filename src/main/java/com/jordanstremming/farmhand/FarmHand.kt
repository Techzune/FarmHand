package com.jordanstremming.farmhand

import org.bukkit.plugin.java.JavaPlugin

class FarmHand : JavaPlugin() {

	// the plugin's config
	val pluginConfig: Config = Config()

	override fun onEnable() {
		// register events
		server.pluginManager.registerEvents(BlockListener(), this)

		// save config file
		saveDefaultConfig()

		// yay!
		logger.info("FarmHand is ready to go!")
	}

	override fun onDisable() {
		logger.info("FarmHand says goodbye...")
	}

	// keep an instance of the plugin
	init {
		instance = this
	}

	companion object {
		lateinit var instance: FarmHand
	}

}
