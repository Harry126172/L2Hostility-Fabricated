package net.karashokleo.l2hostility.content.component.chunk;

import net.karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import net.minecraft.util.math.BlockPos;

public interface RegionalDifficultyModifier
{
    void modifyInstance(BlockPos pos, MobDifficultyCollector instance);
}
