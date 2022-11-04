package it.bisumto.placeable.mixin;

import it.bisumto.placeable.Placeable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BigDripleafStemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BigDripleafStemBlock.class)
public class BigDripleafSteamBlockMixin {

    // PLACEABLE
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canPlantAnywhere(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(Placeable.isValidFloor(world, pos))
            cir.setReturnValue(true);
    }

}
