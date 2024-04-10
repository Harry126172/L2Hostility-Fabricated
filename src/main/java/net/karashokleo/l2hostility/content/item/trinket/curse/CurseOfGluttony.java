package net.karashokleo.l2hostility.content.item.trinket.curse;

import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfGluttony extends CurseTrinketItem
{
    public CurseOfGluttony(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int rate = (int) Math.round(100 * LHConfig.common().items.curse.gluttonyBottleDropRate);
        tooltip.add(LHTexts.ITEM_CHARM_GLUTTONY.get(rate).formatted(Formatting.GOLD));
    }
}
