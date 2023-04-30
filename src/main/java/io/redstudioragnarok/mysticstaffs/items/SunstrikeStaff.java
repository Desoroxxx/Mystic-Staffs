package io.redstudioragnarok.mysticstaffs.items;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.config.MysticStaffsConfig;
import io.redstudioragnarok.mysticstaffs.utils.MysticStaffsUtils;
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

    private static final SoundEvent glow = new SoundEvent(new ResourceLocation("mysticstaffs", "glow"));

    private final Queue<EntitySunstrike> sunstrikeQueue = new LinkedList<>();

    private int delay = 0;

    public SunstrikeStaff() {
        super(MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.durability);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote && (!isElenaiDodge2Loaded || FeathersHelper.getFeatherLevel((EntityPlayerMP) player) >= MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.featherConsumption)) {
            RayTraceResult rayTraceResult = MysticStaffsUtils.rayTraceWithExtendedReach(world, player);

            if ((rayTraceResult != null) && rayTraceResult.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                final BlockPos target = new BlockPos.MutableBlockPos(rayTraceResult.getBlockPos());

                final int area = MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.radius;

                for (int i = 0; i < MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.amount; i++) {
                    final int offsetX = MysticStaffsUtils.random.nextInt((area * 2) + 1) - area;
                    final int offsetZ = MysticStaffsUtils.random.nextInt((area * 2) + 1) - area;

                    sunstrikeQueue.add(new EntitySunstrike(world, player, target.getX() + offsetX, target.getY(), target.getZ() + offsetZ));
                }

                world.playSound(null, player.getPosition(), glow, SoundCategory.PLAYERS, 2.0F, 0.7F);

                return useItem(itemStack, player, MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.cooldown, MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.featherConsumption);
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
                delay = MysticStaffsUtils.random.nextInt(MysticStaffsConfig.common.mowziesStaffs.sunstrikeStaff.delay) + 1;
            } else {
                delay--;
            }
        }
    }
}
