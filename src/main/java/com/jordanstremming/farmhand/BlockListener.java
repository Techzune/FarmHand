package com.jordanstremming.farmhand;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Crops;

public class BlockListener implements Listener {

	private FarmHand plugin = FarmHand.getPlugin();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		// check if the event has been cancelled
		if (event.isCancelled()) return;

		// only handle block interactions
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		// get player
		Player player = event.getPlayer();

		// ignore sneaking player
		if (player.isSneaking()) return;

		// prevent placing blocks
		event.setUseItemInHand(Event.Result.DENY);

		// get the clicked block
		Block block = event.getClickedBlock();
		if (block == null) return;

		// convert the block into "Crops"
		if (!(block.getState().getData() instanceof Crops)) return;
		Crops crops = (Crops) block.getState().getData();

		// handle ripe crops
		if (crops.getState() == CropState.RIPE) {

			// if specified, make sure acceptable tool is used
			if (plugin.getConfig().getBoolean("acceptableToolsOnly")) {

				// check main hand tool
				String handTool = player.getInventory().getItemInMainHand().getType().toString();
				if (!plugin.getConfig().getStringList("acceptableTools").contains(handTool)) {
					// cancel if not acceptable
					return;
				}

			}

			// store the material and drops
			Material cropType = crops.getItemType();

			// check block permissions
			if (plugin.getConfig().getBoolean("checkPermissionsToBreak")) {
				BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
				plugin.getServer().getPluginManager().callEvent(blockBreakEvent);
				if (blockBreakEvent.isCancelled()) return;
			}

			// break the crop
			block.breakNaturally();

			// change to seeded type
			if (plugin.getConfig().getBoolean("replantSeeds")) {
				block.setType(cropType);
				crops.setState(CropState.SEEDED);
			}
		}

	}

}
