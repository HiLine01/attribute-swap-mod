package com.hiline.attributeswap.hud;

import com.hiline.attributeswap.config.ConfigManager;
import com.hiline.attributeswap.swap.SwapManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class HudRenderer implements HudRenderCallback {
	@Override
	public void onHudRender(DrawContext drawContext, float tickDelta) {
		if (SwapManager.isSwapping()) {
			int currentProfile = ConfigManager.getCurrentProfile();
			String text = "§6Attribute Swap: §aProfile #" + (currentProfile + 1);
			
			drawContext.drawText(
				null,
				Text.literal(text),
				10,
				10,
				0x00FF00,
				true
			);
		}
	}
}
