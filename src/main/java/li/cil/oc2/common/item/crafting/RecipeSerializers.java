package li.cil.oc2.common.item.crafting;

import li.cil.oc2.api.API;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public final class RecipeSerializers {
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, API.MOD_ID);

    public static final RegistryObject<WrenchRecipe.Serializer> WRENCH = RECIPE_SERIALIZERS.register("wrench", WrenchRecipe.Serializer::new);

    public static void initialize() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RECIPE_SERIALIZERS.register(modEventBus);
        modEventBus.addGenericListener(IRecipeSerializer.class, RecipeSerializers::registerRecipeSerializers);
    }

    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        registry.registerAll(
                StorageItemRecipe.SERIALIZER
        );
    }
}
