package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SolarBeamStaff extends Staff {

    EntitySolarBeam solarBeam;

    public SolarBeamStaff() {
        super(MysticStaffsConfig.common.solarBeamStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.solarBeamStaff.featherConsumption) {
            solarBeam = new EntitySolarBeam(world, player, player.posX, player.posY + 1.2, player.posZ, (float) ((player.rotationYaw + 90) * Math.PI / 180), (float) Math.toRadians(-player.rotationPitch), MysticStaffsConfig.common.solarBeamStaff.duration);

            world.spawnEntity(solarBeam);

            return useItem(itemStack, player, MysticStaffsConfig.common.solarBeamStaff.cooldown, MysticStaffsConfig.common.solarBeamStaff.featherConsumption);
        }

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer && solarBeam != null) {
            EntityPlayer player = (EntityPlayer) entity;

            solarBeam.setPosition(player.posX, player.posY + 1.2, player.posZ);
            solarBeam.setYaw((float) ((player.rotationYaw + 90) * Math.PI / 180));
            solarBeam.setPitch((float) Math.toRadians(-player.rotationPitch));
        }
    }
}
