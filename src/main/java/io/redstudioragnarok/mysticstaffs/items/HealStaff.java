package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class HealStaff extends Staff {

    public HealStaff() {
        super(MysticStaffsConfig.common.healStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.healStaff.featherConsumption) {
            PotionEffect instantHeal = new PotionEffect(MobEffects.INSTANT_HEALTH, 1, MysticStaffsConfig.common.healStaff.strength);

            final int range = MysticStaffsConfig.common.healStaff.range;

            for (EntityPlayer nearbyPlayer : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range)))
                nearbyPlayer.addPotionEffect(instantHeal);

            return useItem(itemStack, player, MysticStaffsConfig.common.healStaff.cooldown, MysticStaffsConfig.common.healStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

}
