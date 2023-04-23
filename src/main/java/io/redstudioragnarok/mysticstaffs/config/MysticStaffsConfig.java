package io.redstudioragnarok.mysticstaffs.config;

import io.redstudioragnarok.mysticstaffs.utils.ModReference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModReference.id, name = ModReference.name)
public class MysticStaffsConfig {

    public static final Common common = new Common();

    public static class Common {

        public final PathStaffConfig pathStaff = new PathStaffConfig();
        public final IceballStaffConfig iceBallStaff = new IceballStaffConfig();
        public final SunstrikeStaffConfig sunstrikeStaff = new SunstrikeStaffConfig();
        public final SolarBeamStaffConfig solarBeamStaff = new SolarBeamStaffConfig();

        public static class PathStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 32;

            public int cooldown = 20;
            public int featherConsumption = 1;

            public int lifetime = 100;
            public int length = 10;
        }

        public static class IceballStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 32;

            public int cooldown = 20;
            public int featherConsumption = 1;
        }

        public static class SunstrikeStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 32;

            public int cooldown = 20;
            public int featherConsumption = 1;

            public int amount = 4;
            public int radius = 2;
        }

        public static class SolarBeamStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 32;

            public int cooldown = 20;
            public int featherConsumption = 1;

            public int duration = 55;
        }
    }

    @Mod.EventBusSubscriber(modid = ModReference.id)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent onConfigChangedEvent) {
            if (onConfigChangedEvent.getModID().equals(ModReference.id)) {
                ConfigManager.sync(ModReference.id, Config.Type.INSTANCE);
            }
        }
    }

}
