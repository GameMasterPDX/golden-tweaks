package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_hitbox_shapes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin extends AbstractChestBlock<@NotNull ChestBlockEntity> {
    protected ChestBlockMixin(Properties properties, Supplier<BlockEntityType<? extends @NotNull ChestBlockEntity>> supplier) {
        super(properties, supplier);
    }

    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    public VoxelShape retrotweaks$get_shape(VoxelShape original, BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OldBlockShapesHelper.getFullBlockShape().orElse(original);
    }

    @Override
    public boolean hasDynamicShape() {
        return true;
    }
}
