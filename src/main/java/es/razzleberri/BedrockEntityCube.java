package es.razzleberri;

import es.razzleberri.util.*;
import org.jetbrains.annotations.NotNull;

public class BedrockEntityCube {
    
    @NotNull
    private final Vec3d origin;
    @NotNull
    private final Vec3i size;
    @NotNull
    private final Vec2i uv;
    
    public BedrockEntityCube(@NotNull Vec3d origin, @NotNull Vec3i size, @NotNull Vec2i uv) {
        this.origin = origin;
        this.size = size;
        this.uv = uv;
    }
    
    @NotNull
    public Vec3d getOrigin() {
        return origin;
    }
    
    @NotNull
    public Vec3i getSize() {
        return size;
    }
    
    @NotNull
    public Vec2i getUv() {
        return uv;
    }
    
}
