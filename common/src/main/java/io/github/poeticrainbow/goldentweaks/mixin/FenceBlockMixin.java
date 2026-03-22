package io.github.poeticrainbow.goldentweaks.mixin;

import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FenceBlock.class)
public class FenceBlockMixin extends Block {

    public FenceBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world instanceof  ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(GoldenTweaks.FULLBLOCK_FENCES.get())) {
                return Shapes.block();
            }
        }

        return super.getShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world instanceof  ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(GoldenTweaks.FULLBLOCK_FENCES.get())) {
                return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D);
            }
        }

        return super.getCollisionShape(state, world, pos, context);
    }
}
