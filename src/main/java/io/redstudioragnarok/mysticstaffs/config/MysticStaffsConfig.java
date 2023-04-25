package io.redstudioragnarok.mysticstaffs.config;

import io.redstudioragnarok.mysticstaffs.utils.ModReference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("CanBeFinal")
@Config(modid = ModReference.id, name = ModReference.name)
public class MysticStaffsConfig {

    public static final Common common = new Common();

    public static class Common {

        public final PathStaffConfig pathStaff = new PathStaffConfig();
        public final IceballStaffConfig iceBallStaff = new IceballStaffConfig();
        public final SunstrikeStaffConfig sunstrikeStaff = new SunstrikeStaffConfig();
        public final SolarBeamStaffConfig solarBeamStaff = new SolarBeamStaffConfig();
        public final InvisibilityStaffConfig invisibilityStaff = new InvisibilityStaffConfig();
        public final HealStaffConfig healStaff = new HealStaffConfig();
        public final FireStaffConfig fireStaff = new FireStaffConfig();

        public static class PathStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 128;

            public int cooldown = 32;
            public int featherConsumption = 1;

            public int lifetime = 256;
            public int length = 10;
        }

        public static class IceballStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 128;

            public int cooldown = 32;
            public int featherConsumption = 1;
        }

        public static class SunstrikeStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 64;

            public int cooldown = 32;
            public int featherConsumption = 1;

            public int amount = 4;
            public int radius = 2;
            public int delay = 6;
            public int reachMultiplier = 40;
        }

        public static class SolarBeamStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 64;

            public int cooldown = 110;
            public int featherConsumption = 1;

            public int slowness = 1;
        }

        public static class InvisibilityStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 128;

            public int cooldown = 1800;
            public int featherConsumption = 6;

            public int duration = 1200;
        }

        public static class HealStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 256;

            public int cooldown = 30;
            public int featherConsumption = 1;

            public int range = 16;
            public int strength = 2;
        }

        public static class FireStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 256;

            public int cooldown = 40;
            public int featherConsumption = 4;

            public int range = 16;
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
