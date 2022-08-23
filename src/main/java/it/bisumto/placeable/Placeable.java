package it.bisumto.placeable;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Placeable implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("placeable");

    @Override
    public void onInitialize() {
        LOGGER.info("Mod loaded");
    }

}
