package dev.qweru.mcu;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("qweruhax");

	@Override
	public void onInitialize() {


		LOGGER.info("[QweruHax] Initialised");
	}
}
