package dev.redstudio.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import dev.redstudio.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HealStaff extends Staff {

    private static final SoundEvent resurrection = new SoundEvent(new ResourceLocation("mysticstaffs", "resurrection"));

    public HealStaff() {
        super(MysticStaffsConfig.COMMON.healStaff.durability);
    }

    /**
     * Activates the Heal Staff's ability when the item is right-clicked.
     * <p>
     * Applies the Instant Health effect to nearby living entities within the configured range, with different strengths for the caster and other living entities.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.healStaff.featherConsumption)) {
            final int range = MysticStaffsConfig.COMMON.healStaff.range;

            for (EntityLivingBase nearbyLivingEntity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range))) {
                if (nearbyLivingEntity == player)
                    nearbyLivingEntity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, MysticStaffsConfig.COMMON.healStaff.strengthCaster));
                else
                    nearbyLivingEntity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, MysticStaffsConfig.COMMON.healStaff.strength));
            }

            world.playSound(null, player.getPosition(), resurrection, SoundCategory.PLAYERS, 0.5F, 0.9F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.SPELL_WITCH, player.posX, player.posY, player.posZ, 2000, 2.75, 0.5, 2.75, 0.1);

            return useItem(itemStack, player, MysticStaffsConfig.COMMON.healStaff.cooldown, MysticStaffsConfig.COMMON.healStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
