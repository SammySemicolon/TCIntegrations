package tcintegrations.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.crafting.conditions.TrueCondition;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import tcintegrations.common.json.ConfigEnabledCondition;
import tcintegrations.data.tcon.SmelteryCompat;
import tcintegrations.items.TCIntegrationsItems;

public class SmelteryRecipeProvider extends BaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {

    public SmelteryRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Smeltery Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
        this.addCastingRecipes(consumer);
        this.addAlloyRecipes(consumer);
    }

    private void addCastingRecipes(Consumer<FinishedRecipe> consumer) {
        // Pure Fluid Recipes
        String folder = "smeltery/casting/";

        // Molten objects with Bucket, Block, Ingot, and Nugget forms with standard values
        String metalFolder = folder + "metal/";

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            this.metalTagCasting(consumer, compat.getFluid(), compat.getName(), metalFolder, false);
        }
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";

        // ores
        String metalFolder = folder + "metal/";
        metalMelting(consumer, TCIntegrationsItems.MANASTEEL.get(), "manasteel", false, metalFolder, false, Byproduct.IRON);
    }

    private void addAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/alloys/";

        ConditionalRecipe.builder()
            // TODO: Fix ConfigEnabledCondition. No idea why it isn't registering properly
            //.addCondition(ConfigEnabledCondition.BRONZE_RECIPE)
            .addCondition(TrueCondition.INSTANCE)
            .addRecipe(
                AlloyRecipeBuilder.alloy(TinkerFluids.moltenBronze.get(), FluidValues.INGOT * 4)
                    .addInput(TinkerFluids.moltenCopper.getForgeTag(), FluidValues.INGOT * 3)
                    .addInput(TinkerFluids.moltenQuartz.getLocalTag(), FluidValues.GEM)::save)
            .build(consumer, prefix(TinkerFluids.moltenBronze, folder));
    }

}
