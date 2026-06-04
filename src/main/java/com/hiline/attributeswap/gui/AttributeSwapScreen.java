package com.hiline.attributeswap.gui;

import com.hiline.attributeswap.config.ConfigManager;
import com.hiline.attributeswap.config.SwapRule;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class AttributeSwapScreen extends Screen {
	private final Screen parent;
	private Screen built;

	public AttributeSwapScreen() {
		this(null);
	}

	public AttributeSwapScreen(Screen parent) {
		super(Text.literal("Attribute Swap"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		this.built = buildScreen();
		if (this.built != null) {
			this.built.init(this.client, this.width, this.height);
		}
	}

	private Screen buildScreen() {
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(Text.literal("Attribute Swap Configuration"))
			.setSavingRunnable(ConfigManager::saveConfig);

		ConfigEntryBuilder entryBuilder = builder.entryBuilder();
		List<SwapRule> rules = ConfigManager.getRules();

		var mainCategory = builder.getOrCreateCategory(Text.literal("Main Settings"));
		mainCategory.addEntry(entryBuilder.startIntField(Text.literal("Current Profile"), ConfigManager.getCurrentProfile())
			.setDefaultValue(-1)
			.setSaveConsumer(ConfigManager::setCurrentProfile)
			.build());

		for (int i = 0; i < rules.size(); i++) {
			SwapRule rule = rules.get(i);
			int finalI = i;

			var ruleCategory = builder.getOrCreateCategory(Text.literal("Rule #" + (i + 1) + ": " + rule.name));
			
			ruleCategory.addEntry(entryBuilder.startStrField(Text.literal("Name"), rule.name)
				.setSaveConsumer(s -> {
					rule.name = s;
					ConfigManager.updateRule(finalI, rule);
				})
				.build());
			
			ruleCategory.addEntry(entryBuilder.startStrField(Text.literal("From Item ID"), rule.fromItemId)
				.setTooltip(Text.literal("e.g. minecraft:diamond_sword"))
				.setSaveConsumer(s -> {
					rule.fromItemId = s;
					ConfigManager.updateRule(finalI, rule);
				})
				.build());
			
			ruleCategory.addEntry(entryBuilder.startStrField(Text.literal("To Item ID"), rule.toItemId)
				.setTooltip(Text.literal("e.g. minecraft:iron_sword"))
				.setSaveConsumer(s -> {
					rule.toItemId = s;
					ConfigManager.updateRule(finalI, rule);
				})
				.build());
			
			ruleCategory.addEntry(entryBuilder.startEnumSelector(Text.literal("Mode"), SwapRule.SwapMode.class, rule.mode)
				.setSaveConsumer(mode -> {
					rule.mode = mode;
					ConfigManager.updateRule(finalI, rule);
				})
				.build());
			
			ruleCategory.addEntry(entryBuilder.startStrField(Text.literal("Key Binding"), rule.keyBinding)
				.setSaveConsumer(s -> {
					rule.keyBinding = s;
					ConfigManager.updateRule(finalI, rule);
				})
				.build());
			
			ruleCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enabled"), rule.enabled)
				.setSaveConsumer(enabled -> {
					rule.enabled = enabled;
					ConfigManager.updateRule(finalI, rule);
				})
				.build());
		}

		return builder.build();
	}

	@Override
	public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
		if (built != null) {
			built.render(context, mouseX, mouseY, delta);
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		if (built != null) {
			return built.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
		}
		return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (built != null) {
			return built.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (built != null) {
			return built.mouseReleased(mouseX, mouseY, button);
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}
}
