package com.ryanbester.shfa.forge;

import com.ryanbester.shfa.SHFAState;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<List<String>> BLOCKS;

    static {
        BLOCKS = BUILDER.comment("List of blocks that are included with SHFA").define("Blocks", SHFAState.enabledBlocksDefault.stream().toList());
        SPEC = BUILDER.build();
    }

}
