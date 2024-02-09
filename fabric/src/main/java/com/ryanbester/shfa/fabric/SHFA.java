package com.ryanbester.shfa.fabric;

import com.ryanbester.shfa.SHFAState;
import com.ryanbester.shfa.fabriclike.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

import java.awt.event.KeyEvent;

public class SHFA implements ClientModInitializer {
    public static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "shfa.toggle_shfa",
                KeyEvent.VK_H,
                "shfa.shfa_category"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleKey.consumeClick()) {
                SHFAState.toggleSHFA();
            }
        });

        Config.load();
    }
}
