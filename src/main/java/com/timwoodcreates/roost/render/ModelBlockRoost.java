package com.timwoodcreates.roost.render;

import com.google.common.collect.ImmutableList;
import com.timwoodcreates.roost.RoostTextures;
import com.timwoodcreates.roost.block.BlockRoost;
import com.timwoodcreates.roost.data.DataChicken;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelBlockRoost implements IModel {

    public IModel roostModel;
    public IModel chickenModel;
    public ModelBlockRoost() {
        roostModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("roost:block/roost_box"));
        chickenModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("roost:block/roost_chicken"));
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        List<ResourceLocation> textures = new ArrayList<>();
        textures.addAll(roostModel.getTextures());
        textures.addAll(chickenModel.getTextures());

        RoostTextures.stockTextures.stream()
                .map(ModelBlockRoost::getTextureLocation)
                .forEach(textures::add);

        return textures;
    }

    public static Collection<ResourceLocation> getChickenTextures() {
        Set<ResourceLocation> textures = DataChicken.getAllChickens().stream()
                .map(DataChicken::getTextureName)
                .filter(Objects::nonNull)
                .map(ModelBlockRoost::getTextureLocation)
                .collect(Collectors.toSet());

        return textures;
    }

    private static ResourceLocation getTextureLocation(String tex) {
        return new ResourceLocation("roost", "blocks/chicken/" + tex);
    }

    @SubscribeEvent
    public static void preStitch(TextureStitchEvent.Pre event) {
        for (ResourceLocation itemTexture : getChickenTextures()) {
            event.getMap().registerSprite(itemTexture);
        }
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelBlockRoost(state, format, bakedTextureGetter);
    }

    public class BakedModelBlockRoost implements IBakedModel {
        public Map<String, IBakedModel> chickenBakedModels = new HashMap<>();
        public IBakedModel roostBakedModel = null;
        ItemOverrideList overrides = new ItemOverrideList(ImmutableList.of());
        VertexFormat format;
        IModelState state;

        public final ResourceLocation placeholder = new ResourceLocation("roost", "blocks/chicken/vanilla");

        public BakedModelBlockRoost(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
            this.state = state;
            this.format = format;

            roostBakedModel = roostModel.bake(state, format, bakedTextureGetter);

            chickenBakedModels.put("minecraft:vanilla", chickenModel.bake(state, format, bakedTextureGetter));
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
            List<BakedQuad> quads = new ArrayList<>();
            quads.addAll(roostBakedModel.getQuads(state, side, rand));

            IExtendedBlockState extState = (IExtendedBlockState) state;
            String chickenName = extState.getValue(BlockRoost.CHICKEN);
            if (!chickenName.equals("roost:empty")) {
                IBakedModel chicken = chickenBakedModels.computeIfAbsent(chickenName, this::bakeChicken);
                if (chicken != null) {
                    quads.addAll(chicken.getQuads(state, side, rand));
                }
            }
            return quads;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
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
            return roostBakedModel.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides() {
            return overrides;
        }
    }

    public static class ModelBlockRoostLoader implements ICustomModelLoader {
        static public ModelBlockRoostLoader INSTANCE = new ModelBlockRoostLoader();

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            if (modelLocation.getResourceDomain().equals("roost") && modelLocation.getResourcePath().equals("models/block/roost"))
                return true;

            return false;
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception {
            return new ModelBlockRoost();
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {

        }
    }

    public static class StateMapper implements IStateMapper {
        public static StateMapper INSTANCE = new StateMapper();

        @Override
        public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
            Map<IBlockState, ModelResourceLocation> map = new HashMap<>();


            return map;
        }
    }
}
