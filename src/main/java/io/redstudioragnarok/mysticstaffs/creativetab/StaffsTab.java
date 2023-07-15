package io.redstudioragnarok.mysticstaffs.creativetab;

import io.redstudioragnarok.mysticstaffs.MysticStaffs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class StaffsTab extends CreativeTabs {

    public StaffsTab() {
        super("staffs");
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public ItemStack createIcon() {
        return new ItemStack(MysticStaffs.fireStaff);
    }
}
