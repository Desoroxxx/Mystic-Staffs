package io.redstudioragnarok.mysticstaffs.utils;

import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.jafama.FastMath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class MysticStaffsUtils {

    public static final Random random = new Random();

    /**
     * Spawns a particle effect around the given entity in the world.
     *
     * @param particle The particle to spawn
     * @param entity The entity around which to spawn the particle effect
     * @param amount The number of particles to spawn
     */
    public static void spawnParticleAtEntity(final EnumParticleTypes particle, final Entity entity, final int amount) {
        final double velocity = random.nextGaussian() / 8;
        final double xOffset = random.nextGaussian() / 12;
        final double yOffset = random.nextGaussian() / 12;
        final double zOffset = random.nextGaussian() / 12;

        ((WorldServer) entity.getEntityWorld()).spawnParticle(particle, entity.posX, entity.posY, entity.posZ, amount, xOffset, yOffset, zOffset, MathHelper.clamp(velocity, 0.06, 1));
    }

    /**
     * Performs a ray trace with extended reach in the world using the player's position, rotation and by calculating the reach with the config options.
     *
     * @param world The world in which to perform the ray trace
     * @param player The player whose position and rotation to use for the ray trace
     * @return A RayTraceResult object containing information about the block that was hit (if any)
     */
    public static RayTraceResult rayTraceWithExtendedReach(final World world, final EntityPlayer player) {
        final Vec3d startPosition = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);

        final float yaw = player.rotationYaw;
        final float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float) FastMath.PI);
        final float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float) FastMath.PI);

        final float pitch = player.rotationPitch;
        final float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        final float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        final float reachMultiplier = MysticStaffsConfig.common.sunstrikeStaff.reachMultiplier;

        final Vec3d endPosition = startPosition.add((sinYaw * cosPitch) * reachMultiplier, sinPitch * reachMultiplier, (cosYaw * cosPitch) * reachMultiplier);

        return world.rayTraceBlocks(startPosition, endPosition, false, true, false);
    }
}
