package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.ConfigScreen;
import com.ryanbester.shfa.SHFAState;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.awt.event.KeyEvent;


@Mod("shfa")
public class SHFA {
    public static final KeyMapping toggleKey = new KeyMapping("shfa.toggle_shfa", KeyEvent.VK_H, "shfa.toggle_shfa");

    public SHFA() {
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "shfa-config.toml");
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreen(this::saveConfig)));
    }

    private void saveConfig() {
        Config.BLOCKS.set(SHFAState.enabledBlocks.stream().toList());
        Config.SPEC.save();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (SHFA.toggleKey.isDown()) {
            SHFAState.toggleSHFA();
        }
    }

    @Mod.EventBusSubscriber(modid = "shfa", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(toggleKey);
        }

        @SubscribeEvent
        public static void loadConfig(final ModConfigEvent.Loading event) {
            loadConfigChanges();
        }

        @SubscribeEvent
        public static void reloadConfig(final ModConfigEvent.Reloading event) {
            loadConfigChanges();
        }

        private static void loadConfigChanges() {
            SHFAState.enabledBlocks.clear();
            SHFAState.enabledBlocks.addAll(Config.BLOCKS.get());
        }
    }
}
