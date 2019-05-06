package com.jordanstremming.farmhand

import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration

abstract class Config : FileConfiguration() {

	/**
	 * if "acceptableToolsOnly" enabled in config
	 */
	val acceptableToolsOnly
		get() = getBoolean("acceptableToolsOnly")

	/**
	 * if material defined in "acceptableTools" in config
	 */
	fun isAcceptableTool(tool: Material): Boolean
			  = getStringList("acceptableTools").contains(tool.toString())

	/**
	 * if "acceptableCropsOnly" enabled in config
	 */
	val acceptableCropsOnly
		get() = getBoolean("acceptableCropsOnly")

	/**
	 * if material defined in "acceptableCrops" in config
	 */
	fun isAcceptableCrop(crop: Material): Boolean
			  = getStringList("acceptableCrops").contains(crop.toString())

	/**
	 * if "checkPermissionsToBreak" enabled in config
	 */
	val checkPermissionsToBreak
		get() = getBoolean("checkPermissionsToBreak")

	/**
	 * if "ignoreSneaking" enabled in config
	 */
	val ignoreSneaking
		get() = getBoolean("ignoreSneaking")

	/**
	 * if "replantSeeds" enabled in config
	 */
	val replantSeeds
		get() = getBoolean("replantSeeds")

}
