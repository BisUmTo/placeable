package it.bisumto.placeable;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Placeable implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("placeable");

    @Override
    public void onInitialize() {
        LOGGER.info("Mod loaded");
    }

    public static boolean isValidFloor(LevelReader world, BlockPos pos){
        if(world.getChunk(pos).getPersistedStatus() != ChunkStatus.FULL)
            return false;
        return isValidFloor(world.getBlockState(pos.below()), world, pos.below());
    }
    public static boolean isValidFloor(BlockState floor, BlockGetter world, BlockPos pos){
        return Block.canSupportRigidBlock(world, pos) || floor.is(BlockTags.LEAVES) || floor.is(Blocks.DIRT_PATH);
    }
}
