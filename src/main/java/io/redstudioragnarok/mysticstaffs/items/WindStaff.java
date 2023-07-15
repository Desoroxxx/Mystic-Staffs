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

    private static final SoundEvent GUST = new SoundEvent(new ResourceLocation("mysticstaffs", "gust"));

    public WindStaff() {
        super(MysticStaffsConfig.COMMON.windStaff.durability);
    }

    /**
     * Activates the Wind Staff's ability when the item is right-clicked.
     * <p>
     * Launches the player in the direction they are facing with configurable forward and upward strength.
     * The player's fall distance is reduced.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.windStaff.featherConsumption)) {
            MysticStaffsUtils.launchPlayerInDirection(player, MysticStaffsConfig.COMMON.windStaff.forwardStrength, MysticStaffsConfig.COMMON.windStaff.upwardStrength);

            player.fallDistance -= 6;

            world.playSound(null, player.getPosition(), GUST, SoundCategory.PLAYERS, 2, 0.7F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, player.posX, player.posY, player.posZ, 1000, 0, 0, 0, 0.6, 1000);

            return useItem(itemStack, player, MysticStaffsConfig.COMMON.windStaff.cooldown, MysticStaffsConfig.COMMON.windStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
