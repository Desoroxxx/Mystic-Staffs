package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.message.MessagePlayerSolarBeam;
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
import net.minecraft.world.World;

public class SolarBeamStaff extends Staff {

    public SolarBeamStaff() {
        super(MysticStaffsConfig.common.solarBeamStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!isElenaiDodge2Loaded || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.solarBeamStaff.featherConsumption)) {
            MowziesMobs.NETWORK_WRAPPER.sendToServer(new MessagePlayerSolarBeam());

            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 75, MysticStaffsConfig.common.solarBeamStaff.slowness));

            return useItem(itemStack, player, MysticStaffsConfig.common.solarBeamStaff.cooldown, MysticStaffsConfig.common.solarBeamStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
