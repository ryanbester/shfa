package com.ryanbester.shfa;

import java.awt.event.KeyEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("shfa")
public class SHFA {

    public static final KeyBinding toggleKey = new KeyBinding("Toggle SHFA", KeyEvent.VK_H,
        "SHFA");
    public static boolean toggled = false;

    public SHFA() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initClient);
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }

    private void initClient(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(toggleKey);
    }
}
