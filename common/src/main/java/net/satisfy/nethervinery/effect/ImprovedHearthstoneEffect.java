package net.satisfy.nethervinery.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ImprovedHearthstoneEffect extends HearthstoneEffect {

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
        super.applyEffectTick(entity, amplifier);
    }
}
