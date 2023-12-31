package net.isaiah.deathstatues.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.isaiah.deathstatues.DeathStatues;
import net.isaiah.deathstatues.block.ModBlocks;
import net.isaiah.deathstatues.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DEATH_STATUE_BLOCK)
                .pattern("FFF")
                .pattern("FNF")
                .pattern("FAF")
                .input('N', Items.NAME_TAG)
                .input('F', Items.ITEM_FRAME)
                .input('A', Items.ARMOR_STAND)
                .criterion(hasItem(Items.NAME_TAG), conditionsFromItem(Items.NAME_TAG))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.ITEM_FRAME))
                .criterion(hasItem(Items.ARMOR_STAND), conditionsFromItem(Items.ARMOR_STAND))
                .offerTo(exporter, new Identifier(DeathStatues.MOD_ID, "death_statue_block"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DEATH_STATUE_BASE_BLOCK)
                .pattern("FFF")
                .pattern("FNF")
                .pattern("FFF")
                .input('N', Items.NAME_TAG)
                .input('F', Items.IRON_BLOCK)
                .criterion(hasItem(Items.NAME_TAG), conditionsFromItem(Items.NAME_TAG))
                .criterion(hasItem(Items.ITEM_FRAME), conditionsFromItem(Items.IRON_BLOCK))
                .offerTo(exporter, new Identifier(DeathStatues.MOD_ID, "death_statue_base_block"));

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Items.IRON_BLOCK, RecipeCategory.MISC, ModBlocks.DEATH_STATUE_BASE_BLOCK);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DEATH_STATUE_ITEM)
                .input(ModBlocks.DEATH_STATUE_BLOCK.asItem())
                .input(Items.ARMOR_STAND)
                .criterion(hasItem(ModBlocks.DEATH_STATUE_BLOCK.asItem()), conditionsFromItem(ModBlocks.DEATH_STATUE_BLOCK.asItem()))
                .criterion(hasItem(Items.ARMOR_STAND), conditionsFromItem(Items.ARMOR_STAND))
                .offerTo(exporter, new Identifier(DeathStatues.MOD_ID, "death_statue_item"));
    }
}
