package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SolarBeamStaff extends Staff {

    public SolarBeamStaff() {
        super(MysticStaffsConfig.common.solarBeamStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.solarBeamStaff.featherConsumption) {
            world.spawnEntity(new EntitySolarBeam(world, player, player.posX, player.posY + 1.5, player.posZ, (float) ((player.rotationYaw + 90) * Math.PI / 180), (float) Math.toRadians(-player.rotationPitch), MysticStaffsConfig.common.solarBeamStaff.duration));

            return useItem(itemStack, player, MysticStaffsConfig.common.solarBeamStaff.cooldown, MysticStaffsConfig.common.solarBeamStaff.featherConsumption);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
