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
        super(MysticStaffsConfig.common.mowziesStaffs.solarBeamStaff.durability);
    }

    /**
     * Activates the Solar Beam Staff's ability when the item is right-clicked.
     * <p>
     * Sends a message to the server to perform a solar beam attack.
     * The player is given a configurable slowness effect during the attack.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!isElenaiDodge2Loaded || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.mowziesStaffs.solarBeamStaff.featherConsumption)) {
            MowziesMobs.NETWORK_WRAPPER.sendToServer(new MessagePlayerSolarBeam());

            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 75, MysticStaffsConfig.common.mowziesStaffs.solarBeamStaff.slowness));

            return useItem(itemStack, player, MysticStaffsConfig.common.mowziesStaffs.solarBeamStaff.cooldown, MysticStaffsConfig.common.mowziesStaffs.solarBeamStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
