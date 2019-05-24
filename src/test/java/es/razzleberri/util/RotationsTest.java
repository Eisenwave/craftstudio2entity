package es.razzleberri.util;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class RotationsTest {
    
    private final static DecimalFormat FORMAT = new DecimalFormat("####.####");
    
    private final static double EPSILON = 1E-10;
    private final static double
        RAD_TO_DEG = Math.toDegrees(1),
        DEG_TO_RAD = Math.toRadians(1);
    
    @Test
    public void isZeroRotation() {
        assertTrue(Rotations.isZeroRotation(new Vec3d(0, 0, 0)));
        assertTrue(Rotations.isZeroRotation(new Vec3d(0, 360, -360)));
        assertTrue(Rotations.isZeroRotation(new Vec3d(720, 1080, -720)));
        assertFalse(Rotations.isZeroRotation(new Vec3d(55, 400, -400)));
    }
    
    /* private static Vec3d nextVector(Random random) {
        return new Vec3d(
            random.nextDouble(),
            random.nextDouble(),
            random.nextDouble()
        );
    } */
    
    private static Vec3d nextRotation(Random random) {
        return new Vec3d(
            random.nextDouble() * Math.PI * 2,
            random.nextDouble() * Math.PI * 2,
            random.nextDouble() * Math.PI * 2
        );
    }
    
    private static Matrix3x3d nextRotationMatrix(Random random) {
        Vec3d rotSource = nextRotation(random);
        return Matrix3x3d.fromEulerXYZ(rotSource);
    }
    
    // X Y Z
    
    /**
     * This test verifies that negating an angle (flipping the direction of a rotation) is equivalent to extracting
     * non-negated angles and negating one of them.
     * <p>
     * This property is verified for just the x-rotation, y-rotation and z-rotation as well as for all rotations
     * at the same time.
     */
    @Test
    public void testExtractXYZ_Negation() {
        Random random = new Random(12345);
        
        for (int i = 0; i < 1_000; i++) {
            Vec3d rotSource = nextRotation(random);
            Matrix3x3d matSource = Matrix3x3d.fromEulerXYZ(rotSource);
            
            Matrix3x3d matNegX = Matrix3x3d.fromEulerXYZ(-rotSource.getX(), rotSource.getY(), rotSource.getZ());
            Matrix3x3d matNegY = Matrix3x3d.fromEulerXYZ(rotSource.getX(), -rotSource.getY(), rotSource.getZ());
            Matrix3x3d matNegZ = Matrix3x3d.fromEulerXYZ(rotSource.getX(), rotSource.getY(), -rotSource.getZ());
            Matrix3x3d matNegA = Matrix3x3d.fromEulerXYZ(-rotSource.getX(), -rotSource.getY(), -rotSource.getZ());
            
            int index = 0;
            for (Matrix3x3d mat : new Matrix3x3d[] {matNegX, matNegY, matNegZ, matNegA}) {
                Vec3d rotTarget = mat.getXYZEulerRotation();
                Matrix3x3d matTarget;
                if (index == 0)
                    matTarget = Matrix3x3d.fromEulerXYZ(-rotTarget.getX(), rotTarget.getY(), rotTarget.getZ());
                else if (index == 1)
                    matTarget = Matrix3x3d.fromEulerXYZ(rotTarget.getX(), -rotTarget.getY(), rotTarget.getZ());
                else if (index == 2)
                    matTarget = Matrix3x3d.fromEulerXYZ(rotTarget.getX(), rotTarget.getY(), -rotTarget.getZ());
                else
                    matTarget = Matrix3x3d.fromEulerXYZ(-rotTarget.getX(), -rotTarget.getY(), -rotTarget.getZ());
                
                assertTrue(matSource.equals(matTarget, EPSILON));
                index++;
            }
        }
    }
    
    // X Y Z
    
    @Test
    public void testExtractXYZ() {
        Random random = new Random(12345);
        //long totalTime = 0;
        
        for (int i = 0; i < 1_000; i++) {
            Matrix3x3d matSource = nextRotationMatrix(random);
            //long before = System.currentTimeMillis();
            Vec3d rotTarget = matSource.getXYZEulerRotation();
            //totalTime += System.currentTimeMillis() - before;
            Matrix3x3d matTarget = Matrix3x3d.fromEulerXYZ(rotTarget);
            
            assertTrue(matSource.equals(matTarget, EPSILON));
        }
        
        //System.out.println((totalTime / 1000d) + " s");
    }
    
    // X Z Y
    
    @Test
    public void testExtractXZY() {
        Random random = new Random(12345);
        
        for (int i = 0; i < 1_000; i++) {
            Matrix3x3d matSource = nextRotationMatrix(random);
            Vec3d rotTarget = matSource.getXZYEulerRotation();
            Matrix3x3d matTarget = Matrix3x3d.fromEulerXZY(rotTarget);
            
            assertTrue(matSource.equals(matTarget, EPSILON));
        }
    }
    
    // Y X Z
    
    @Test
    public void testExtractYXZ() {
        Random random = new Random(12345);
        
        for (int i = 0; i < 1_000; i++) {
            Matrix3x3d matSource = nextRotationMatrix(random);
            Vec3d rotTarget = matSource.getYXZEulerRotation();
            Matrix3x3d matTarget = Matrix3x3d.fromEulerYXZ(rotTarget);
            
            assertTrue(matSource.equals(matTarget, EPSILON));
        }
    }
    
    // Y Z X
    
    @Test
    public void testExtractYZX() {
        Random random = new Random(12345);
        
        for (int i = 0; i < 1_000; i++) {
            Matrix3x3d matSource = nextRotationMatrix(random);
            Vec3d rotTarget = matSource.getYZXEulerRotation();
            Matrix3x3d matTarget = Matrix3x3d.fromEulerYZX(rotTarget);
            
            assertTrue(matSource.equals(matTarget, EPSILON));
        }
    }
    
    // Z Y X
    
    @Test
    public void testExtractZYX() {
        Random random = new Random(12345);
        
        for (int i = 0; i < 1_000; i++) {
            Matrix3x3d matSource = nextRotationMatrix(random);
            Vec3d rotTarget = matSource.getZYXEulerRotation();
            Matrix3x3d matTarget = Matrix3x3d.fromEulerZYX(rotTarget);
            
            assertTrue(matSource.equals(matTarget, EPSILON));
        }
    }
    
    // Z Y X
    
    @Test
    public void testExtractZXY() {
        Random random = new Random(12345);
        
        for (int i = 0; i < 1_000; i++) {
            Matrix3x3d matSource = nextRotationMatrix(random);
            Vec3d rotTarget = matSource.getZXYEulerRotation();
            Matrix3x3d matTarget = Matrix3x3d.fromEulerZXY(rotTarget);
            
            assertTrue(matSource.equals(matTarget, EPSILON));
        }
    }
    
    @Test
    public void eulerYXZtoEulerXYZ() {
        for (int i = 0; i < 1000; i++) {
            //Vec3d v = new Vec3d(Math.random(), Math.random(), Math.random());
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
