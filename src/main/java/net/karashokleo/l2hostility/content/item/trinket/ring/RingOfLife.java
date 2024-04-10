package net.karashokleo.l2hostility.content.item.trinket.ring;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfLife extends CurseTrinketItem
{
    public RingOfLife(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int perc = (int) Math.round(LHConfig.common().items.ringOfLifeMaxDamage * 100);
        tooltip.add(LHTexts.ITEM_RING_LIFE.get(perc).formatted(Formatting.GOLD));
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        boolean bypassInvul = event.getSource().isIn(DamageTypeTags.BYPASSES_INVULNERABILITY);
        boolean bypassMagic = event.getSource().isIn(DamageTypeTags.BYPASSES_EFFECTS);
        if (!bypassInvul && !bypassMagic)
        {
            float damage = event.getAmount();
            float maxHealth = event.getEntity().getMaxHealth();
            damage = Math.min(damage, (float) (maxHealth * LHConfig.common().items.ringOfLifeMaxDamage));
            event.setAmount(damage);
        }
    }
}
