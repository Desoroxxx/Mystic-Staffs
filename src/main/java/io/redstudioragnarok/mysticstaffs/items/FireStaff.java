package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
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
        super(MysticStaffsConfig.common.fireStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!isElenaiDodge2Loaded || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.fireStaff.featherConsumption)) {

            final int range = MysticStaffsConfig.common.fireStaff.range;

            for (EntityLivingBase nearbyLivingEntity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range))) {
                if (nearbyLivingEntity != player) {
                    if (MysticStaffsConfig.common.fireStaff.ignitePlayers && nearbyLivingEntity instanceof EntityPlayer)
                        continue;

                    nearbyLivingEntity.setFire(5);
                }
            }

            world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 2, 0.7F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, player.posX, player.posY - 0.5, player.posZ, 25000, 3.3, 0.5, 3.3, 0.1);

            return useItem(itemStack, player, MysticStaffsConfig.common.fireStaff.cooldown, MysticStaffsConfig.common.fireStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
