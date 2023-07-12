package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.SHFAState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod("shfa")
public class SHFA {
    public SHFA() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(SHFAClient.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "shfa-config.toml");
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientConfig::registerConfigScreen);
    }

    @Mod.EventBusSubscriber(modid = "shfa", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
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
