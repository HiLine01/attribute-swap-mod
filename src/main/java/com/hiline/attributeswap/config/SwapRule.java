package com.hiline.attributeswap.config;

public class SwapRule {
	public String name;
	public String fromItemId;
	public String toItemId;
	public SwapMode mode;
	public String keyBinding;
	public boolean enabled;

	public SwapRule() {
	}

	public SwapRule(String name, String fromItemId, String toItemId, SwapMode mode, String keyBinding) {
		this.name = name;
		this.fromItemId = fromItemId;
		this.toItemId = toItemId;
		this.mode = mode;
		this.keyBinding = keyBinding;
		this.enabled = true;
	}

	public enum SwapMode {
		TOGGLE,
		SINGLE
	}

	@Override
	public String toString() {
		return String.format("%s: %s -> %s (%s)", name, fromItemId, toItemId, mode);
	}
}
