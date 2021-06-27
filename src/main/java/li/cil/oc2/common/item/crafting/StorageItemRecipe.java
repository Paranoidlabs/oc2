package li.cil.oc2.common.item.crafting;

import li.cil.oc2.api.API;
import li.cil.oc2.common.item.AbstractStorageItem;
import li.cil.oc2.common.item.Items;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public final class StorageItemRecipe extends SpecialRecipe {

    public static IRecipeSerializer<?> SERIALIZER = new SpecialRecipeSerializer<>(StorageItemRecipe::new).setRegistryName(new ResourceLocation(API.MOD_ID, "storage"));

    public StorageItemRecipe(final ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(final @NotNull CraftingInventory craftingInventory, final @NotNull World w) {
        return !this.getOutput(craftingInventory).isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(final @NotNull CraftingInventory craftingInventory) {
        return this.getOutput(craftingInventory);
    }

    @Override
    public boolean canCraftInDimensions(final int p_194133_1_, final int p_194133_2_) {
        return true;
    }

    private ItemStack getOutput(final CraftingInventory inv) {
        // This checks if the recipe is equals to:
        // " T "
        // "MSM"
        // " B "
        // where T is a Transistor, M is not empty, S is a Slimeball and B is a Circut Board
        // It returns ItemStack.EMPTY when it does not equal. Everything else gets checked afterwards.
        if (!(
                inv.getItem(0).isEmpty() &&
                inv.getItem(1).getItem() == Items.TRANSISTOR.get().getItem() &&
                inv.getItem(2).isEmpty() &&
                !inv.getItem(3).isEmpty() &&
                inv.getItem(4).getItem() == net.minecraft.item.Items.SLIME_BALL.getItem() &&
                !inv.getItem(5).isEmpty() &&
                inv.getItem(6).isEmpty() &&
                inv.getItem(7).getItem() == Items.CIRCUIT_BOARD.get().getItem() &&
                inv.getItem(8).isEmpty()
        )) {
            return ItemStack.EMPTY;
        }

        ItemStack inputItemLeft = inv.getItem(3);
        ItemStack inputItemRight = inv.getItem(5);

        // Now we check if the "M" are identical Items
        if (inputItemLeft.getItem() != inputItemRight.getItem()) {
            return ItemStack.EMPTY;
        }

        if (!(inputItemLeft.getItem() instanceof AbstractStorageItem)) {
            return ItemStack.EMPTY;
        }
        AbstractStorageItem storageItem = (AbstractStorageItem) inputItemLeft.getItem();

        if (storageItem.getCapacity(inputItemLeft) != storageItem.getCapacity(inputItemRight)) {
            return ItemStack.EMPTY;
        }

        int newCapacity = storageItem.getCapacity(inputItemLeft) * 2;
        if (newCapacity > storageItem.getMaxCapacity()) {
            return ItemStack.EMPTY;
        }

        return storageItem.withCapacity(newCapacity);
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
