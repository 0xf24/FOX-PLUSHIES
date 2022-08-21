package im.f24.plushies.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = {"net/minecraft/client/render/item/HeldItemRenderer$HandRenderType"})
public interface HeldItemRenderer_HandRenderTypeAccessor {

	@Accessor()
	boolean getRenderMainHand();

	@Accessor()
	boolean getRenderOffHand();
}
