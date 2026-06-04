package com.hiline.attributeswap.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
	public static KeyBinding TOGGLE_SWAP;
	public static KeyBinding OPEN_GUI;

	public static void register() {
		TOGGLE_SWAP = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.attributeswap.toggle_swap",
			GLFW.GLFW_KEY_U,
			"category.attributeswap.main"
		));

		OPEN_GUI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.attributeswap.open_gui",
			GLFW.GLFW_KEY_I,
			"category.attributeswap.main"
		));
	}
}
