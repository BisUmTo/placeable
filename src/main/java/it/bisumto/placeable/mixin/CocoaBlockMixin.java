package it.bisumto.placeable.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

@Mixin(CocoaBlock.class)
public class CocoaBlockMixin {

    // PLACEABLE
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canPlantAnywhere(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.relative(state.getValue(FACING)));
        if (blockState.isFaceSturdy(world, pos, state.getValue(FACING), SupportType.RIGID))
            cir.setReturnValue(true);
    }

    // PREVENT GROWING
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTickMixin(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        BlockState floor = world.getBlockState(pos.relative(state.getValue(FACING)));
        if (!floor.is(BlockTags.JUNGLE_LOGS))
            ci.cancel();
    }

}
