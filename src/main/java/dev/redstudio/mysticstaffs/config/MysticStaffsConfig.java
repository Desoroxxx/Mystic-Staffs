package dev.redstudio.mysticstaffs.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dev.redstudio.mysticstaffs.ProjectConstants.*;

@SuppressWarnings("CanBeFinal")
@Config(modid = ID, name = NAME)
public class MysticStaffsConfig {

    public static final Common COMMON = new Common();

    public static class Common {

        public final MowziesStaffs mowziesStaffs = new MowziesStaffs();

        public final InvisibilityStaffConfig invisibilityStaff = new InvisibilityStaffConfig();
        public final HealStaffConfig healStaff = new HealStaffConfig();
        public final FireStaffConfig fireStaff = new FireStaffConfig();
        public final WindStaffConfig windStaff = new WindStaffConfig();
        public final FlightStaffConfig flightStaff = new FlightStaffConfig();
        public final GlowStaffConfig glowStaff = new GlowStaffConfig();

        public static class MowziesStaffs {

            public final PathStaffConfig pathStaff = new PathStaffConfig();
            public final IceballStaffConfig iceBallStaff = new IceballStaffConfig();
            public final SunstrikeStaffConfig sunstrikeStaff = new SunstrikeStaffConfig();
            public final SolarBeamStaffConfig solarBeamStaff = new SolarBeamStaffConfig();

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

                public float velocity = 1.6F;
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
            public int strengthCaster = 1;
            public int strength = 2;
        }

        public static class FireStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 256;

            public int cooldown = 40;
            public int featherConsumption = 4;

            public int range = 16;
            public boolean ignitePlayers = false;
        }

        public static class WindStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 1024;

            public int cooldown = 20;
            public int featherConsumption = 4;

            public float forwardStrength = 2.45F;
            public float upwardStrength = 0.1F;
        }

        public static class FlightStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 256;

            public int cooldown = 850;
            public int featherConsumption = 4;

            public float forwardStrength = 0.1F;
            public float upwardStrength = 3.6F;
        }

        public static class GlowStaffConfig {

            @Config.RequiresMcRestart
            public int durability = 256;

            public int cooldown = 2000;
            public int featherConsumption = 1;

            public int duration = 1200;
            public int range = 40;
        }
    }

    @Mod.EventBusSubscriber(modid = ID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent onConfigChangedEvent) {
            if (onConfigChangedEvent.getModID().equals(ID))
                ConfigManager.sync(ID, Config.Type.INSTANCE);
        }
    }
}
