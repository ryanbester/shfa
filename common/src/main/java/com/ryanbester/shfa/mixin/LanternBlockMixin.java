package com.ryanbester.shfa.mixin;

import com.ryanbester.shfa.SHFAState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LanternBlock.class)
public class LanternBlockMixin extends Block {

    @Shadow @Final public static BooleanProperty HANGING;
    @Shadow @Final protected static VoxelShape HANGING_AABB;
    @Shadow @Final protected static VoxelShape AABB;

    public LanternBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("HEAD"), method = "getShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true)
    private void getShape(BlockState state, BlockGetter reader, BlockPos pos,
                          CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (SHFAState.showHitbox(state)) {
            cir.setReturnValue(Shapes.block());
        }
    }

    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return (Boolean)state.getValue(HANGING) ? HANGING_AABB : AABB;
    }
}
