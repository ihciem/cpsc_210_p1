package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the Bus class
 */
public class BusTest {
    private Bus testBus;

    @BeforeEach
    void runBefore() {
        Route route = new Route("41");
        testBus = new Bus(route, 50, 49, "Joyce", "11:15");
    }

    @Test
    void testConstructor() {
        assertEquals("41", testBus.getRoute().getNumber());
        assertEquals(50, testBus.getLatLon().getLatitude());
        assertEquals(49, testBus.getLatLon().getLongitude());
        assertEquals("Joyce", testBus.getDestination());
        assertEquals("11:15", testBus.getTime());
    }
}
