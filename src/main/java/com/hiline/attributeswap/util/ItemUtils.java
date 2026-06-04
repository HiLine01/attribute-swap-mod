package com.hiline.attributeswap.util;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
	public static List<ItemStack> getAllItems() {
		List<ItemStack> items = new ArrayList<>();
		Registries.ITEM.forEach(item -> items.add(new ItemStack(item)));
		return items;
	}

	public static ItemStack getItemStackFromId(String itemId) {
		try {
			return new ItemStack(Registries.ITEM.get(new Identifier(itemId)));
		} catch (Exception e) {
			return ItemStack.EMPTY;
		}
	}

	public static String getItemId(ItemStack stack) {
		if (stack.isEmpty()) return "empty";
		Identifier id = Registries.ITEM.getId(stack.getItem());
		return id != null ? id.toString() : "unknown";
	}

	public static String getItemName(ItemStack stack) {
		if (stack.isEmpty()) return "Empty";
		return stack.getItem().getName(stack).getString();
	}

	public static List<ItemStack> searchItems(String query) {
		List<ItemStack> results = new ArrayList<>();
		String lowerQuery = query.toLowerCase();
		
		Registries.ITEM.forEach(item -> {
			ItemStack stack = new ItemStack(item);
			String itemName = getItemName(stack).toLowerCase();
			String itemId = getItemId(stack).toLowerCase();
			
			if (itemName.contains(lowerQuery) || itemId.contains(lowerQuery)) {
				results.add(stack);
			}
		});
		
		return results;
	}
}
