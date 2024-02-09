package com.ryanbester.shfa;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockSelectionList extends ObjectSelectionList<BlockSelectionList.BlockSelection> {
    public static int PADDING_X = 4;

    public BlockSelectionList(Minecraft minecraft, int width, int height, int top, int itemHeight) {
        super(minecraft, width, height, top, itemHeight);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getRight() - 6;
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    public static class BlockSelection extends ObjectSelectionList.Entry<BlockSelection> {

        private final Minecraft minecraft;
        private final BlockSelectionList parent;
        private final String name;
        private final FormattedCharSequence nameDisplayCache;

        private ItemStack is;

        public BlockSelection(Minecraft minecraft, BlockSelectionList parent, String name) {
            this.minecraft = minecraft;
            this.parent = parent;
            this.name = name;
            try {
                Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(name));
                this.is = new ItemStack(block);
            } catch (Exception ex) {
                // Tag or invalid block
                this.is = null;
            }
            this.nameDisplayCache = cacheName(minecraft, Component.literal(this.name));
        }

        private FormattedCharSequence cacheName(Minecraft minecraft, Component name) {
            return name.getVisualOrderText();
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.translatable("narrator.select", this.name);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean bl, float partialTick) {
            if (is != null) {
                guiGraphics.renderFakeItem(is, left + BlockSelectionList.PADDING_X, top);
            }
            guiGraphics.drawString(this.minecraft.font, this.nameDisplayCache, left + BlockSelectionList.PADDING_X + 22, top + 5, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            double d0 = mouseX - (double) this.parent.getRowLeft();
            double d1 = mouseY - (double) this.parent.getRowTop(this.parent.children().indexOf(this));
            if (d1 < 36) {
                this.parent.setSelected(this);
            }

            return false;
        }

        public String getName() {
            return name;
        }
    }
}

