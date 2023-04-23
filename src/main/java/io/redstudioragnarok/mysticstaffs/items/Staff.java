package io.redstudioragnarok.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.redstudioragnarok.mysticstaffs.MysticStaffs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;

public class Staff extends Item {

    private final int durability;

    protected Staff(final int durabilityInput) {
        setCreativeTab(MysticStaffs.staffsTab);

        maxStackSize = 1;

        durability = durabilityInput;

        if (durability > 1)
            setMaxDamage(durability - 1);
        else if (durability == 1)
            setMaxDamage(1);
    }

    protected ActionResult<ItemStack> useItem(ItemStack itemStack, EntityPlayer player, int cooldown, int featherConsumption) {
        FeathersHelper.decreaseFeathers((EntityPlayerMP) player, featherConsumption);

        player.getCooldownTracker().setCooldown(this, cooldown);

        if (durability == 1)
            itemStack.damageItem(2, player);
        else if (durability > 0)
            itemStack.damageItem(1, player);

        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }
}
