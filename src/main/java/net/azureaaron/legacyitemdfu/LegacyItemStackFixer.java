package net.azureaaron.legacyitemdfu;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;

import net.azureaaron.legacyitemdfu.fixers.AttributeIdFix;
import net.azureaaron.legacyitemdfu.fixers.BannerIdsFix;
import net.azureaaron.legacyitemdfu.fixers.BannerPatternFormatFix;
import net.azureaaron.legacyitemdfu.fixers.EnchantmentsFix;
import net.azureaaron.legacyitemdfu.fixers.HypixelComponentsMergeFix;
import net.azureaaron.legacyitemdfu.fixers.HypixelComponentsRenameFix;
import net.azureaaron.legacyitemdfu.fixers.ItemCustomNameAndLoreToTextFix;
import net.azureaaron.legacyitemdfu.fixers.ItemStackComponentizationFix;
import net.azureaaron.legacyitemdfu.fixers.ItemStackUuidsFix;
import net.azureaaron.legacyitemdfu.fixers.NumericItemIdFix;
import net.azureaaron.legacyitemdfu.fixers.PostFlatteningItemIdsFix;
import net.azureaaron.legacyitemdfu.fixers.SpawnEggItemIdFix;
import net.azureaaron.legacyitemdfu.fixers.TheFlatteningFix;
import net.azureaaron.legacyitemdfu.fixers.TooltipDisplayFix;
import net.azureaaron.legacyitemdfu.fixers.UnflattenTextComponentFix;
import net.azureaaron.legacyitemdfu.schemas.Schema1;
import net.azureaaron.legacyitemdfu.schemas.Schema11;
import net.azureaaron.legacyitemdfu.schemas.Schema2;

public final class LegacyItemStackFixer {
	private static final int FIRST_VERSION = 1;
	private static final int LATEST_VERSION = 16;
	private static final DataFixer FIXER = build();

	private static DataFixer build() {
		DataFixerBuilder builder = new DataFixerBuilder(LATEST_VERSION);

		builder.addSchema(1, Schema1::new);

		Schema numericItemIdFixSchema = builder.addSchema(2, Schema2::new);
		builder.addFixer(new NumericItemIdFix(numericItemIdFixSchema, true));

		Schema hypixelComponentsRenameFixSchema = builder.addSchema(3, Schema::new);
		builder.addFixer(new HypixelComponentsRenameFix(hypixelComponentsRenameFixSchema, true));

		Schema spawnEggItemIdFixSchema = builder.addSchema(4, Schema::new);
		builder.addFixer(new SpawnEggItemIdFix(spawnEggItemIdFixSchema, true));

		Schema bannerIdsFixSchema = builder.addSchema(5, Schema::new);
		builder.addFixer(new BannerIdsFix(bannerIdsFixSchema, true));

		Schema theFlatteningFixSchema = builder.addSchema(6, Schema::new);
		builder.addFixer(new TheFlatteningFix(theFlatteningFixSchema, true));

		Schema postFlatteningFixSchema = builder.addSchema(7, Schema::new);
		builder.addFixer(new PostFlatteningItemIdsFix(postFlatteningFixSchema, true));

		Schema itemCustomNameAndLoreToTextFixSchema = builder.addSchema(8, Schema::new);
		builder.addFixer(new ItemCustomNameAndLoreToTextFix(itemCustomNameAndLoreToTextFixSchema, true));

		Schema enchantmentsFixSchema = builder.addSchema(9, Schema::new);
		builder.addFixer(new EnchantmentsFix(enchantmentsFixSchema, true));

		Schema itemStackUuidsFixSchema = builder.addSchema(10, Schema::new);
		builder.addFixer(new ItemStackUuidsFix(itemStackUuidsFixSchema, true));

		Schema bannerPatternFormatFixSchema = builder.addSchema(11, Schema::new);
		builder.addFixer(new BannerPatternFormatFix(bannerPatternFormatFixSchema, true));

		Schema itemStackComponentizationFixSchema = builder.addSchema(12, Schema11::new);
		builder.addFixer(new ItemStackComponentizationFix(itemStackComponentizationFixSchema, true));

		// This is here instead of the end since some tests relied on this being done sooner
		// it really does not matter as long as the merged in components from Hypixel
		// can be data fixed as needed for newer Minecraft versions
		Schema hypixelComponentsMergeFixSchema = builder.addSchema(13, Schema::new);
		builder.addFixer(new HypixelComponentsMergeFix(hypixelComponentsMergeFixSchema, true));

		Schema attributeIdFixSchema = builder.addSchema(14, Schema::new);
		builder.addFixer(new AttributeIdFix(attributeIdFixSchema, true));

		Schema unflattenTextComponentFixSchema = builder.addSchema(15, Schema::new);
		builder.addFixer(new UnflattenTextComponentFix(unflattenTextComponentFixSchema, true));

		Schema tooltipDisplayFixSchema = builder.addSchema(16, Schema::new);
		builder.addFixer(new TooltipDisplayFix(tooltipDisplayFixSchema, true));

		return builder.build().fixer();
	}

	public static DataFixer getFixer() {
		return FIXER;
	}

	public static int getFirstVersion() {
		return FIRST_VERSION;
	}

	public static int getLatestVersion() {
		return LATEST_VERSION;
	}
}
