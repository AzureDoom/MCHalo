package mod.azure.mchalo.blocks.blockentity;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.blocks.TickingLightBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TickingLightEntity extends BlockEntity {
	private int lifespan = 0;

	public TickingLightEntity(BlockPos blockPos, BlockState blockState) {
		super(MCHaloMod.TICKING_LIGHT_ENTITY, blockPos, blockState);
	}

	public void refresh(int lifeExtension) {
		lifespan = -lifeExtension;
	}

	private void tick() {
		if (lifespan++ >= 5) {
			if (world.getBlockState(getPos()).getBlock() instanceof TickingLightBlock)
				world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
			else
				markRemoved();
		}
	}

	public static void staticTick(World world, BlockPos blockPos, BlockState blockState,
			TickingLightEntity blockEntity) {
		blockEntity.tick();
	}
}
