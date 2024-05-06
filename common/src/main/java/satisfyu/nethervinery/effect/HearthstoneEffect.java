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
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.Vec3;

public class HearthstoneEffect extends InstantenousMobEffect {

    public HearthstoneEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide() && livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getRespawnPosition() != null && serverPlayer.getRespawnDimension() != null) {
                ResourceKey<Level> respawnDimension = serverPlayer.getRespawnDimension();
                ServerLevel targetLevel = serverPlayer.server.getLevel(respawnDimension);
                BlockPos respawnPos = serverPlayer.getRespawnPosition();
                if (targetLevel != null && (targetLevel.getBlockState(respawnPos).getBlock() instanceof BedBlock)) {
                    Vec3 pos = Vec3.atBottomCenterOf(respawnPos);
                    serverPlayer.teleportTo(targetLevel, pos.x, pos.y, pos.z, Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
                }
            } else {
                Vec3 pos = Vec3.atBottomCenterOf(serverPlayer.level().getSharedSpawnPos());
                serverPlayer.connection.teleport(pos.x, pos.y, pos.z, Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
            }
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
