package io.redstudioragnarok.mysticstaffs;

import io.redstudioragnarok.mysticstaffs.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static dev.redstudio.mysticstaffs.ProjectConstants.*;

//   /$$      /$$                       /$$     /$$                  /$$$$$$   /$$                /$$$$$$   /$$$$$$
//  | $$$    /$$$                      | $$    |__/                 /$$__  $$ | $$               /$$__  $$ /$$__  $$
//  | $$$$  /$$$$ /$$   /$$  /$$$$$$$ /$$$$$$   /$$  /$$$$$$$      | $$  \__//$$$$$$    /$$$$$$ | $$  \__/| $$  \__//$$$$$$$
//  | $$ $$/$$ $$| $$  | $$ /$$_____/|_  $$_/  | $$ /$$_____/      |  $$$$$$|_  $$_/   |____  $$| $$$$    | $$$$   /$$_____/
//  | $$  $$$| $$| $$  | $$|  $$$$$$   | $$    | $$| $$             \____  $$ | $$      /$$$$$$$| $$_/    | $$_/  |  $$$$$$
//  | $$\  $ | $$| $$  | $$ \____  $$  | $$ /$$| $$| $$             /$$  \ $$ | $$ /$$ /$$__  $$| $$      | $$     \____  $$
//  | $$ \/  | $$|  $$$$$$$ /$$$$$$$/  |  $$$$/| $$|  $$$$$$$      |  $$$$$$/ |  $$$$/|  $$$$$$$| $$      | $$     /$$$$$$$/
//  |__/     |__/ \____  $$|_______/    \___/  |__/ \_______/       \______/   \___/   \_______/|__/      |__/    |_______/
//                /$$  | $$
//               |  $$$$$$/
//                \______/
@Mod(modid = ID, name = NAME, version = VERSION, updateJSON = "https://raw.githubusercontent.com/Desoroxxx/Mystic-Staffs/main/update.json")
@Mod.EventBusSubscriber
public class MysticStaffs {

    public static final boolean IS_MOWZIES_MOBS_LOADED = Loader.isModLoaded("mowziesmobs");

    public static Item pathStaff, iceBallStaff, sunstrikeStaff, solarBeamStaff, invisibilityStaff, healStaff, fireStaff, windStaff, flightStaff, glowStaff;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> itemRegistryEvent) {
        invisibilityStaff = new InvisibilityStaff().setTranslationKey("invisibility_staff").setRegistryName(ID, "invisibility_staff");
        healStaff = new HealStaff().setTranslationKey("heal_staff").setRegistryName(ID, "heal_staff");
        fireStaff = new FireStaff().setTranslationKey("fire_staff").setRegistryName(ID, "fire_staff");
        windStaff = new WindStaff().setTranslationKey("wind_staff").setRegistryName(ID, "wind_staff");
        flightStaff = new FlightStaff().setTranslationKey("flight_staff").setRegistryName(ID, "flight_staff");
        glowStaff = new GlowStaff().setTranslationKey("glow_staff").setRegistryName(ID, "glow_staff");

        itemRegistryEvent.getRegistry().registerAll(invisibilityStaff, healStaff, fireStaff, windStaff, flightStaff, glowStaff);

        if (!IS_MOWZIES_MOBS_LOADED)
            return;

        pathStaff = new PathStaff().setTranslationKey("path_staff").setRegistryName(ID, "path_staff");
        iceBallStaff = new IceBallStaff().setTranslationKey("ice_ball_staff").setRegistryName(ID, "ice_ball_staff");
        sunstrikeStaff = new SunstrikeStaff().setTranslationKey("sunstrike_staff").setRegistryName(ID, "sunstrike_staff");
        solarBeamStaff = new SolarBeamStaff().setTranslationKey("solar_beam_staff").setRegistryName(ID, "solar_beam_staff");

        itemRegistryEvent.getRegistry().registerAll(pathStaff, iceBallStaff, sunstrikeStaff, solarBeamStaff);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerRenders(final ModelRegistryEvent modelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(invisibilityStaff, 0, new ModelResourceLocation(invisibilityStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(healStaff, 0, new ModelResourceLocation(healStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(fireStaff, 0, new ModelResourceLocation(fireStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(windStaff, 0, new ModelResourceLocation(windStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(flightStaff, 0, new ModelResourceLocation(flightStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(glowStaff, 0, new ModelResourceLocation(glowStaff.delegate.name(), "inventory"));

        if (!IS_MOWZIES_MOBS_LOADED)
            return;

        ModelLoader.setCustomModelResourceLocation(pathStaff, 0, new ModelResourceLocation(pathStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(iceBallStaff, 0, new ModelResourceLocation(iceBallStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(sunstrikeStaff, 0, new ModelResourceLocation(sunstrikeStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(solarBeamStaff, 0, new ModelResourceLocation(solarBeamStaff.delegate.name(), "inventory"));
    }
}
