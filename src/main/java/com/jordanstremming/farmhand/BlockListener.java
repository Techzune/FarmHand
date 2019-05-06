package com.jordanstremming.farmhand;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Crops;

public class BlockListener implements Listener {

	private final FarmHand plugin = FarmHand.getPlugin();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {

		// check if the event has been cancelled
		if (event.isCancelled()) return;

		// only handle block interactions
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		// get player
		Player player = event.getPlayer();

		// ignore sneaking player
		if (player.isSneaking() && plugin.getConfig().getBoolean("ignoreSneaking")) return;

		// get the clicked block
		Block block = event.getClickedBlock();
		if (block == null) return;

		// convert the block into "Crops"
		if (!(block.getState().getData() instanceof Crops)) return;
		Crops crops = (Crops) block.getState().getData();

		// handle only ripe crops
		if (crops.getState() != CropState.RIPE) return;

		// if specified, make sure acceptable crop is harvested
		if (plugin.getConfig().getBoolean("acceptableCropsOnly")) {
			if (!plugin.getConfig().getStringList("acceptableCrops").contains(block.getType().toString())) {
				// cancel if not acceptable crop
				return;
			}
		}

		// if specified, make sure acceptable tool is used
		if (plugin.getConfig().getBoolean("acceptableToolsOnly")) {

			// check main hand tool
			String handTool = player.getInventory().getItemInMainHand().getType().toString();
			if (!plugin.getConfig().getStringList("acceptableTools").contains(handTool)) {
				// cancel if not acceptable hand tool
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

		// prevent placing blocks
		event.setUseItemInHand(Event.Result.DENY);

		// break the crop
		block.breakNaturally();

		// change to seeded type
		if (plugin.getConfig().getBoolean("replantSeeds")) {
			block.setType(cropType);
			crops.setState(CropState.SEEDED);
		}
	}

}
