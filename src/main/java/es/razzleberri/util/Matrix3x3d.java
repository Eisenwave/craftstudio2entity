package es.razzleberri.util;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Arrays;

import static java.lang.Math.atan2;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Matrix3x3d {
    
    //private final static double HALF_PI = Math.toRadians(90);
    
    /**
     * <p>
     * Returns a 3x3 rotation matrix representing a counter-clockwise rotation around the x-axis.
     * </p>
     * <p>
     * For easier understanding, see: <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix3x3d fromRotX(double angle) {
        double sin = sin(angle), cos = cos(angle);
        return new Matrix3x3d(
            1, 0, 0,
            0, cos, -sin,
            0, sin, cos);
    }
    
    /**
     * <p>
     * Returns a 3x3 rotation matrix representing a counter-clockwise rotation around the y-axis.
     * </p>
     * <p>
     * For easier understanding, see: <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix3x3d fromRotY(double angle) {
        double sin = sin(angle), cos = cos(angle);
        return new Matrix3x3d(
            cos, 0, sin,
            0, 1, 0,
            -sin, 0, cos);
    }
    
    /**
     * <p>
     * Returns a 3x3 rotation matrix representing a counter-clockwise rotation around the z-axis.
     * </p>
     * <p>
     * For easier understanding, see: <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix3x3d fromRotZ(double angle) {
        double sin = sin(angle), cos = cos(angle);
        return new Matrix3x3d(
            cos, -sin, 0,
            sin, cos, 0,
            0, 0, 1);
    }
    
    // X Y Z
    
    public static Matrix3x3d fromEulerXYZ(double x, double y, double z) {
        return fromRotX(x).times(fromRotY(y)).times(fromRotZ(z));
    }
    
    public static Matrix3x3d fromEulerXYZ(Vec3d v) {
        return fromEulerXYZ(v.getX(), v.getY(), v.getZ());
    }
    
    // X Z Y
    
    public static Matrix3x3d fromEulerXZY(double x, double y, double z) {
        return fromRotX(x).times(fromRotZ(z)).times(fromRotY(y));
    }
    
    public static Matrix3x3d fromEulerXZY(Vec3d v) {
        return fromEulerXZY(v.getX(), v.getY(), v.getZ());
    }
    
    // Z X Y
    
    public static Matrix3x3d fromEulerZXY(double x, double y, double z) {
        return fromRotZ(z).times(fromRotX(x)).times(fromRotY(y));
    }
    
    public static Matrix3x3d fromEulerZXY(Vec3d v) {
        return fromEulerZXY(v.getX(), v.getY(), v.getZ());
    }
    
    // Y X Z
    
    public static Matrix3x3d fromEulerYXZ(double x, double y, double z) {
        return fromRotY(y).times(fromRotX(x)).times(fromRotZ(z));
    }
    
    public static Matrix3x3d fromEulerYXZ(Vec3d v) {
        return fromEulerYXZ(v.getX(), v.getY(), v.getZ());
    }
    
    // Y Z X
    
    public static Matrix3x3d fromEulerYZX(double x, double y, double z) {
        return fromRotY(y).times(fromRotZ(z)).times(fromRotX(x));
    }
    
    public static Matrix3x3d fromEulerYZX(Vec3d v) {
        return fromEulerYZX(v.getX(), v.getY(), v.getZ());
    }
    
    // Z Y X
    
    public static Matrix3x3d fromEulerZYX(double x, double y, double z) {
        return fromRotZ(z).times(fromRotY(y)).times(fromRotX(x));
    }
    
    public static Matrix3x3d fromEulerZYX(Vec3d v) {
        return fromEulerZYX(v.getX(), v.getY(), v.getZ());
    }
    
    private final double[] content;
    
    private Matrix3x3d(double... content) {
        this.content = content;
    }
    
    public Matrix3x3d(double m00, double m01, double m02,
                      double m10, double m11, double m12,
                      double m20, double m21, double m22) {
        this.content = new double[] {m00, m01, m02, m10, m11, m12, m20, m21, m22};
    }
    
    public Matrix3x3d() {
        this(new double[9]);
    }
    
    // GETTERS
    
    public double get(int i, int j) {
        return content[i * 3 + j];
    }
    
    /**
     * Special case formula for 3x3 matrices. (Using <a href="https://en.wikipedia.org/wiki/Cramer%27s_rule">Cramer's
     * Rule</a>)
     *
     * @return the determinant of the matrix
     */
    private double getDeterminant() {
        return get(0, 0) * get(1, 1) * get(2, 2)
            + get(0, 1) * get(1, 2) * get(2, 0)
            + get(0, 2) * get(1, 0) * get(2, 1)
            - get(0, 2) * get(1, 1) * get(2, 0)
            - get(0, 0) * get(1, 2) * get(2, 1)
            - get(0, 1) * get(1, 0) * get(2, 2);
    }
    
    // XYZ
    
    private static double hypot(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
    
    // X_l Y_l Z_l
    public Vec3d getLXLYLZEulerRotation() {
        double x = atan2(get(1, 2), get(2, 2));
        double cosY = hypot(get(0, 0), get(0, 1));
        double y = atan2(-get(0, 2), cosY);
        double sinX = sin(x);
        double cosX = cos(x);
        double sinZ = sinX * get(2, 0) - cosX * get(1, 0);
        double cosZ = cosX * get(1, 1) - sinX * get(2, 1);
        double z = atan2(sinZ, cosZ);
        return new Vec3d(x, y, z);
    }
    
    // X_r Y_r Z_r
    @SuppressWarnings("Duplicates")
    public Vec3d getXYZEulerRotation() {
        double x = atan2(-get(1, 2), get(2, 2));
        double cosY = hypot(get(0, 0), get(0, 1));
        double y = atan2(get(0, 2), cosY);
        double sinX = sin(x);
        double cosX = cos(x);
        double sinZ = sinX * get(2, 0) + cosX * get(1, 0);
        double cosZ = sinX * get(2, 1) + cosX * get(1, 1);
        double z = atan2(sinZ, cosZ);
        return new Vec3d(x, y, z);
    }
    
    // X_r Y_r Z_l
    @SuppressWarnings("Duplicates")
    public Vec3d getXYLZEulerRotation() {
        double x = atan2(-get(1, 2), get(2, 2));
        double cosY = hypot(get(0, 0), get(0, 1));
        double y = atan2(get(0, 2), cosY);
        double sinX = sin(x);
        double cosX = cos(x);
        double sinZ = -cosX * get(1, 0) - sinX * get(2, 0);
        double cosZ = cosX * get(1, 1) + sinX * get(2, 1);
        double z = atan2(sinZ, cosZ);
        return new Vec3d(x, y, z);
    }
    
    // XZY
    
    // X_r Z_r Y_r
    public Vec3d getXZYEulerRotation() {
        double x = atan2(get(2, 1), get(1, 1));
        double cosZ = hypot(get(0, 0), get(0, 2));
        double z = atan2(-get(0, 1), cosZ);
        double sinX = sin(x);
        double cosX = cos(x);
        double sinY = sinX * get(1, 0) - cosX * get(2, 0);
        double cosY = cosX * get(2, 2) - sinX * get(1, 2);
        double y = atan2(sinY, cosY);
        return new Vec3d(x, y, z);
    }
    
    // YXZ
    
    // Y_r X_r Z_r
    public Vec3d getYXZEulerRotation() {
        double y = atan2(get(0, 2), get(2, 2));
        double cosX = hypot(get(1, 0), get(1, 1));
        double x = atan2(-get(1, 2), cosX);
        double sinY = sin(y);
        double cosY = cos(y);
        double sinZ = sinY * get(2, 1) - cosY * get(0, 1);
        double cosZ = cosY * get(0, 0) - sinY * get(2, 0);
        double z = atan2(sinZ, cosZ);
        return new Vec3d(x, y, z);
    }
    
    // YZX
    
    public Vec3d getYZXEulerRotation() {
        double y = atan2(-get(2, 0), get(0, 0));
        double cosZ = hypot(get(1, 1), get(1, 2));
        double z = atan2(get(1, 0), cosZ);
        double sinY = sin(y);
        double cosY = cos(y);
        double sinX = sinY * get(0, 1) + cosY * get(2, 1);
        double cosX = sinY * get(0, 2) + cosY * get(2, 2);
        double x = atan2(sinX, cosX);
        return new Vec3d(x, y, z);
    }
    
    // ZYX
    
    // Z_l Y_r X_r
    @SuppressWarnings("Duplicates")
    public Vec3d getLZYXEulerRotation() {
        double z = atan2(-get(1, 0), get(0, 0));
        double cosY = hypot(get(2, 1), get(2, 2));
        double y = atan2(-get(2, 0), cosY);
        double sinZ = sin(z);
        double cosZ = cos(z);
        double sinX = -sinZ * get(0, 2) - cosZ * get(1, 2);
        double cosX = sinZ * get(0, 1) + cosZ * get(1, 1);
        double x = atan2(sinX, cosX);
        return new Vec3d(x, y, z);
    }
    
    // Z_r Y_r X_r
    @SuppressWarnings("Duplicates")
    public Vec3d getZYXEulerRotation() {
        double z = atan2(get(1, 0), get(0, 0));
        double cosY = hypot(get(2, 1), get(2, 2));
        double y = atan2(-get(2, 0), cosY);
        double sinZ = sin(z);
        double cosZ = cos(z);
        double sinX = sinZ * get(0, 2) - cosZ * get(1, 2);
        double cosX = cosZ * get(1, 1) - sinZ * get(0, 1);
        double x = atan2(sinX, cosX);
        return new Vec3d(x, y, z);
    }
    
    // ZXY
    
    // Z_r Y_r X_r
    public Vec3d getZXYEulerRotation() {
        double z = atan2(-get(0, 1), get(1, 1));
        double cosX = hypot(get(2, 0), get(2, 2));
        double x = atan2(get(2, 1), cosX);
        double sinZ = sin(z);
        double cosZ = cos(z);
        double sinY = sinZ * get(1, 2) + cosZ * get(0, 2);
        double cosY = sinZ * get(1, 0) + cosZ * get(0, 0);
        double y = atan2(sinY, cosY);
        return new Vec3d(x, y, z);
    }
    
    /**
     * Multiplies this matrix with another matrix which will be the right hand side of the matrix multiplication.
     *
     * @param m the right hand side matrix
     */
    @NotNull
    public Matrix3x3d times(Matrix3x3d m) {
        double[] result = new double[3 * 3];
        
        /* outer loop for acquiring the position (i, j) in the product matrix */
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                final int index = i * 3 + j;
                
                /* inner loop for calculating the result at (i, j) */
                for (int k = 0, l = 0; k < 3 && l < 3; k++, l++)
                    result[index] += this.get(i, k) * m.get(l, j);
            }
        
        return new Matrix3x3d(result);
    }
    
    public Vec3d times(Vec3d v) {
        return new Vec3d(
            get(0, 0) * v.getX() + get(0, 1) * v.getY() + get(0, 2) * v.getZ(),
            get(1, 0) * v.getX() + get(1, 1) * v.getY() + get(1, 2) * v.getZ(),
            get(2, 0) * v.getX() + get(2, 1) * v.getY() + get(2, 2) * v.getZ()
        );
    }
    
    // SETTERS
    
    public void set(int i, int j, double value) {
        content[i * 3 + j] = value;
    }
    
    public void swap(int i0, int j0, int i1, int j1) {
        final int from = i0 * 3 + j0, to = i1 * 3 + j1;
        
        double swap = content[to];
        content[to] = content[from];
        content[from] = swap;
    }
    
    public void scale(double factor) {
        for (int k = 0; k < content.length; k++)
            content[k] *= factor;
    }
    
    // MISC
    
    @Override
    public String toString() {
        return Arrays.toString(content);
    }
    
    public String toString(DecimalFormat format) {
        StringBuilder builder = new StringBuilder("[")
            .append(format.format(content[0]));
        for (int i = 1; i < content.length; i++)
            builder
                .append(", ")
                .append(format.format(content[i]));
        return builder.append(']').toString();
    }
    
    public boolean equals(Matrix3x3d other, double epsilon) {
        for (int i = 0; i < content.length; i++)
            if (Math.abs(this.content[i] - other.content[i]) > epsilon)
                return false;
        return true;
    }
    
}
