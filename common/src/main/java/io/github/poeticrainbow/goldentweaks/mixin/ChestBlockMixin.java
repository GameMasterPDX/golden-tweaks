package io.github.poeticrainbow.goldentweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    public VoxelShape goldentweaks$getShape(VoxelShape original, BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {;
        if (world instanceof  ServerLevel serverLevel) {
            if (serverLevel.getGameRules().get(GoldenTweaks.FULLBLOCK_CHESTS.get())) {
                return Shapes.block();
            }
        }

        return original;
    }
}
