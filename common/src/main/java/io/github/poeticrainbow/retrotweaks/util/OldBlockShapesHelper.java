package io.github.poeticrainbow.retrotweaks.util;

import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.Optional;

public class OldBlockShapesHelper {
    public static final VoxelShape FENCE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D);

    public static final TagKey<Block> FULL = TagKey.create(Registries.BLOCK, RetroTweaks.id("shapes/full"));
    public static final TagKey<Block> FENCE_GATE = TagKey.create(Registries.BLOCK, RetroTweaks.id("shapes/fence_gate"));
    public static final TagKey<Block> FENCE = TagKey.create(Registries.BLOCK, RetroTweaks.id("shapes/fence"));

    public static boolean shouldOverrideBlockShapes() {
        return Tweaks.OLD_HITBOX_SHAPES.get();
    }

    /**
     * We cannot directly call {@link BlockBehaviour.BlockStateBase#is(TagKey)}
     * due to tags not being bound on startup.
     *
     * @author PoeticRainbow
     */
    public static boolean is(BlockBehaviour.BlockStateBase state, TagKey<Block> blockTagKey) {
        try {
            return state.is(blockTagKey);
        } catch (IllegalStateException e) {
            return false;
        }
    }

    public static Optional<VoxelShape> getCollisionShapeForState(BlockBehaviour.BlockStateBase state) {
        if (is(state, FULL)) {
            return getFullBlockShape();
        }
        if (is(state, FENCE)) {
            return getFenceShape();
        }
        if (is(state, FENCE_GATE)) {
            if (state.hasProperty(BlockStateProperties.OPEN) && state.getValue(BlockStateProperties.OPEN)) {
                return getEmptyShape();
            }
            return getFenceShape();
        }
        return Optional.empty();
    }

    public static Optional<VoxelShape> getOutlineShapeForState(BlockBehaviour.BlockStateBase state) {
        if (is(state, FULL) || is(state, FENCE) || is(state, FENCE_GATE)) {
            return getFullBlockShape();
        }
        return Optional.empty();
    }

    public static Optional<VoxelShape> getFullBlockShape() {
        if (shouldOverrideBlockShapes()) {
            return Optional.of(Shapes.block());
        }
        return Optional.empty();
    }

    public static Optional<VoxelShape> getFenceShape() {
        if (shouldOverrideBlockShapes()) {
            return Optional.of(FENCE_SHAPE);
        }
        return Optional.empty();
    }

    public static Optional<VoxelShape> getEmptyShape() {
        if (shouldOverrideBlockShapes()) {
            return Optional.of(Shapes.empty());
        }
        return Optional.empty();
    }

    public static boolean hasLargeCollisionShape(VoxelShape shape) {
        return Arrays.stream(Direction.Axis.values())
                     .anyMatch(axis -> shape.min(axis) < 0.0 || shape.max(axis) > 1.0);
    }
}
