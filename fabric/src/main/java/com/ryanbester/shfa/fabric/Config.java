package com.ryanbester.shfa.fabric;

import com.google.gson.*;
import com.ryanbester.shfa.SHFAState;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {
    private static final File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), "shfa.json");
    public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

    public static void load() {
        try {
            if (!file.exists()) {
                SHFAState.enabledBlocks.clear();
                SHFAState.enabledBlocks.addAll(SHFAState.enabledBlocksDefault);
                save();
            }
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                JsonObject json = JsonParser.parseReader(br).getAsJsonObject();

                JsonArray blocks = json.getAsJsonArray("blocks");
                SHFAState.enabledBlocks.clear();
                blocks.forEach(block -> {
                    SHFAState.enabledBlocks.add(block.getAsString());
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        JsonObject config = new JsonObject();

        JsonArray blocks = new JsonArray();
        SHFAState.enabledBlocks.forEach(blocks::add);
        config.add("blocks", blocks);

        String jsonString = GSON.toJson(config);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
