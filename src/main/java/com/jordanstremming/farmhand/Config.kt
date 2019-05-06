package com.jordanstremming.farmhand

import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration

abstract class Config : FileConfiguration() {

	val acceptableToolsOnly
		get() = getBoolean("acceptableToolsOnly")

	fun isAcceptableTool(tool: Material): Boolean
			  = getStringList("acceptableTools").contains(tool.toString())

	val acceptableCropsOnly
		get() = getBoolean("acceptableCropsOnly")

	fun isAcceptableCrop(crop: Material): Boolean
			  = getStringList("acceptableCrops").contains(crop.toString())

	val checkPermissionsToBreak
		get() = getBoolean("checkPermissionsToBreak")

	val ignoreSneaking
		get() = getBoolean("ignoreSneaking")

	val replantSeeds
		get() = getBoolean("replantSeeds")

}
