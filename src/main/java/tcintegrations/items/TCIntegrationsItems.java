package tcintegrations.items;

import java.util.function.Function;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;

import tcintegrations.client.CreativeTabBase;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.items.tool.modifiers.ManaItemModifier;
import tcintegrations.TCIntegrations;

public final class TCIntegrationsItems extends TCIntegrationsModule {

    public static CreativeTabBase ITEM_TAB_GROUP;
    public static Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM;

    public static FluidObject<ForgeFlowingFluid> MANASTEEL;

    public static MetalItemObject BRONZE;

    public static RegistryObject<ManaItemModifier> MANA_MODIFIER;

    public static void init() {
        ITEM_TAB_GROUP = new CreativeTabBase(TCIntegrations.MODID + ".items", () -> new ItemStack(BRONZE.getNugget()));
        GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, new Item.Properties().tab(ITEM_TAB_GROUP));

        // Fluids
        MANASTEEL = FLUID_REGISTRY.register(
                "manasteel", hotBuilder().temperature(1250), Material.LAVA, 13);

        // Metals
        BRONZE = METAL_BLOCK_REGISTRY.registerMetal(
            "bronze",
            metalBuilder(MaterialColor.WOOD),
            GENERAL_TOOLTIP_BLOCK_ITEM,
            new Item.Properties().tab(ITEM_TAB_GROUP)
        );

        // Modifiers
        MANA_MODIFIER = MODIFIERS_REGISTRY.register("mana", ManaItemModifier::new);
    }

    private static FluidAttributes.Builder hotBuilder() {
        return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(
            SoundEvents.BUCKET_FILL_LAVA,
            SoundEvents.BUCKET_EMPTY_LAVA
        );
    }

    private static BlockBehaviour.Properties builder(Material material, MaterialColor color, SoundType soundType) {
        return Block.Properties.of(material, color).sound(soundType);
    }

    private static BlockBehaviour.Properties metalBuilder(MaterialColor color) {
        return builder(Material.METAL, color, SoundType.METAL).requiresCorrectToolForDrops().strength(5.0f);
    }

}
