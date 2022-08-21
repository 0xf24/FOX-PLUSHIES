package im.f24.plushies.mixin;

import im.f24.plushies.PlushieMod;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow
	protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

	@Shadow
	private void applySwingOffset(MatrixStack matrices, Arm arm, float equipProgress) {
		throw new IllegalStateException();
	}


	@Shadow
	protected abstract void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack);

	@Inject(
		method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
			ordinal = 1
		)
	)
	private void fox_plushies$renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (item.isOf(PlushieMod.FOX_PLUSHIE_ITEM) && player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
			Arm arm = (hand == Hand.MAIN_HAND) ? player.getMainArm() : player.getMainArm().getOpposite();
//			float n = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
//			float m = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
//			float f = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
//			int o = (arm == Arm.RIGHT) ? 1 : -1;
//			matrices.translate((float)o * n, m, f);

			int i = arm == Arm.RIGHT ? 1 : -1;

			matrices.translate(
				(float)i * (0.56F - .15f),
				-0.52F + 0.11F, //y
				-0.72F + .2F
			);


//			this.applyEquipOffset(matrices, arm, 0.0f);
//			this.applySwingOffset(matrices, arm, swingProgress);
		}
	}

	@Redirect(
		method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/render/item/HeldItemRenderer$HandRenderType;renderMainHand:Z"
		)

	)
	public boolean fox_plushies$_renderMainHand_renderItem(@Coerce Object instance, float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light) {
		boolean b = ((HeldItemRenderer_HandRenderTypeAccessor) instance).getRenderMainHand();

		if (player.isUsingItem()) {
			ItemStack offhand = player.getOffHandStack();


			if (offhand.isOf(PlushieMod.FOX_PLUSHIE_ITEM)) {
				return false;
			} else {
				return b;
			}

		}

		return b;
	}

	@Redirect(
		method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/render/item/HeldItemRenderer$HandRenderType;renderOffHand:Z"
		)

	)
	public boolean fox_plushies$_renderOffHand_renderItem(@Coerce Object instance, float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light) {
		boolean b = ((HeldItemRenderer_HandRenderTypeAccessor) instance).getRenderOffHand();

		if (player.isUsingItem()) {
			ItemStack main = player.getMainHandStack();


			if (main.isOf(PlushieMod.FOX_PLUSHIE_ITEM)) {
				return false;
			} else {
				return b;
			}

		}

		return b;
	}

}
