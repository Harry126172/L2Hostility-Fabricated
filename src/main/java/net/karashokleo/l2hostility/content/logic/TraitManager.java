package net.karashokleo.l2hostility.content.logic;

import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.HashMap;

public class TraitManager
{
    // 增加属性
    public static void addAttribute(LivingEntity le, EntityAttribute attr, String name, double factor, EntityAttributeModifier.Operation op)
    {
        var ins = le.getAttributeInstance(attr);
        if (ins == null) return;
        var modifier = new EntityAttributeModifier(MathHelper.getUUIDFromString(name), name, factor, op);
        if (ins.hasModifier(modifier))
            ins.removeModifier(modifier.getId());
        ins.addPersistentModifier(modifier);
    }

    // 怪物难度实现
    public static int fill(LivingEntity le, HashMap<MobTrait, Integer> traits, MobDifficultyCollector ins)
    {
        int lv = ins.getDifficulty(le.getRandom());
        int ans = 0;
        if (ins.apply_chance() < le.getRandom().nextDouble())
            return ans;
        // add attributes
        if (!le.getType().isIn(LHTags.NO_SCALING))
        {
            double factor;
            if (LHConfig.common().scaling.exponentialHealth)
                factor = Math.pow(1 + LHConfig.common().scaling.healthFactor, lv) - 1;
            else
                factor = lv * LHConfig.common().scaling.healthFactor;
            addAttribute(le, EntityAttributes.GENERIC_MAX_HEALTH, "hostility_health", factor,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
            ans = lv;
        }
        // armor
        if (le.getType().isIn(LHTags.ARMOR_TARGET))
        {
            ItemPopulator.populateArmors(le, lv);
        }
        // add traits
        if (ins.trait_chance(lv) >= le.getRandom().nextDouble())
        {
            if (!le.getType().isIn(LHTags.NO_TRAIT))
                TraitGenerator.generateTraits(le, lv, traits, ins);
            ans = lv;
        }
        le.setHealth(le.getMaxHealth());
        return ans;
    }

    public static int getMaxLevel()
    {
        return 5;
    }

    public static int getTraitCap(int maxRankKilled, DifficultyLevel diff)
    {
        return maxRankKilled + 1;
    }
}
