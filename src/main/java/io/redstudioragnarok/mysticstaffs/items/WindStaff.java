package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import io.redstudioragnarok.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class WindStaff extends Staff {

    public WindStaff() {
        super(MysticStaffsConfig.common.windStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.windStaff.featherConsumption) {

            final float yaw = player.rotationYaw;
            final float pitch = player.rotationPitch - 8;

            final float strength = MysticStaffsConfig.common.windStaff.strength;

            player.motionX = (-MathHelper.sin(yaw / 180 * 3) * MathHelper.cos(pitch / 180 * 3) * strength);
            player.motionZ = (MathHelper.cos(yaw / 180 * 3) * MathHelper.cos(pitch / 180 * 3) * strength);
            player.motionY = (-MathHelper.sin(pitch / 295 * 3) * strength);

            player.velocityChanged = true;

            world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation("mysticstaffs", "gust")), SoundCategory.PLAYERS, 2, 0.7F);

            ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, player.posX, player.posY, player.posZ, 1000, 0, 0, 0, 0.6, 1000);

            return useItem(itemStack, player, MysticStaffsConfig.common.windStaff.cooldown, MysticStaffsConfig.common.windStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
