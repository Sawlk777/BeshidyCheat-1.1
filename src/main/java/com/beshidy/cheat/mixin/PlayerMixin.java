package com.beshidy.cheat.mixin;
import com.beshidy.cheat.client.ClientState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Player p = (Player)(Object)this;
        if (!p.level().isClientSide) return;
        int mode = ClientState.bypassMode;
        if (mode == 0) return;
        if (mode == 1) {
            p.setDeltaMovement(p.getDeltaMovement().x + (Math.random()-0.5)*0.001,
                               p.getDeltaMovement().y + (Math.random()-0.5)*0.001,
                               p.getDeltaMovement().z + (Math.random()-0.5)*0.001);
        } else if (mode == 2 && p.tickCount % 3 == 0) {
            p.setPos(p.getX() + 0.005, p.getY() - 0.002, p.getZ() + 0.005);
        }
    }
}