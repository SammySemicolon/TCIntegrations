package tcintegrations.items.tool.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import vazkii.botania.api.mana.ManaItemHandler;

import tcintegrations.util.BotaniaHelper;

public class ManaItemModifier extends NoLevelsModifier {

    private static final int MANA_PER_DAMAGE = 60;

    public int getManaPerDamage(Player player) {
        return BotaniaHelper.getManaPerDamageBonus(player, MANA_PER_DAMAGE);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide
                && holder.tickCount % 20 == 0
                && holder instanceof Player player
                && tool.getDamage() > 0
                && ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerDamage(player) * 2, true)) {
            tool.setDamage(tool.getDamage() - 1);
        }
    }

}
