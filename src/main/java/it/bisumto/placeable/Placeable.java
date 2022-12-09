package it.bisumto.placeable;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.ChunkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Placeable implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("placeable");

    @Override
    public void onInitialize() {
        LOGGER.info("Mod loaded");
    }

    public static boolean isValidFloor(WorldView world, BlockPos pos){
        if(world.getChunk(pos).getStatus() != ChunkStatus.FULL)
            return false;
        return isValidFloor(world.getBlockState(pos.down()), world, pos.down());
    }
    public static boolean isValidFloor(BlockState floor, BlockView world, BlockPos pos){
        return Block.hasTopRim(world, pos) || floor.isIn(BlockTags.LEAVES) || floor.isOf(Blocks.DIRT_PATH);
    }
}
