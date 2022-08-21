package im.f24.plushies;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlushieMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("FOX PLUSHIES!!!");

	public static final String MOD_ID = "fox_plushies";

	public static final Item FOX_PLUSHIE_ITEM = new FoxPlushieItem(new QuiltItemSettings().group(ItemGroup.DECORATIONS).equipmentSlot(EquipmentSlot.HEAD).maxCount(1));

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fox_plushie"), FOX_PLUSHIE_ITEM);
	}
}
