package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import io.redstudioragnarok.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class FlightStaff extends Staff {

    private static final SoundEvent GUST = new SoundEvent(new ResourceLocation("mysticstaffs", "gust"));

    public FlightStaff() {
        super(MysticStaffsConfig.COMMON.flightStaff.durability);
    }


    /**
     * Activates the Flight Staff's ability when the item is right-clicked.
     * <p>
     * Launches the player in the direction they are facing with configurable forward and upward strength.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.flightStaff.featherConsumption)) {
            MysticStaffsUtils.launchPlayerInDirection(player, MysticStaffsConfig.COMMON.flightStaff.forwardStrength, MysticStaffsConfig.COMMON.flightStaff.upwardStrength);

            world.playSound(null, player.getPosition(), GUST, SoundCategory.PLAYERS, 1.2F, 0.6F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.SNOW_SHOVEL, player.posX, player.posY -1.25, player.posZ, 10000, 0.55, 3.0, 0.55, 0.01);

            return useItem(itemStack, player, MysticStaffsConfig.COMMON.flightStaff.cooldown, MysticStaffsConfig.COMMON.flightStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    /**
     * Called each tick as long the item is on a player inventory.
     * <p>
     * Preventing the player from taking fall damage when holding the Flight Staff in either hand.
     */
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (player.getHeldItemMainhand().getItem() == this || player.getHeldItemOffhand().getItem() == this)
                player.fallDistance = 0;
        }
    }
}
