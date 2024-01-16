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

public class GlowStaff extends Staff {

    private static final SoundEvent glow = new SoundEvent(new ResourceLocation("mysticstaffs", "glow"));

    public GlowStaff() {
        super(MysticStaffsConfig.COMMON.glowStaff.durability);
    }

    /**
     * Activates the Glow Staff's ability when the item is right-clicked.
     * <p>
     * Applies the Glowing effect to nearby living entities within the configured range.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.glowStaff.featherConsumption)) {
            final int range = MysticStaffsConfig.COMMON.glowStaff.range;

            for (EntityLivingBase nearbyLivingEntity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range)))
                nearbyLivingEntity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, MysticStaffsConfig.COMMON.glowStaff.duration, 0));

            world.playSound(null, player.getPosition(), glow, SoundCategory.PLAYERS, 1.5F, 0.5F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.SPELL_INSTANT, player.posX, player.posY - 1.25, player.posZ, 10000, 10, 5, 10, 0F);

            return useItem(itemStack, player, MysticStaffsConfig.COMMON.glowStaff.cooldown, MysticStaffsConfig.COMMON.glowStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
