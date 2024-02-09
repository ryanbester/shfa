package com.ryanbester.shfa.fabriclike;

import com.ryanbester.shfa.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        ConfigScreen configScreen = new ConfigScreen(Config::save);
        return configScreen::create;
    }
}
