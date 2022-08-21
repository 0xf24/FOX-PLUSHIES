package im.f24.plushies.mixin;

import im.f24.plushies.FoxPlushieItem;
import im.f24.plushies.PlushieMod;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin<T extends PlayerEntity, M extends EntityModel<T> & ModelWithArms & ModelWithHead> extends HeldItemFeatureRenderer<T, M> {

	@Shadow
	@Final
	private HeldItemRenderer itemRenderer;

	private PlayerHeldItemFeatureRendererMixin(FeatureRendererContext<T, M> featureRendererContext, HeldItemRenderer heldItemRenderer) {
		super(featureRendererContext, heldItemRenderer);
	}

	@Inject(
		method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
		at = @At("HEAD"),
		cancellable = true
	)
	protected void fox_plushies$renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (stack.isOf(PlushieMod.FOX_PLUSHIE_ITEM)) {
			this.fox_plushies$renderPlushie(entity, stack, arm, matrices, vertexConsumers, light);
			ci.cancel();
		}
	}

	private void fox_plushies$renderPlushie(LivingEntity entity, ItemStack stack, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-180F));
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180F));

		double y = -6/16.0;
		double z = -5/16.0;

		float s = 1f;

		if (entity.getActiveItem().isItemEqual(stack)) {
			y += 3/16.0;
			z += 1/16.0;
			s =    0.98f;
		}

		if (entity.isInSneakingPose()) {
			y -= 3/16.0;
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-22.5F));
		}



		matrices.translate(0, y, z);

		matrices.scale(s, s, s);

		this.itemRenderer.renderItem(entity, stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light);

		matrices.pop();
	}
}
