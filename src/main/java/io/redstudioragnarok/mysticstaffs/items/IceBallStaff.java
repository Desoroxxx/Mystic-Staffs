package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceBallStaff extends Staff {

    public IceBallStaff() {
        super(MysticStaffsConfig.common.iceBallStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.iceBallStaff.featherConsumption) {
            EntityIceBall iceBall = new EntityIceBall(world, player);

            Vec3d offset = player.getLookVec().scale(1.5);

            iceBall.setPositionAndRotation(player.posX + offset.x, player.posY + 1.5, player.posZ + offset.z, (float) Math.toRadians(player.rotationYaw), (float) Math.toRadians(player.rotationPitch));
            iceBall.shoot(player.getLookVec().x, player.getLookVec().y, player.getLookVec().z, MysticStaffsConfig.common.iceBallStaff.velocity, 0F);

            world.spawnEntity(iceBall);

            world.playSound(null, player.getPosition(), MMSounds.ENTITY_FROSTMAW_ICEBALL_SHOOT, SoundCategory.PLAYERS, 2, 0.7F);

            return useItem(itemStack, player, MysticStaffsConfig.common.iceBallStaff.cooldown, MysticStaffsConfig.common.iceBallStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }
}
