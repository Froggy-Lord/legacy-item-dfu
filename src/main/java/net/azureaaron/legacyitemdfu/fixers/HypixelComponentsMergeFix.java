package net.azureaaron.legacyitemdfu.fixers;

import java.util.Map;
import java.util.function.Function;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

import net.azureaaron.legacyitemdfu.TypeReferences;

public class HypixelComponentsMergeFix extends DataFix {

	public HypixelComponentsMergeFix(Schema outputSchema, boolean changesType) {
		super(outputSchema, changesType);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.writeFixAndRead(
				"HypixelComponentsMergeFix",
				this.getInputSchema().getType(TypeReferences.LEGACY_ITEM_STACK),
				this.getOutputSchema().getType(TypeReferences.LEGACY_ITEM_STACK),
				itemStackDynamic -> {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					Map<Dynamic<?>, Dynamic<?>> hypixelComponents = (Map) itemStackDynamic.get("hypixelComponents").asMap(Function.identity(), Function.identity());					

					// If there are no components to merge in then skip
					// This ensures that the components key is not added unless it is needed
					if (hypixelComponents.isEmpty()) {
						return itemStackDynamic;
					}

					Dynamic<?> vanillaComponents = itemStackDynamic.get("components").orElseEmptyMap();

					for (Map.Entry<Dynamic<?>, Dynamic<?>> entry : hypixelComponents.entrySet()) {
						String key = entry.getKey().asString("");
						Dynamic<?> value = entry.getValue();

						// Override the vanilla component with Hypixel's
						vanillaComponents = vanillaComponents.set(key, value);
					}

					// Update the components & remove the temporary hypixelComponents field
					itemStackDynamic = itemStackDynamic.set("components", vanillaComponents);
					itemStackDynamic = itemStackDynamic.remove("hypixelComponents");

					return itemStackDynamic;
				});
	}
}
