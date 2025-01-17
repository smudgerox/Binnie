package binnie.genetics.machine.incubator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.genetics.api.IIncubatorRecipe;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;

public class Incubator {

    public static final int[] SLOT_QUEUE = new int[] { 0, 1, 2 };
    public static final int[] SLOT_OUTPUT = new int[] { 4, 5, 6 };
    public static final int SLOT_INCUBATOR = 3;
    public static final int TANK_INPUT = 0;
    public static final int TANK_OUTPUT = 1;
    public static final float ENERGY_PER_TICK = 2.0f;
    public static final List<IIncubatorRecipe> RECIPES = new ArrayList<>();
    public static final int POWER_CAPACITY = 100000;
    public static final int INPUT_TANK_CAPACITY = 64000;
    public static final int OUTPU_TANK_CAPACITY = 64000;

    public static void addRecipes() {
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        GeneticsItems.GrowthMedium.get(1),
                        Binnie.Liquid.getLiquidStack("water", 250),
                        GeneticLiquid.GrowthMedium.get(500),
                        0.2f));
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        new ItemStack(Items.wheat),
                        GeneticLiquid.GrowthMedium.get(250),
                        GeneticLiquid.Bacteria.get(500),
                        0.2f));
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        GeneticsItems.GrowthMedium.get(1),
                        GeneticLiquid.Bacteria.get(0),
                        GeneticLiquid.Bacteria.get(500),
                        0.05f));
        Incubator.RECIPES.add(
                new IncubatorRecipe(new ItemStack(Items.sugar), GeneticLiquid.Bacteria.get(200), null, 0.5f, 0.2f)
                        .setOutputStack(GeneticsItems.Enzyme.get(5)));
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        GeneticsItems.GrowthMedium.get(1),
                        GeneticLiquid.BacteriaPoly.get(0),
                        GeneticLiquid.BacteriaPoly.get(500),
                        0.05f));
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        GeneticsItems.GrowthMedium.get(1),
                        GeneticLiquid.BacteriaVector.get(0),
                        GeneticLiquid.BacteriaVector.get(500),
                        0.05f));
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        new ItemStack(Items.dye, 1, 15),
                        GeneticLiquid.Bacteria.get(500),
                        GeneticLiquid.BacteriaPoly.get(1000),
                        0.1f));
        Incubator.RECIPES.add(
                new IncubatorRecipe(
                        new ItemStack(Items.blaze_powder),
                        GeneticLiquid.Bacteria.get(500),
                        GeneticLiquid.BacteriaVector.get(1000),
                        0.05f));

        if (BinnieCore.isApicultureActive()) {
            AlleleManager.alleleRegistry.getRegisteredAlleles().values().forEach(iAllele -> {
                IAllele[] template = BeeManager.beeRoot.getTemplate(iAllele.getUID());
                if (template != null) {
                    IIndividual individual = BeeManager.beeRoot.templateAsIndividual(template);
                    ItemStack bee = BeeManager.beeRoot.getMemberStack(individual, EnumBeeType.LARVAE.ordinal());
                    Incubator.RECIPES.add(new IncubatorRecipeLarvae(bee));
                }
            });
        }
    }
}
