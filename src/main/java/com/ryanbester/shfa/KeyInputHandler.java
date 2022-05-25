package com.ryanbester.shfa;

import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (SHFA.toggleKey.isDown()) {
            SHFA.toggled = !SHFA.toggled;
        }
    }
}
