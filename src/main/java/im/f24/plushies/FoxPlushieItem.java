package im.f24.plushies;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

import java.util.Random;

public class FoxPlushieItem extends Item{

	private final static Random random = new Random();

	public FoxPlushieItem(Settings settings) {
		super(settings);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 1200;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (world.isClient && (remainingUseTicks % 4) == 0) {

			float yaw = (float) Math.toRadians(user.bodyYaw + 90);

			Vec2f rot = new Vec2f((float) Math.cos(yaw), (float) Math.sin(yaw)).multiply(0.4f);

			world.addParticle(

				ParticleTypes.HEART,

				user.getX() + rot.x + ((random.nextDouble() * 2) - 1) * 0.25,
				user.getY() + 2,
				user.getZ() + rot.y + ((random.nextDouble() * 2) - 1) * 0.25,
				0.0,
				0.1 + (random.nextDouble() * 0.5),
				0.0
			);
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}
