package com.example.particletoggle.mixin;

import com.example.particletoggle.ParticleToggle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void particleToggle$filter(ParticleEffect effect, double x, double y, double z, double vx, double vy, double vz, CallbackInfoReturnable<?> cir) {
        if (!ParticleToggle.allowed(effect)) cir.setReturnValue(null);
    }
}
