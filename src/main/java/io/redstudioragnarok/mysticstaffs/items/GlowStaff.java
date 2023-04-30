package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
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
        super(MysticStaffsConfig.common.glowStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!isElenaiDodge2Loaded || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.glowStaff.featherConsumption)) {
            final int range = MysticStaffsConfig.common.glowStaff.range;

            for (EntityLivingBase nearbyLivingEntity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range)))
                nearbyLivingEntity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, MysticStaffsConfig.common.glowStaff.duration, 0));

            world.playSound(null, player.getPosition(), glow, SoundCategory.PLAYERS, 1.5F, 0.5F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.SPELL_INSTANT, player.posX, player.posY - 1.25, player.posZ, 10000, 10, 5, 10, 0F);

            return useItem(itemStack, player, MysticStaffsConfig.common.glowStaff.cooldown, MysticStaffsConfig.common.glowStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
