package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.SHFAState;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.event.KeyEvent;


@Mod("shfa")
public class SHFA
{
    public static final KeyMapping toggleKey = new KeyMapping("shfa.toggle_shfa", KeyEvent.VK_H, "shfa.toggle_shfa");

    public SHFA()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initClient);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void initClient(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(toggleKey);
    }


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (SHFA.toggleKey.isDown()) {
            SHFAState.toggleSHFA();
        }
    }
}
