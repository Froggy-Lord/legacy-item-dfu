package net.azureaaron.legacyitemdfu.fixers;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

import net.azureaaron.legacyitemdfu.TypeReferences;

/// Hypixel has begun storing components directly in the item data but we don't want those to be "lost"
/// so they are renamed to something else and then merged back in through a later data fix.
public class HypixelComponentsRenameFix extends DataFix {

	public HypixelComponentsRenameFix(Schema outputSchema, boolean changesType) {
		super(outputSchema, changesType);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.writeFixAndRead(
				"HypixelComponentsRenameFix",
				this.getInputSchema().getType(TypeReferences.LEGACY_ITEM_STACK),
				this.getOutputSchema().getType(TypeReferences.LEGACY_ITEM_STACK),
				itemStackDynamic -> itemStackDynamic.renameField("components", "hypixelComponents"));
	}
}
