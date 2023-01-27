package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.SHFAState;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;

public class SHFAClient {
    public static final KeyMapping toggleKey = new KeyMapping("shfa.toggle_shfa", KeyEvent.VK_H, "shfa.toggle_shfa");

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (SHFAClient.toggleKey.isDown()) {
            SHFAState.toggleSHFA();
        }
    }

    @Mod.EventBusSubscriber(modid = "shfa", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(toggleKey);
        }
    }
}
