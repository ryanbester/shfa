package com.ryanbester.shfa.mixin;

import com.ryanbester.shfa.SHFA;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FourWayBlock.class)
public abstract class MixinFourWayBlock {

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/block/FourWayBlock;getShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true)
    private void onClearChatHistory(BlockState state, IBlockReader reader, BlockPos pos,
        ISelectionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (SHFA.toggled) {
            cir.setReturnValue(VoxelShapes.fullCube());
        }
    }

}
