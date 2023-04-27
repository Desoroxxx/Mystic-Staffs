package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class FlightStaff extends Staff {

    public FlightStaff() {
        super(MysticStaffsConfig.common.flightStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.flightStaff.featherConsumption) {
            final float yaw = player.rotationYaw;
            final float pitch = player.rotationPitch;

            final float strength = MysticStaffsConfig.common.flightStaff.forwardStrength;

            player.motionX = (-MathHelper.sin(yaw / 180 * 3) * MathHelper.cos(pitch / 180 * 3) * strength);
            player.motionZ = (MathHelper.cos(yaw / 180 * 3) * MathHelper.cos(pitch / 180 * 3) * strength);
            player.motionY = MysticStaffsConfig.common.flightStaff.upwardStrength;

            player.velocityChanged = true;

            world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation("mysticstaffs", "gust")), SoundCategory.PLAYERS, 1.2F, 0.6F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.SNOW_SHOVEL, player.posX, player.posY -1.25, player.posZ, 10000, 0.55, 3.0, 0.55, 0.01);

            return useItem(itemStack, player, MysticStaffsConfig.common.flightStaff.cooldown, MysticStaffsConfig.common.flightStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (player.getHeldItemMainhand().getItem() == this || player.getHeldItemOffhand().getItem() == this)
                player.fallDistance = 0;
        }
    }
}
