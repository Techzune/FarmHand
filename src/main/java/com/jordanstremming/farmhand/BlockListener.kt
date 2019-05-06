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

    private val plugin = FarmHand.instance

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerInteract(event: PlayerInteractEvent) {

        // check if the event has been cancelled
        if (event.isCancelled) return

        // only handle block interactions
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        // get player
        val player = event.player

        // ignore sneaking player
        if (player.isSneaking && plugin.config.getBoolean("ignoreSneaking")) return

        // get the clicked block
        val block = event.clickedBlock ?: return

        // convert the block into "Crops"
        if (block.state.data !is Crops) return
        val crops = block.state.data as Crops

        // handle only ripe crops
        if (crops.state != CropState.RIPE) return

        // if specified, make sure acceptable crop is harvested
        if (plugin.config.getBoolean("acceptableCropsOnly")) {
            if (!plugin.config.getStringList("acceptableCrops").contains(block.type.toString())) {
                // cancel if not acceptable crop
                return
            }
        }

        // if specified, make sure acceptable tool is used
        if (plugin.config.getBoolean("acceptableToolsOnly")) {

            // check main hand tool
            val handTool = player.inventory.itemInMainHand.type.toString()
            if (!plugin.config.getStringList("acceptableTools").contains(handTool)) {
                // cancel if not acceptable hand tool
                return
            }

        }

        // store the material and drops
        val cropType = crops.itemType

        // check block permissions
        if (plugin.config.getBoolean("checkPermissionsToBreak")) {
            val blockBreakEvent = BlockBreakEvent(block, player)
            plugin.server.pluginManager.callEvent(blockBreakEvent)
            if (blockBreakEvent.isCancelled) return
        }

        // prevent placing blocks
        event.setUseItemInHand(Event.Result.DENY)

        // break the crop
        block.breakNaturally()

        // change to seeded type
        if (plugin.config.getBoolean("replantSeeds")) {
            block.type = cropType
            crops.state = CropState.SEEDED
        }
    }

}
