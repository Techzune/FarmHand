package com.jordanstremming.farmhand

import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration

class Config {

	private val config: FileConfiguration
		get() = FarmHand.instance.config

	/**
	 * if "acceptableToolsOnly" enabled in config
	 */
	val acceptableToolsOnly
		get() = config.getBoolean("acceptableToolsOnly")

	/**
	 * if material defined in "acceptableTools" in config
	 */
	fun isAcceptableTool(tool: Material): Boolean
			  = config.getStringList("acceptableTools").contains(tool.toString())

	/**
	 * if "acceptableCropsOnly" enabled in config
	 */
	val acceptableCropsOnly
		get() = config.getBoolean("acceptableCropsOnly")

	/**
	 * if material defined in "acceptableCrops" in config
	 */
	fun isAcceptableCrop(crop: Material): Boolean
			  = config.getStringList("acceptableCrops").contains(crop.toString())

	/**
	 * if "checkPermissionsToBreak" enabled in config
	 */
	val checkPermissionsToBreak
		get() = config.getBoolean("checkPermissionsToBreak")

	/**
	 * if "ignoreSneaking" enabled in config
	 */
	val ignoreSneaking
		get() = config.getBoolean("ignoreSneaking")

	/**
	 * if "replantSeeds" enabled in config
	 */
	val replantSeeds
		get() = config.getBoolean("replantSeeds")

}
