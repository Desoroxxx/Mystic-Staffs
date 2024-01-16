package dev.redstudio.mysticstaffs.items;

import com.elenai.elenaidodge2.api.FeathersHelper;
import dev.redstudio.mysticstaffs.creativetab.StaffsTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.fml.common.Loader;

/**
 * This is the base class for all staffs in the mod.
 * <p>
 * It extends the Item class and provides common functionality for staffs, such as handling durability and usage.
 */
public class Staff extends Item {

    public static final boolean IS_ELENAI_DODGE_2_LOADED = Loader.isModLoaded("elenaidodge2");

    public static final StaffsTab STAFFS_TAB = new StaffsTab();

    private final int durability;

    /**
     * Constructor for the Staff class.
     *
     * @param durabilityInput The durability value for the staff
     */
    protected Staff(final int durabilityInput) {
        setCreativeTab(STAFFS_TAB);

        maxStackSize = 1;

        durability = durabilityInput;

        if (durability > 1)
            setMaxDamage(durability - 1);
        else if (durability == 1)
            setMaxDamage(1);
    }

    /**
     * Use the staff item, consuming resources as necessary.
     *
     * @param itemStack The ItemStack representing the staff item being used
     * @param player The EntityPlayer using the staff item
     * @param cooldown The cooldown duration (in ticks) to apply after using the staff item
     * @param featherConsumption The number of feathers to consume upon using the staff item
     * @return An ActionResult object containing the result of the action and the modified ItemStack
     */
    protected ActionResult<ItemStack> useItem(ItemStack itemStack, EntityPlayer player, int cooldown, int featherConsumption) {
        if (IS_ELENAI_DODGE_2_LOADED)
            FeathersHelper.decreaseFeathers((EntityPlayerMP) player, featherConsumption);

        player.getCooldownTracker().setCooldown(this, cooldown);

        if (durability == 1)
            itemStack.damageItem(2, player);
        else if (durability > 0)
            itemStack.damageItem(1, player);

        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }
}
