package io.redstudioragnarok.mysticstaffs.utils;

import io.redstudioragnarok.mysticstaffs.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class defines constants for Mystic Staffs.
 * <p>
 * They are automatically updated by RFG on compile time, except for the name as Gradle would remove spaces.
 */
public class ModReference {

    public static final String ID = Tags.ID;
    public static final String NAME = "Mystic Staffs";
    public static final String VERSION = Tags.VERSION;
    public static final Logger LOG = LogManager.getLogger(ID);
}
