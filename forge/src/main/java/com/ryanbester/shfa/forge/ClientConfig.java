package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.ConfigScreen;
import com.ryanbester.shfa.SHFAState;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class ClientConfig {
    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreen(ClientConfig::saveConfig)));
    }

    private static void saveConfig() {
        Config.BLOCKS.set(SHFAState.enabledBlocks.stream().toList());
        Config.SPEC.save();
    }
}
