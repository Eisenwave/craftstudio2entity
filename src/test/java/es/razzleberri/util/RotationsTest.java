package es.razzleberri.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class RotationsTest {
    
    @Test
    public void isZeroRotation() {
        assertTrue(Rotations.isZeroRotation(new Vec3d(0, 0, 0)));
        assertTrue(Rotations.isZeroRotation(new Vec3d(0, 360, -360)));
        assertTrue(Rotations.isZeroRotation(new Vec3d(720, 1080, -720)));
        assertFalse(Rotations.isZeroRotation(new Vec3d(55, 400, -400)));
    }
    
}
