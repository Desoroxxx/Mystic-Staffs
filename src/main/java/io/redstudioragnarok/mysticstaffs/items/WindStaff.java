package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import io.redstudioragnarok.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class WindStaff extends Staff {

    private static final SoundEvent gust = new SoundEvent(new ResourceLocation("mysticstaffs", "gust"));

    public WindStaff() {
        super(MysticStaffsConfig.common.windStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.windStaff.featherConsumption) {
            MysticStaffsUtils.launchPlayerInDirection(player, MysticStaffsConfig.common.windStaff.forwardStrength, MysticStaffsConfig.common.windStaff.upwardStrength);

            player.fallDistance -= 6;

            world.playSound(null, player.getPosition(), gust, SoundCategory.PLAYERS, 2, 0.7F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, player.posX, player.posY, player.posZ, 1000, 0, 0, 0, 0.6, 1000);

            return useItem(itemStack, player, MysticStaffsConfig.common.windStaff.cooldown, MysticStaffsConfig.common.windStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
