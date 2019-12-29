package com.elite.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.elite.audio.AudioManagement;
import com.elite.entities.AmmoCrate;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertEquals;

public class TestAmmoCrate {
    private static World world = new World(new Vector2(0, -200f), true);
//    private static AmmoCrate ammoCrate = new AmmoCrate(world,0,0,0);

    @Test
    public void ConstructorTest() {
        try {
            //World mockWorld = Mockito.mock(World.class);
            AmmoCrate mockAmmoCrate = Mockito.mock(AmmoCrate.class);
            PowerMockito.whenNew(AmmoCrate.class).withNoArguments().thenReturn(mockAmmoCrate);

            AmmoCrate testAmmoCrate = new AmmoCrate(world, 0, 0, 0);

            assertEquals(testAmmoCrate, mockAmmoCrate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void getTypeTest() {
//        assertEquals("AMMO", ammoCrate.getType());
//    }
//
//    @Test
//    public void getUserDataTest() {
//        assertEquals(0, ammoCrate.getUserData());
//    }
//
//    @Test
//    public void updateCrateTest() {
//        try {
//            ammoCrate.updateCrate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
