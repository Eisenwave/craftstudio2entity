package es.razzleberri;

import es.razzleberri.util.Vec2i;
import es.razzleberri.util.Vec3d;
import es.razzleberri.util.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CraftStudioBlock {
    
    private final String name;
    private final Vec3d position;
    private final Vec3d offsetFromPivot;
    private final Vec3i size;
    private final Vec3d rotation;
    private final Vec2i texOffset;
    private final List<CraftStudioBlock> children = new ArrayList<>(2);
    
    public CraftStudioBlock(String name, Vec3d position, Vec3d offsetFromPivot, Vec3i size, Vec3d rotation, Vec2i texOffset) {
        this.name = name;
        this.position = position;
        this.offsetFromPivot = offsetFromPivot;
        this.size = size;
        this.rotation = rotation;
        this.texOffset = texOffset;
    }
    
    public void addChild(@NotNull CraftStudioBlock block) {
        this.children.add(block);
    }
    
    @NotNull
    public List<CraftStudioBlock> getChildren() {
        return Collections.unmodifiableList(children);
    }
    
    @NotNull
    public String getName() {
        return name;
    }
    
    @NotNull
    public Vec3d getPosition() {
        return position;
    }
    
    @NotNull
    public Vec3d getOffsetFromPivot() {
        return offsetFromPivot;
    }
    
    @NotNull
    public Vec3i getSize() {
        return size;
    }
    
    @NotNull
    public Vec3d getRotation() {
        return rotation;
    }
    
    @NotNull
    public Vec2i getTexOffset() {
        return texOffset;
    }
    
}
