package dev.redstudio.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBoulder;
import com.elenai.elenaidodge2.api.FeathersHelper;
import dev.redstudio.mysticstaffs.config.MysticStaffsConfig;
import dev.redstudio.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.Queue;

public class PathStaff extends Staff {

    private final Queue<EntityBoulder> boulderQueue = new LinkedList<>();

    public PathStaff() {
        super(MysticStaffsConfig.COMMON.mowziesStaffs.pathStaff.durability);
    }

    /**
     * Activates the Path Staff's ability when the item is right-clicked.
     * <p>
     * Creates a path of ice blocks in front of the player based on the player's viewing direction.
     * <p>
     * The ice blocks are created at a specified length, and each block is placed at an increment of 1.5 blocks from the previous one.
     * The blocks are created as EntityBoulder instances, which are queued and then spawned in the world during onUpdate method.
     * <p>
     * Ice blocks will despawn after a configurable lifetime.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.mowziesStaffs.pathStaff.featherConsumption)) {
            for (int i = 0; i < MysticStaffsConfig.COMMON.mowziesStaffs.pathStaff.length; i++) {
                final double yaw = Math.toRadians(player.rotationYaw);
                double pitch = Math.toRadians(player.rotationPitch);

                if (Math.abs(Math.toDegrees(pitch)) < 8) // Check if the pitch is in the deadzone if true set the pitch to 0
                    pitch = 0;

                if (pitch <= -0.85) // Check if the pitch is above the max height or under the min height if true set the pitch to the maximum usable upward stairway
                    pitch = -0.84;
                else if (pitch >= 0.85) // Check if the pitch is below the min height if true set the pitch to the minimum usable downward stairway
                    pitch = 0.84;

                final double x = -Math.sin(yaw) * Math.cos(pitch);
                double y = -Math.sin(pitch);
                final double z = Math.cos(yaw) * Math.cos(pitch);

                if (pitch <= -0.19)
                    y -= 0.25;

                double yOffset = 0.8;

                if (pitch <= -0.41) // Check if the pitch is above the min height to be an upward stairway, if true set the yOffset to 0.75 to allow the player to climb on the stairs
                    yOffset = 1.2;

                final double distance = 2 + i * 1.5;

                EntityBoulder boulder = new EntityBoulder(world, player, 0, Blocks.ICE.getDefaultState());

                boulder.setDeathTime(MysticStaffsConfig.COMMON.mowziesStaffs.pathStaff.lifetime);
                boulder.setPosition(player.posX + x * distance, (player.posY - yOffset) + y * distance, player.posZ + z * distance);

                if (!world.getBlockState(boulder.getPosition()).getMaterial().isSolid())
                    boulderQueue.add(boulder);
            }

            return useItem(itemStack, player, MysticStaffsConfig.COMMON.mowziesStaffs.pathStaff.cooldown, MysticStaffsConfig.COMMON.mowziesStaffs.pathStaff.featherConsumption);
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    /**
     * Called each tick as long the item is on a player inventory.
     * <p>
     * If the boulder is not null, it will be spawned in the world with an explosion particle effect.
     */
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
