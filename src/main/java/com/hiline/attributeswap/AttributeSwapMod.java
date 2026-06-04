package com.hiline.attributeswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import com.hiline.attributeswap.config.ConfigManager;
import com.hiline.attributeswap.hud.HudRenderer;
import com.hiline.attributeswap.keybind.KeyBindings;
import com.hiline.attributeswap.swap.SwapManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeSwapMod implements ClientModInitializer {
	public static final String MOD_ID = "attributeswap";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Attribute Swap mod");
		
		ConfigManager.loadConfig();
		KeyBindings.register();
		HudRenderCallback.EVENT.register(new HudRenderer());
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (KeyBindings.TOGGLE_SWAP.wasPressed()) {
				SwapManager.toggleSwap();
			}
			while (KeyBindings.OPEN_GUI.wasPressed()) {
				SwapManager.openGui();
			}
		});
		
		LOGGER.info("Attribute Swap mod initialized successfully");
	}
}
