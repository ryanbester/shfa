package com.ryanbester.shfa;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;

public final class ConfigScreen extends Screen {
    private static final int TITLE_HEIGHT = 8;
    private static final int TOP_MARGIN = 24;

    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private static final int DONE_BUTTON_TOP_OFFSET = 26;

    private final Runnable saveCallback;

    private EditBox blocksList;

    public ConfigScreen(Runnable saveCallback) {
        super(new TranslatableComponent("shfa.shfa_config"));
        this.saveCallback = saveCallback;
    }

    public ConfigScreen create(final Screen parent) {
        return this;
    }

    @Override
    protected void init() {
        this.blocksList = new EditBox(this.font, this.width / 2 - (BUTTON_WIDTH + 5), TOP_MARGIN + 12, BUTTON_WIDTH * 2 + 10, BUTTON_HEIGHT, new TranslatableComponent("shfa.enabled_blocks"));
        this.blocksList.setMaxLength(Integer.MAX_VALUE);
        this.blocksList.setValue(String.join(",", SHFAState.enabledBlocks));
        this.addRenderableWidget(this.blocksList);

        this.addRenderableWidget(new Button(this.width / 2 - (BUTTON_WIDTH + 5), this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT, CommonComponents.GUI_BACK, (button) -> {
            this.onClose();
        }));

        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - DONE_BUTTON_TOP_OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT, CommonComponents.GUI_DONE, (button) -> {
            SHFAState.enabledBlocks.clear();
            SHFAState.enabledBlocks.addAll(Arrays.stream(this.blocksList.getValue().split(",")).toList());
            saveCallback.run();
            this.onClose();
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, TITLE_HEIGHT, 0xffffff);
        drawString(poseStack, this.font, new TranslatableComponent("shfa.enabled_blocks"), this.width / 2 - (BUTTON_WIDTH + 5), TOP_MARGIN, 0xffffff);

        this.font.drawWordWrap(FormattedText.of(new TranslatableComponent("shfa.enabled_blocks_description").getString()), this.width / 2 - (BUTTON_WIDTH + 5), TOP_MARGIN + 40, BUTTON_WIDTH * 2 + 10, 0xffffff);

        super.render(poseStack, mouseX, mouseY, partialTick);
    }
}
