package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBoulder;
import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import io.redstudioragnarok.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.Queue;

public class PathStaff extends Staff {

    private final Queue<EntityBoulder> boulderQueue = new LinkedList<>();

    public PathStaff() {
        super(MysticStaffsConfig.common.pathStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.pathStaff.featherConsumption) {
            for (int i = 0; i < MysticStaffsConfig.common.pathStaff.length; i++) {
                final double yaw = Math.toRadians(player.rotationYaw);
                double pitch = Math.toRadians(player.rotationPitch);

                if (Math.abs(Math.toDegrees(pitch)) < 6) // Check if the pitch is in the deadzone, if true set the pitch to 0
                    pitch = 0;

                if (pitch <= -0.85) // Check if the pitch is above the max height or under the min height, if true set the pitch to the maximum usable upward stairway
                    pitch = -0.84;
                else if (pitch >= 0.85) // Check if the pitch is below the min height, if true set the pitch to the minimum usable downward stairway
                    pitch = 0.84;

                final double x = -Math.sin(yaw) * Math.cos(pitch);
                double y = -Math.sin(pitch);
                final double z = Math.cos(yaw) * Math.cos(pitch);

                if (pitch <= -0.19)
                    y -= 0.25;

                double yOffset = 1;

                if (pitch <= -0.41) // Check if the pitch is above the min height to be an upward stairway, if true set the yOffset to 0.75 to allow the player to climb on the stairs
                    yOffset = 0.8;

                final double distance = 2 + i * 1.5;

                EntityBoulder boulder = new EntityBoulder(world, player, 0, Blocks.ICE.getDefaultState());

                boulder.setDeathTime(MysticStaffsConfig.common.pathStaff.lifetime);
                boulder.setPosition(player.posX + x * distance, (player.posY - yOffset) + y * distance, player.posZ + z * distance);

                if (world.getBlockState(boulder.getPosition()).getMaterial() == Material.AIR)
                    boulderQueue.add(boulder);
            }

            player.fallDistance = -9;

            world.playSound(null, player.getPosition(), new SoundEvent(new ResourceLocation("element", "gust")), SoundCategory.MASTER, 2.0F, 0.7F);

            return useItem(itemStack, player, MysticStaffsConfig.common.pathStaff.cooldown, MysticStaffsConfig.common.pathStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            EntityBoulder boulder = boulderQueue.poll();

            if (boulder != null) {
                world.spawnEntity(boulder);
                MysticStaffsUtils.spawnExplosionParticleAtEntity(boulder, 25);
            }
        }
    }
}
