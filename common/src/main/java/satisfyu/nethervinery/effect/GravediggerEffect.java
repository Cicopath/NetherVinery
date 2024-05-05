package satisfyu.nethervinery.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class GravediggerEffect extends InstantenousMobEffect {

    public GravediggerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide() && livingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getLastDeathLocation().ifPresent(deathLocation -> {
                ResourceKey<Level> deathDimension = deathLocation.dimension();
                ServerLevel targetLevel = serverPlayer.server.getLevel(deathDimension);
                if (targetLevel != null && serverPlayer.level().dimension() != deathDimension) {
                    BlockPos pos = deathLocation.pos();
                    serverPlayer.teleportTo(targetLevel, pos.getX(), pos.getY(), pos.getZ(), Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
                }
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
