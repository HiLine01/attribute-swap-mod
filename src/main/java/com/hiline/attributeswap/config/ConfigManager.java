package com.hiline.attributeswap.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hiline.attributeswap.AttributeSwapMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("attributeswap");
	private static final File CONFIG_FILE = CONFIG_DIR.resolve("config.json").toFile();
	
	private static List<SwapRule> rules = new ArrayList<>();
	private static int currentProfile = -1;

	public static void loadConfig() {
		try {
			if (!Files.exists(CONFIG_DIR)) {
				Files.createDirectories(CONFIG_DIR);
			}
			
			if (CONFIG_FILE.exists()) {
				try (FileReader reader = new FileReader(CONFIG_FILE)) {
					ConfigData data = GSON.fromJson(reader, ConfigData.class);
					if (data != null && data.rules != null) {
						rules = data.rules;
						currentProfile = data.currentProfile;
					}
				}
			} else {
				saveConfig();
			}
		} catch (IOException e) {
			AttributeSwapMod.LOGGER.error("Failed to load config", e);
		}
	}

	public static void saveConfig() {
		try {
			if (!Files.exists(CONFIG_DIR)) {
				Files.createDirectories(CONFIG_DIR);
			}
			
			ConfigData data = new ConfigData();
			data.rules = rules;
			data.currentProfile = currentProfile;
			
			try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
				GSON.toJson(data, writer);
			}
		} catch (IOException e) {
			AttributeSwapMod.LOGGER.error("Failed to save config", e);
		}
	}

	public static void addRule(SwapRule rule) {
		rules.add(rule);
		saveConfig();
	}

	public static void removeRule(int index) {
		if (index >= 0 && index < rules.size()) {
			rules.remove(index);
			saveConfig();
		}
	}

	public static void updateRule(int index, SwapRule rule) {
		if (index >= 0 && index < rules.size()) {
			rules.set(index, rule);
			saveConfig();
		}
	}

	public static List<SwapRule> getRules() {
		return new ArrayList<>(rules);
	}

	public static SwapRule getRule(int index) {
		return index >= 0 && index < rules.size() ? rules.get(index) : null;
	}

	public static void setCurrentProfile(int profile) {
		currentProfile = profile;
		saveConfig();
	}

	public static int getCurrentProfile() {
		return currentProfile;
	}

	private static class ConfigData {
		public List<SwapRule> rules;
		public int currentProfile = -1;
	}
}
