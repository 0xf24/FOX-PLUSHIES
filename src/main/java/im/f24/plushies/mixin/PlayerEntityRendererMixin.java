package im.f24.plushies.mixin;

import im.f24.plushies.PlushieMod;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

	@Inject(
		method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;",
		at = @At(value = "TAIL"),
		cancellable = true
	)
	private static void fox_plushies$getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
		if (!player.handSwinging && player.getStackInHand(hand).isOf(PlushieMod.FOX_PLUSHIE_ITEM)) {
			cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
			cir.cancel();
		}
	}
}
