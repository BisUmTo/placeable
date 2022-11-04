package it.bisumto.placeable.mixin;

import it.bisumto.placeable.Placeable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooBlock.class)
public class BambooBlockMixin {

    // PLACEABLE
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canPlantAnywhere(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (Placeable.isValidFloor(world, pos))
            cir.setReturnValue(true);
    }

    // PREVENT GROWING
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTickMixin(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        BlockState floor = world.getBlockState(pos.below());
        if (!floor.is(BlockTags.BAMBOO_PLANTABLE_ON))
            ci.cancel();
    }

    // PLACEMENT STATE
    @Inject(method = "getStateForPlacement", at = @At("TAIL"), cancellable = true)
    public void getPlacementStateMixin(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (Placeable.isValidFloor(ctx.getLevel(), ctx.getClickedPos()))
            cir.setReturnValue(Blocks.BAMBOO_SAPLING.defaultBlockState());
    }


}
