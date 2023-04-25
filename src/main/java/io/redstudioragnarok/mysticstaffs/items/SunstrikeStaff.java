package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import io.redstudioragnarok.mysticstaffs.utils.MysticStaffsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SunstrikeStaff extends Staff {

    private static final Random random = new Random();

    private final Queue<EntitySunstrike> sunstrikeQueue = new LinkedList<>();

    private int delay = 0;

    public SunstrikeStaff() {
        super(MysticStaffsConfig.common.sunstrikeStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.sunstrikeStaff.featherConsumption) {
            RayTraceResult rayTraceResult = MysticStaffsUtils.rayTraceWithExtendedReach(world, player);

            if ((rayTraceResult != null) && rayTraceResult.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                final BlockPos target = new BlockPos.MutableBlockPos(rayTraceResult.getBlockPos());

                final int area = MysticStaffsConfig.common.sunstrikeStaff.radius;

                for (int i = 0; i < MysticStaffsConfig.common.sunstrikeStaff.amount; i++) {
                    final int offsetX = random.nextInt((area * 2) + 1) - area;
                    final int offsetZ = random.nextInt((area * 2) + 1) - area;

                    sunstrikeQueue.add(new EntitySunstrike(world, player, target.getX() + offsetX, target.getY(), target.getZ() + offsetZ));
                }

                return useItem(itemStack, player, MysticStaffsConfig.common.sunstrikeStaff.cooldown, MysticStaffsConfig.common.sunstrikeStaff.featherConsumption);
            }
        }

        player.swingArm(hand);

        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && !sunstrikeQueue.isEmpty()) {
            if (delay == 0) {
                world.spawnEntity(sunstrikeQueue.poll());
                delay = random.nextInt(MysticStaffsConfig.common.sunstrikeStaff.delay) + 1;
            } else {
                delay--;
            }
        }
    }
}
