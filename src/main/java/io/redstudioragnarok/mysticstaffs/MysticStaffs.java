package io.redstudioragnarok.mysticstaffs;

import io.redstudioragnarok.mysticstaffs.creativetab.StaffsTab;
import io.redstudioragnarok.mysticstaffs.items.IceBallStaff;
import io.redstudioragnarok.mysticstaffs.items.PathStaff;
import io.redstudioragnarok.mysticstaffs.items.SolarBeamStaff;
import io.redstudioragnarok.mysticstaffs.items.SunstrikeStaff;
import io.redstudioragnarok.mysticstaffs.utils.ModReference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
@Mod(modid = ModReference.id, name = ModReference.name, version = ModReference.version, dependencies = "required:mowziesmobs@[1.5.8,);required:elenaidodge2@[1.1.0,)")
@Mod.EventBusSubscriber
public class MysticStaffs {

    public static final StaffsTab staffsTab = new StaffsTab();

    public static Item pathStaff, iceBallStaff, sunstrikeStaff, solarBeamStaff;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> itemRegistryEvent) {
        pathStaff = new PathStaff().setTranslationKey("path_staff").setRegistryName(ModReference.id, "path_staff");
        iceBallStaff = new IceBallStaff().setTranslationKey("ice_ball_staff").setRegistryName(ModReference.id, "ice_ball_staff");
        sunstrikeStaff = new SunstrikeStaff().setTranslationKey("sunstrike_staff").setRegistryName(ModReference.id, "sunstrike_staff");
        solarBeamStaff = new SolarBeamStaff().setTranslationKey("solar_beam_staff").setRegistryName(ModReference.id, "solar_beam_staff");

        itemRegistryEvent.getRegistry().registerAll(pathStaff, iceBallStaff, sunstrikeStaff, solarBeamStaff);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerRenders(final ModelRegistryEvent modelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(pathStaff, 0, new ModelResourceLocation(pathStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(iceBallStaff, 0, new ModelResourceLocation(iceBallStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(sunstrikeStaff, 0, new ModelResourceLocation(sunstrikeStaff.delegate.name(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(solarBeamStaff, 0, new ModelResourceLocation(solarBeamStaff.delegate.name(), "inventory"));
    }
}
