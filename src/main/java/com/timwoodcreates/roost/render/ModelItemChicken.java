package com.timwoodcreates.roost.render;

import com.google.common.collect.ImmutableList;
import com.setycz.chickens.registry.ChickensRegistry;
import com.setycz.chickens.registry.ChickensRegistryItem;
import com.timwoodcreates.roost.data.DataChicken;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class ModelItemChicken implements IModel {
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelItemChicken(state, format, bakedTextureGetter);
    }

    public static Collection<ResourceLocation> getItemTextures() {
        List<ResourceLocation> textures = ChickensRegistry.getItems().stream().map((item) ->
            new ResourceLocation("roost", "items/chicken/" /* item.getRegistryName().getResourceDomain() */
                    + item.getRegistryName().getResourcePath())
        ).collect(Collectors.toList());
        textures.add(
            new ResourceLocation("roost", "items/chicken/vanilla")
        );
        return textures;
    }

    @SubscribeEvent
    public static void preStitch(TextureStitchEvent.Pre event) {
        for (ResourceLocation itemTexture : getItemTextures()) {
            event.getMap().registerSprite(itemTexture);
        }
    }

    public static class BakedModelItemChicken implements IBakedModel {
        public Map<String, IBakedModel> models = new HashMap<>();
        public ChickenItemOverrideList overrides = new ChickenItemOverrideList();

        public BakedModelItemChicken(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
            try {
                IModel model = ModelLoaderRegistry.getModel(new ResourceLocation("roost:item/chicken/vanilla"));

                for (ChickensRegistryItem item : ChickensRegistry.getItems()) {
                    ResourceLocation name = item.getRegistryName();
                    ResourceLocation texLoc = new ResourceLocation("roost", "items/chicken/"
                            /* + name.getResourceDomain() + "/" */
                            + name.getResourcePath());
                    IBakedModel baked = model.bake(state, format, (loc) -> bakedTextureGetter.apply(texLoc));
                    models.put(name.toString(), baked);

                }

                IBakedModel baked = model.bake(state, format, bakedTextureGetter);
                models.put("minecraft:vanilla", baked);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return ImmutableList.of();
        }

        @Override
        public boolean isAmbientOcclusion() {
            return false;
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return null;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return overrides;
        }

        public class ChickenItemOverrideList extends ItemOverrideList {

            public ChickenItemOverrideList() {
                super(ImmutableList.of());
            }

            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                DataChicken chickenData = DataChicken.getDataFromStack(stack);
                if (chickenData != null) {
                    String name = chickenData.getChickenType();
                    IBakedModel model = BakedModelItemChicken.this.models.getOrDefault(name, null);
                    if (model != null) return model;
                }
                return BakedModelItemChicken.this;
            }

        }
    }
    public static class ModelItemChickenLoader implements ICustomModelLoader {
        static public ModelItemChickenLoader INSTANCE = new ModelItemChickenLoader();

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            if (modelLocation.getResourceDomain().equals("roost") && modelLocation.getResourcePath().equals("models/item/chicken"))
                return true;

            return false;
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception {
            return new ModelItemChicken();
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {

        }
    }
}
