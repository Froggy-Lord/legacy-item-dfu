package net.azureaaron.legacyitemdfu;

import com.mojang.datafixers.DSL;

public final class TypeReferences {
	public static final DSL.TypeReference LEGACY_ITEM_STACK = () -> "legacy_item_stack";
	public static final DSL.TypeReference ITEM_NAME = () -> "legacy_dfu_item_name";
}
