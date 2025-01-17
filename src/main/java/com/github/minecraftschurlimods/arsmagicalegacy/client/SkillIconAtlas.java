package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.util.Lazy;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public final class SkillIconAtlas extends TextureAtlasHolder {
    public static final ResourceLocation SKILL_ICON_ATLAS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/atlas/skill_icons.png");
    private static final String PREFIX = "icon/skill";
    private static final Lazy<SkillIconAtlas> INSTANCE = Lazy.of(SkillIconAtlas::new);
    private static final String SUFFIX = ".png";
    private static final Predicate<String> RESOURCE_PREDICATE = s -> s.endsWith(SUFFIX);
    private Collection<ResourceLocation> resourceLocations;

    private SkillIconAtlas() {
        super(Minecraft.getInstance().textureManager, SKILL_ICON_ATLAS, PREFIX);
    }

    /**
     * @return The only instance of this class.
     */
    public static SkillIconAtlas instance() {
        return INSTANCE.get();
    }

    @Override
    protected Stream<ResourceLocation> getResourcesToLoad() {
        return resourceLocations.stream();
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        if (location == null) location = MissingTextureAtlasSprite.getLocation();
        return super.getSprite(location);
    }

    @Override
    protected TextureAtlas.Preparations prepare(ResourceManager resourceManager, ProfilerFiller pProfiler) {
        int pathLength = ("textures/" + PREFIX + "/").length();
        int suffixLength = SUFFIX.length();
        this.resourceLocations = resourceManager.listResources("textures/" + PREFIX, RESOURCE_PREDICATE).stream().map(resourceLocation -> {
            String path = resourceLocation.getPath();
            path = path.substring(pathLength, path.length() - suffixLength);
            return new ResourceLocation(resourceLocation.getNamespace(), path);
        }).toList();
        return super.prepare(resourceManager, pProfiler);
    }
}
