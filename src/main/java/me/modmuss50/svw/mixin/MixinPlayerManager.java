package me.modmuss50.svw.mixin;

import me.modmuss50.api.DimAPI;
import me.modmuss50.api.PlayerPlacementHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

	@Inject(method = "method_14558", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Profiler;begin(Ljava/lang/String;)V", shift = At.Shift.BEFORE), cancellable = true)
	public void method_14558(Entity entity, DimensionType dimensionType, ServerWorld previousWorld, ServerWorld newWorld, CallbackInfo info) {
		for (PlayerPlacementHandler playerPlacementHandler : DimAPI.playerPlacementHandlerList) {
			if (playerPlacementHandler.placeInPortal(entity, previousWorld, newWorld)) {

				newWorld.spawnEntity(entity);
				newWorld.method_8553(entity, false);
				entity.setWorld(newWorld);

				info.cancel();
				break;
			}
		}

	}

}