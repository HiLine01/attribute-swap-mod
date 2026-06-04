package com.hiline.attributeswap.swap;

import com.hiline.attributeswap.AttributeSwapMod;
import com.hiline.attributeswap.config.ConfigManager;
import com.hiline.attributeswap.config.SwapRule;
import com.hiline.attributeswap.gui.AttributeSwapScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SwapManager {
	private static final Map<Integer, Boolean> ruleStates = new HashMap<>();
	private static boolean isSwapping = false;

	public static void toggleSwap() {
		isSwapping = !isSwapping;
		ConfigManager.setCurrentProfile(isSwapping ? 0 : -1);
		AttributeSwapMod.LOGGER.info("Swap toggled: " + isSwapping);
	}

	public static void executeSwap(SwapRule rule) {
		if (!rule.enabled) return;

		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) return;

		int hotbarSize = 9;
		ItemStack targetStack = null;
		int targetSlot = -1;

		for (int i = 0; i < hotbarSize; i++) {
			ItemStack stack = client.player.getInventory().getStack(i);
			if (!stack.isEmpty() && getItemId(stack).contains(rule.fromItemId)) {
				targetStack = stack;
				targetSlot = i;
				break;
			}
		}

		if (targetSlot != -1 && targetStack != null) {
			try {
				Identifier toItemId = new Identifier(rule.toItemId);
				ItemStack newStack = new ItemStack(Registries.ITEM.get(toItemId));
				newStack.setCount(targetStack.getCount());

				if (targetStack.hasNbt()) {
					newStack.setNbt(targetStack.getNbt().copy());
				}

				client.player.getInventory().setStack(targetSlot, newStack);
				AttributeSwapMod.LOGGER.info("Swapped: " + rule.fromItemId + " -> " + rule.toItemId);
			} catch (Exception e) {
				AttributeSwapMod.LOGGER.error("Failed to swap items", e);
			}
		}
	}

	public static void openGui() {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client != null) {
			client.setScreen(new AttributeSwapScreen());
		}
	}

	public static boolean isSwapping() {
		return isSwapping;
	}

	public static void setRuleState(int ruleIndex, boolean state) {
		ruleStates.put(ruleIndex, state);
	}

	public static boolean getRuleState(int ruleIndex) {
		return ruleStates.getOrDefault(ruleIndex, false);
	}

	private static String getItemId(ItemStack stack) {
		if (stack.isEmpty()) return "empty";
		Identifier id = Registries.ITEM.getId(stack.getItem());
		return id != null ? id.toString() : "unknown";
	}
}
