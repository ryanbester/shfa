package com.ryanbester.shfa;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class ConfigScreen extends Screen {
    private static final int TITLE_HEIGHT = 8;
    private static final int TOP_MARGIN = 32;
    private static final int ENABLED_BLOCKS_TOP_MARGIN = TOP_MARGIN + 40;

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private static final int DONE_BUTTON_TOP_OFFSET = 26;

    private final Runnable saveCallback;

    private BlockSelectionList selectedBlocksList;

    public ConfigScreen(Runnable saveCallback) {
        super(Component.translatable("shfa.shfa_config"));
        this.saveCallback = saveCallback;
    }

    public ConfigScreen create(final Screen parent) {
        return this;
    }

    @Override
    protected void init() {
        this.selectedBlocksList = new BlockSelectionList(this.minecraft, BUTTON_WIDTH,this.height - DONE_BUTTON_TOP_OFFSET - ENABLED_BLOCKS_TOP_MARGIN - 12, ENABLED_BLOCKS_TOP_MARGIN, 20);
        this.addRenderableWidget(this.selectedBlocksList);
        this.selectedBlocksList.setX(this.width / 2 - (BUTTON_WIDTH + 5));

        this.addWidget(this.selectedBlocksList);
        for (String block : SHFAState.enabledBlocks) {
            this.selectedBlocksList.children().add(new BlockSelectionList.BlockSelection(this.minecraft, this.selectedBlocksList, block));
        }

        EditBox addBox = new EditBox(this.font, this.width / 2 + 5, ENABLED_BLOCKS_TOP_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT, Component.translatable("shfa.config.add_block"));
        addBox.setMaxLength(Integer.MAX_VALUE);
        this.addRenderableWidget(addBox);

        this.addRenderableWidget(Button.builder(Component.translatable("shfa.config.add"), (button) -> {
            if (addBox.getValue().length() < 1) {
                return;
            }

            this.selectedBlocksList.children().add(new BlockSelectionList.BlockSelection(this.minecraft, this.selectedBlocksList, addBox.getValue()));
        }).bounds(this.width / 2 + 5, ENABLED_BLOCKS_TOP_MARGIN + BUTTON_HEIGHT + 5, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        this.addRenderableWidget(Button.builder(Component.translatable("shfa.config.remove"), (button) -> {
            if (this.selectedBlocksList.getSelected() == null) {
                return;
            }

            this.selectedBlocksList.children().remove(this.selectedBlocksList.getSelected());
        }).bounds(this.width / 2 + 5, ENABLED_BLOCKS_TOP_MARGIN + 2 * (BUTTON_HEIGHT + 5), BUTTON_WIDTH, BUTTON_HEIGHT).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> this.onClose())
                .bounds(this.width / 2 - (BUTTON_WIDTH + 5), this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            SHFAState.enabledBlocks.clear();
            SHFAState.enabledBlocks.addAll(this.selectedBlocksList.children().stream().map(BlockSelectionList.BlockSelection::getName).toList());
            saveCallback.run();
            this.onClose();
        }).bounds(this.width / 2 + 5, this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, TITLE_HEIGHT, 0xffffff);
        guiGraphics.drawString(this.font, Component.translatable("shfa.enabled_blocks"), this.width / 2 - (BUTTON_WIDTH + 5), TOP_MARGIN, 0xffffff);

        guiGraphics.drawWordWrap(this.font, FormattedText.of(Component.translatable("shfa.enabled_blocks_description").getString()), this.width / 2 - (BUTTON_WIDTH + 5), TOP_MARGIN + 12, BUTTON_WIDTH * 2 + 10, 0xffffff);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderDirtBackground(guiGraphics);
    }
}
