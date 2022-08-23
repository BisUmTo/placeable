package it.bisumto.placeable.mixin;

import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public class PlantBlockMixin {
    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    public void canPlantAnywhere(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if(Block.hasTopRim(world, pos) || floor.isIn(BlockTags.LEAVES))
            cir.setReturnValue(true);
    }
}
