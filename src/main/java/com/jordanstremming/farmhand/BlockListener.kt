package com.jordanstremming.farmhand

import org.bukkit.CropState
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.material.Crops

class BlockListener : Listener {

	// store plugin instances
	private val plugin = FarmHand.instance
	private val config = plugin.pluginConfig

	@EventHandler(priority = EventPriority.MONITOR)
	fun onPlayerInteract(event: PlayerInteractEvent) {

		// check if the event has been cancelled
		if (event.isCancelled)
			return

		// only handle block interactions
		if (event.action != Action.RIGHT_CLICK_BLOCK)
			return

		// get player
		val player = event.player

		// ignore sneaking player
		if (player.isSneaking && config.ignoreSneaking)
			return

		// get the clicked block
		val block = event.clickedBlock ?: return
		val blockType = block.type

		// convert the block into "Crops"
		val crops = block.state.data as? Crops ?: return

		// handle only ripe crops
		if (crops.state != CropState.RIPE)
			return

		// if config, make sure acceptable crop is harvested
		if (config.acceptableCropsOnly && !config.isAcceptableCrop(blockType))
			return

		// if config, make sure acceptable tool is used
		if (config.acceptableToolsOnly && !config.isAcceptableTool(player.inventory.itemInMainHand.type))
			return

		// if config, check block permissions
		if (config.checkPermissionsToBreak) {
			val blockBreakEvent = BlockBreakEvent(block, player)
			plugin.server.pluginManager.callEvent(blockBreakEvent)
			if (blockBreakEvent.isCancelled) return
		}

		// prevent placing blocks
		event.setUseItemInHand(Event.Result.DENY)

		// break the crop
		block.breakNaturally()

		// if config, change block to seed
		if (config.replantSeeds) {
			block.type = blockType
			crops.state = CropState.SEEDED
		}
	}

}
