package es.razzleberri.util;

import java.text.DecimalFormat;

public class Vec3d {
    
    public final static Vec3d ZERO = new Vec3d(0, 0, 0);
    
    private final double x, y, z;
    
    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }
    
    public Vec3d withX(double x) {
        return new Vec3d(x, y, z);
    }
    
    public Vec3d withY(double y) {
        return new Vec3d(x, y, z);
    }
    
    public Vec3d withZ(double z) {
        return new Vec3d(x, y, z);
    }
    
    public Vec3d plus(Vec3d v) {
        return new Vec3d(this.x + v.x, this.y + v.y, this.z + v.z);
    }
    
    public Vec3d minus(Vec3d v) {
        return new Vec3d(this.x - v.x, this.y - v.y, this.z - v.z);
    }
    
    public Vec3d modulo(Vec3d v) {
        return new Vec3d(this.x % v.x, this.y % v.y, this.z % v.z);
    }
    
    public Vec3d times(double s) {
        return new Vec3d(this.x * s, this.y * s, this.z * s);
    }
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
    
    public String toString(DecimalFormat format) {
        return "[" + format.format(x) + ", " + format.format(y) + ", " + format.format(z) + "]";
    }
    
    public boolean equals(Vec3d v, double epsilon) {
        return Math.abs(this.x - v.x) < epsilon
            && Math.abs(this.y - v.y) < epsilon
            && Math.abs(this.z - v.z) < epsilon;
    }
    
}
