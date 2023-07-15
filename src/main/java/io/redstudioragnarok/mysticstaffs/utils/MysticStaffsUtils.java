package io.redstudioragnarok.mysticstaffs.utils;

import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

/**
 * This utility class provides several useful methods for manipulating entities, particles, and ray tracing.
 */
public class MysticStaffsUtils {

    public static final Random RANDOM = new Random();

    /**
     * Spawns an explosion particle effect around the given entity in the world.
     *
     * @param entity The entity around which to spawn the particle effect
     * @param amount The number of particles to spawn
     */
    public static void spawnExplosionParticleAtEntity(final Entity entity, final int amount) {
        // Generate random values for particle velocity and offsets in each axis
        final double velocity = RANDOM.nextGaussian() / 8;
        final double xOffset = RANDOM.nextGaussian() / 12;
        final double yOffset = RANDOM.nextGaussian() / 12;
        final double zOffset = RANDOM.nextGaussian() / 12;

        // Spawn explosion particles around the entity with the specified amount and random offsets and velocity
        ((WorldServer) entity.getEntityWorld()).spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entity.posX, entity.posY, entity.posZ, amount, xOffset, yOffset, zOffset, MathHelper.clamp(velocity, 0.06, 1));
    }

    /**
     * Performs a ray trace with extended reach in the world using the player's position, rotation, and a reach multiplier.
     *
     * @param world The world in which to perform the ray trace
     * @param player The player whose position and rotation to use for the ray trace
     * @return A RayTraceResult object containing information about the block that was hit (if any)
     */
    public static RayTraceResult rayTraceWithExtendedReach(final World world, final EntityPlayer player) {
        // Calculate the start position of the ray trace using player's position and eye height
        final Vec3d startPosition = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);

        // Get player's rotation yaw and compute cosine and sine values
        final float yaw = player.rotationYaw;
        final float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        final float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);

        // Get player's rotation pitch and compute cosine and sine values
        final float pitch = player.rotationPitch;
        final float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        final float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        // Get the reach multiplier from the configuration
        final float reachMultiplier = MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.reachMultiplier;

        // Calculate the end position of the ray trace using the player's rotation and reach multiplier
        final Vec3d endPosition = startPosition.add((sinYaw * cosPitch) * reachMultiplier, sinPitch * reachMultiplier, (cosYaw * cosPitch) * reachMultiplier);

        // Perform the ray trace in the world and return the result
        return world.rayTraceBlocks(startPosition, endPosition, false, true, false);
    }

    /**
     * Launches the given player in a specified direction with forward and upward strengths.
     *
     * @param player The EntityPlayer instance representing the player to be launched.
     * @param forwardStrength The strength of the horizontal launch force (positive values launch the player forward).
     * @param upwardStrength The strength of the vertical launch force (positive values launch the player upwards).
     */
    public static void launchPlayerInDirection(final EntityPlayer player, final float forwardStrength, final float upwardStrength) {
        // Convert player's rotation yaw and pitch to radians
        float yaw = (float) Math.toRadians(player.rotationYaw);
        float pitch = (float) Math.toRadians(player.rotationPitch);

        // Calculate the horizontal components of the launch force using yaw and pitch angles
        player.motionX = (-MathHelper.sin(yaw) * MathHelper.cos(pitch) * forwardStrength);
        player.motionZ = (MathHelper.cos(yaw) * MathHelper.cos(pitch) * forwardStrength);

        // Set the vertical component of the launch force
        player.motionY = upwardStrength;

        // Mark the player's velocity as changed to ensure the update is processed by the client
        player.velocityChanged = true;
    }
}
