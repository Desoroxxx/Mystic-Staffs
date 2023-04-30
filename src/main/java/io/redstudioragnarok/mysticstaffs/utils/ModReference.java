package io.redstudioragnarok.mysticstaffs.utils;

import io.redstudioragnarok.mysticstaffs.Tags;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class defines constants for Mystic Staffs.
 * <p>
 * They are automatically updated by Gradle on compile time.
 */
public class ModReference {

    public static final String id = Tags.ID;
    public static final String name = "Mystic Staffs";
    public static final String version = Tags.VERSION;
    public static final Logger log = LogManager.getLogger(id);

    public static final boolean isElenaiDodge2Loaded = Loader.isModLoaded("elenaidodge2");
}
