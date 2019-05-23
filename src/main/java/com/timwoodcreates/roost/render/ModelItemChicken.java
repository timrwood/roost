package com.timwoodcreates.roost.render;

import com.google.common.collect.ImmutableList;
import com.setycz.chickens.registry.ChickensRegistry;
import com.setycz.chickens.registry.ChickensRegistryItem;
import com.timwoodcreates.roost.RoostTextures;
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
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.minecraft.client.renderer.texture.TextureMap.LOCATION_MISSING_TEXTURE;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelItemChicken implements IModel {
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelItemChicken(state, format, bakedTextureGetter);
    }

    public static Collection<ResourceLocation> getItemTextures() {
        List<ResourceLocation> textures = DataChicken.getAllChickens().stream()
                .map(DataChicken::getTextureName)
                .filter(Objects::nonNull).map(ModelItemChicken::getTextureLocation)
                .collect(Collectors.toList());

        textures.add(getTextureLocation("vanilla"));

        RoostTextures.stockTextures.stream()
                .map(ModelItemChicken::getTextureLocation)
                .forEach(textures::add);

        return textures;
    }

    private static ResourceLocation getTextureLocation(String tex) {
        return new ResourceLocation("roost", "items/chicken/" + tex);
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
        public IModel chickenModel;
        public IBakedModel missingChickenBakedModel;
        VertexFormat format;
        IModelState state;

        public final ResourceLocation placeholder = new ResourceLocation("roost", "items/chicken/vanilla");

        public BakedModelItemChicken(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
            this.state = state;
            this.format = format;

            chickenModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("roost:item/chicken/vanilla"));
            IBakedModel baked = chickenModel.bake(state, format, bakedTextureGetter);
            models.put("minecraft:vanilla", baked);

            missingChickenBakedModel = chickenModel.bake(state, format, (loc) -> bakedTextureGetter.apply(LOCATION_MISSING_TEXTURE));

        }


        public IBakedModel bakeChicken(String chickenName) {
            DataChicken chickenData = DataChicken.getDataFromName(chickenName);
            ResourceLocation texLoc = getTextureLocation(chickenData.getTextureName());

            Function<ResourceLocation, TextureAtlasSprite> textureGetter;
            textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

            return chickenModel.bake(this.state, this.format, (loc) -> textureGetter.apply(loc.equals(placeholder) ? texLoc: loc));
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
                    IBakedModel model = BakedModelItemChicken.this.models.computeIfAbsent(name, BakedModelItemChicken.this::bakeChicken);
                    if (model != null) return model;
                }
                return missingChickenBakedModel;
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
