package es.razzleberri;

import es.razzleberri.util.Matrix3x3d;
import es.razzleberri.util.Vec3d;
import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

public class CraftStudio2BedrockEntityTest {
    
    private final static double EPSILON = 1E-10;
    private final static double
        RAD_TO_DEG = Math.toDegrees(1),
        DEG_TO_RAD = Math.toRadians(1);
    
    @Test
    public void eulerYXZtoEulerXYZ() {
        for (int i = 0; i < 1000; i++) {
            Vec3d v = new Vec3d(Math.random(), Math.random(), Math.random());
            //System.out.println("\nTESTING CONSISTENCY FOR " + v);
            Vec3d anglesYXZ = new Vec3d(
                Math.random() * Math.PI * 2,
                Math.random() * Math.PI * 2,
                Math.random() * Math.PI * 2
            );
            Matrix3x3d transformYXZ = Matrix3x3d.fromEulerYXZ(anglesYXZ.getX(), anglesYXZ.getY(), anglesYXZ.getZ());
            Vec3d anglesXYZ = transformYXZ.getXYZEulerRotation();
            Matrix3x3d transformXYZ = Matrix3x3d.fromEulerXYZ(anglesXYZ);
            
            assertTrue(transformYXZ.equals(transformXYZ, EPSILON));
        }
    }
    
    @Test
    public void eulerZXYtoEulerXYLZ() {
        for (int i = 0; i < 1000; i++) {
            Vec3d v = new Vec3d(Math.random(), Math.random(), Math.random());
            //System.out.println("\nTESTING CONSISTENCY FOR " + v);
            Vec3d anglesZXY = new Vec3d(
                Math.random() * Math.PI * 2,
                Math.random() * Math.PI * 2,
                Math.random() * Math.PI * 2
            );
            Vec3d anglesXYLZ = Matrix3x3d.fromEulerZXY(anglesZXY).getXYLZEulerRotation();
            Vec3d anglesXYZ = new Vec3d(anglesXYLZ.getX(), anglesXYLZ.getY(), -anglesXYLZ.getZ());
            
            Vec3d euleredUsingZXY = Matrix3x3d.fromEulerZXY(anglesZXY).times(v);
            Vec3d euleredUsingXYZ = Matrix3x3d.fromEulerXYZ(anglesXYZ).times(v);
            assertTrue(euleredUsingZXY.equals(euleredUsingXYZ, EPSILON));
        }
        
        DecimalFormat format = new DecimalFormat("####.####");
        
        Vec3d anglesYXZ = new Vec3d(45, 45, 90).times(DEG_TO_RAD);
        Vec3d anglesXYLZ = Matrix3x3d.fromEulerYXZ(anglesYXZ).getXYLZEulerRotation();
        Vec3d anglesXYZ = new Vec3d(anglesXYLZ.getX(), anglesXYLZ.getY(), -anglesXYLZ.getZ());
        System.out.println(anglesYXZ.times(RAD_TO_DEG).toString(format)
            + " -> "
            + anglesXYZ.times(RAD_TO_DEG).toString(format) + "\n"
        );
        
        Vec3d p = new Vec3d(10, 0, 0);
        Matrix3x3d transformA = Matrix3x3d.fromEulerYXZ(anglesYXZ);
        Matrix3x3d transformB = Matrix3x3d.fromEulerXYZ(anglesXYZ);
        assertTrue(transformA.equals(transformB, EPSILON));
        
        System.out.println(transformA.toString(format));
        System.out.println(transformB.toString(format));
        System.out.println();
        System.out.println(transformA.times(p).toString(format));
        System.out.println(transformB.times(p).toString(format));
        
    }
    
    @Test
    public void eulerYXZtoEulerLZYX() {
        for (int i = 0; i < 1000; i++) {
            Vec3d anglesYXZ = new Vec3d(
                Math.random() * Math.PI * 2,
                Math.random() * Math.PI * 2,
                Math.random() * Math.PI * 2
            );
            Vec3d anglesLZYX = Matrix3x3d.fromEulerYXZ(anglesYXZ).getLZYXEulerRotation();
            Vec3d anglesZYX = new Vec3d(anglesLZYX.getX(), anglesLZYX.getY(), -anglesLZYX.getZ());
            
            Matrix3x3d transformYXZ = Matrix3x3d.fromEulerYXZ(anglesYXZ);
            Matrix3x3d transformZYX = Matrix3x3d.fromEulerZYX(anglesZYX);
            assertTrue(transformYXZ.equals(transformZYX, EPSILON));
        }
        
        /*DecimalFormat format = new DecimalFormat("####.####");
        
        Vec3d anglesYXZ = new Vec3d(45, 45, 90).times(DEG_TO_RAD);
        Vec3d anglesXYLZ = Matrix3x3d.fromEulerYXZ(anglesYXZ).getXYLZEulerRotation();
        Vec3d anglesXYZ = new Vec3d(anglesXYLZ.getX(), anglesXYLZ.getY(), -anglesXYLZ.getZ());
        System.out.println(anglesYXZ.times(RAD_TO_DEG).toString(format)
            + " -> "
            + anglesXYZ.times(RAD_TO_DEG).toString(format) + "\n"
        );
        
        
        
        Vec3d p = new Vec3d(10, 0, 0);
        Matrix3x3d transformA = Matrix3x3d.fromEulerYXZ(anglesYXZ);
        Matrix3x3d transformB = Matrix3x3d.fromEulerXYZ(anglesXYZ);
        assertTrue(transformA.equals(transformB, EPSILON));
        
        System.out.println(transformA.toString(format));
        System.out.println(transformB.toString(format));
        System.out.println();
        System.out.println(transformA.times(p).toString(format));
        System.out.println(transformB.times(p).toString(format));*/
        
    }
    
    @Test
    public void eulerYXZtoEulerLZYXManual() {
        DecimalFormat format = new DecimalFormat("####.####");
        
        Vec3d anglesYXZ = new Vec3d(35, 15, 75).times(DEG_TO_RAD);
        Vec3d anglesLZYX = Matrix3x3d.fromEulerYXZ(anglesYXZ).getLZYXEulerRotation();
        Vec3d anglesZYX = new Vec3d(anglesLZYX.getX(), anglesLZYX.getY(), -anglesLZYX.getZ());
        
        System.out.println(anglesYXZ.times(RAD_TO_DEG).toString(format)
            + " -> "
            + anglesZYX.times(RAD_TO_DEG).toString(format) + "\n"
        );
        
        Matrix3x3d transformYXZ = Matrix3x3d.fromEulerYXZ(anglesYXZ);
        Matrix3x3d transformZYX = Matrix3x3d.fromEulerZYX(anglesZYX);
        assertTrue(transformYXZ.equals(transformZYX, EPSILON));
        
        Matrix3x3d transformA = Matrix3x3d.fromEulerYXZ(anglesYXZ);
        Matrix3x3d transformB = Matrix3x3d.fromEulerZYX(anglesZYX);
        System.out.println(transformA.toString(format));
        System.out.println(transformB.toString(format));
        assertTrue(transformA.equals(transformB, EPSILON));
        
        System.out.println(transformA.toString(format));
        System.out.println(transformB.toString(format));
        
    }
    
}
