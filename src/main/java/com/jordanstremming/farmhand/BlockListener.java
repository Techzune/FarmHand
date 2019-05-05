package com.jordanstremming.farmhand;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Crops;

public class BlockListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// only handle block interactions
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		// get the clicked block
		Block block = event.getClickedBlock();
		if (block == null) {
			return;
		}

		// convert the block into "Crops"
		if (!(block.getState().getData() instanceof Crops)) return;
		Crops crops = (Crops) block.getState().getData();

		// handle ripe crops
		if (crops.getState() == CropState.RIPE) {

			// store the material and drops
			Material cropType = crops.getItemType();

			// break the crop
			block.breakNaturally();

			// change to seeded type
			block.setType(cropType);
			crops.setState(CropState.SEEDED);
		}
	}
}
