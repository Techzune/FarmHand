package com.jordanstremming.farmhand

import org.bukkit.plugin.java.JavaPlugin

class FarmHand : JavaPlugin() {

    // keep an instance of the plugin
    init {
        instance = this
    }

    companion object {
        lateinit var instance: FarmHand
    }

    override fun onEnable() {
        // register events
        server.pluginManager.registerEvents(BlockListener(), this)

        // load configuration
        loadConfig()

        // yay!
        logger.info("FarmHand is ready to go!")
    }

    override fun onDisable() {
        logger.info("FarmHand says goodbye...")
    }

    private fun loadConfig() {
        saveDefaultConfig()
    }


}
