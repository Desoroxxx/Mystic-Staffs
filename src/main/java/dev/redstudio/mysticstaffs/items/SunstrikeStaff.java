package dev.redstudio.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.elenai.elenaidodge2.api.FeathersHelper;
import dev.redstudio.mysticstaffs.config.MysticStaffsConfig;
import dev.redstudio.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.Queue;

public class SunstrikeStaff extends Staff {

    private static final SoundEvent GLOW = new SoundEvent(new ResourceLocation("mysticstaffs", "glow"));

    private final Queue<EntitySunstrike> sunstrikeQueue = new LinkedList<>();

    private int delay = 0;

    public SunstrikeStaff() {
        super(MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.durability);
    }

    /**
     * Activates the Sunstrike Staff's ability when the item is right-clicked.
     * <p>
     * Creates multiple sunstrikes around a targeted block within a specified radius.
     * The sunstrikes are created as EntitySunstrike instances, which are queued and then spawned in the world during onUpdate method, with a configurable delay between each.
     * <p>
     * The ability will only be activated if the ray tracing result is a block.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!IS_ELENAI_DODGE_2_LOADED || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.featherConsumption)) {
            RayTraceResult rayTraceResult = MysticStaffsUtils.rayTraceWithExtendedReach(world, player);

            if ((rayTraceResult != null) && rayTraceResult.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                final BlockPos target = new BlockPos.MutableBlockPos(rayTraceResult.getBlockPos());

                final int area = MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.radius;

                for (int i = 0; i < MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.amount; i++) {
                    final int offsetX = MysticStaffsUtils.RANDOM.nextInt((area * 2) + 1) - area;
                    final int offsetZ = MysticStaffsUtils.RANDOM.nextInt((area * 2) + 1) - area;

                    sunstrikeQueue.add(new EntitySunstrike(world, player, target.getX() + offsetX, target.getY(), target.getZ() + offsetZ));
                }

                world.playSound(null, player.getPosition(), GLOW, SoundCategory.PLAYERS, 2.0F, 0.7F);

                return useItem(itemStack, player, MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.cooldown, MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.featherConsumption);
            }
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    /**
     * Called each tick as long the item is on a player inventory.
     * <p>
     * Handles the delay between sunstrike spawns.
     * Updates the sunstrike queue, spawning a sunstrike and decrementing the delay counter.
     */
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && !sunstrikeQueue.isEmpty()) {
            if (delay == 0) {
                world.spawnEntity(sunstrikeQueue.poll());
                delay = MysticStaffsUtils.RANDOM.nextInt(MysticStaffsConfig.COMMON.mowziesStaffs.sunstrikeStaff.delay) + 1;
            } else {
                delay--;
            }
        }
    }
}
