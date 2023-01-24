package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.ConfigScreen;
import com.ryanbester.shfa.SHFAState;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.event.KeyEvent;


@Mod("shfa")
public class SHFA {
    public static final KeyMapping toggleKey = new KeyMapping("shfa.toggle_shfa", KeyEvent.VK_H, "shfa.toggle_shfa");

    public SHFA() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initClient);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadConfig);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::reloadConfig);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "shfa-config.toml");
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((minecraft, screen) -> new ConfigScreen(this::saveConfig)));
    }

    private void initClient(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(toggleKey);
    }

    private void loadConfig(final ModConfigEvent.Loading event) {
        loadConfigChanges();
    }

    private void reloadConfig(final ModConfigEvent.Reloading event) {
        loadConfigChanges();
    }

    private void loadConfigChanges() {
        SHFAState.enabledBlocks.clear();
        SHFAState.enabledBlocks.addAll(Config.BLOCKS.get());
    }

    private void saveConfig() {
        Config.BLOCKS.set(SHFAState.enabledBlocks.stream().toList());
        Config.SPEC.save();
    }


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (SHFA.toggleKey.isDown()) {
            SHFAState.toggleSHFA();
        }
    }
}
