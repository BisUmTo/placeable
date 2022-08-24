package it.bisumto.placeable.mixin;

import it.bisumto.placeable.Placeable;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooBlock.class)
public class BambooBlockMixin {

    // PLACEABLE
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void canPlantAnywhere(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(Placeable.isValidFloor(world, pos))
            cir.setReturnValue(true);
    }

    // PREVENT GROWING
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTickMixin(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockState floor = world.getBlockState(pos.down());
        if (!floor.isIn(BlockTags.BAMBOO_PLANTABLE_ON))
            ci.cancel();
    }

    // PLACEMENT STATE
    @Inject(method = "getPlacementState", at = @At("TAIL"), cancellable = true)
    public void getPlacementStateMixin(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (Placeable.isValidFloor(ctx.getWorld(), ctx.getBlockPos()))
            cir.setReturnValue(Blocks.BAMBOO_SAPLING.getDefaultState());
    }


}
