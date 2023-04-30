package io.redstudioragnarok.mysticstaffs.creativetab;

import io.redstudioragnarok.mysticstaffs.MysticStaffs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StaffsTab extends CreativeTabs {

    public StaffsTab() {
        super("staffs");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(MysticStaffs.fireStaff);
    }
}
