package dev.redstudio.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import dev.redstudio.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class FireStaff extends Staff {

    public FireStaff() {
        super(MysticStaffsConfig.COMMON.fireStaff.durability);
    }

    /**
     * Activates the Fire Staff's ability when the item is right-clicked.
     * <p>
     * Sets nearby living entities on fire, except for the player who used the item and other players if the configuration is set to not ignite players.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.fireStaff.featherConsumption)) {

            final int range = MysticStaffsConfig.COMMON.fireStaff.range;

            for (EntityLivingBase nearbyLivingEntity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range))) {
                if (nearbyLivingEntity != player) {
                    if (MysticStaffsConfig.COMMON.fireStaff.ignitePlayers && nearbyLivingEntity instanceof EntityPlayer)
                        continue;

                    nearbyLivingEntity.setFire(5);
                }
            }

            world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 2, 0.7F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, player.posX, player.posY - 0.5, player.posZ, 25000, 3.3, 0.5, 3.3, 0.1);

            return useItem(itemStack, player, MysticStaffsConfig.COMMON.fireStaff.cooldown, MysticStaffsConfig.COMMON.fireStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
