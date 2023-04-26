package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class InvisibilityStaff extends Staff {

    public InvisibilityStaff() {
        super(MysticStaffsConfig.common.invisibilityStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.invisibilityStaff.featherConsumption) {
            player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, MysticStaffsConfig.common.invisibilityStaff.duration));

            world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 1.5F, 0.8F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.SPELL_INSTANT, player.posX, player.posY, player.posZ, 10000, 0.6, 1.25, 0.6, 0.1);

            return useItem(itemStack, player, MysticStaffsConfig.common.invisibilityStaff.cooldown, MysticStaffsConfig.common.invisibilityStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
