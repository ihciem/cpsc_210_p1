package ca.ubc.cs.cpsc210.translink.tests.model;


import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.exception.RouteException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Stops
 */
public class StopTest {
    private Stop stop;

    @BeforeEach
    void setup() {
        LatLon latLon = new LatLon(49.282729, -123.120738);
        stop = new Stop(50289, "UBC", latLon);
    }

    @Test
    void testConstructor() {
        assertEquals(50289, stop.getNumber());
        assertEquals("UBC", stop.getName());
        LatLon latLon = new LatLon(49.282729, -123.120738);
        assertEquals(latLon, stop.getLocn());
        assertTrue(stop.getRoutes().isEmpty());
        assertTrue(stop.getBuses().isEmpty());
    }

    @Test
    void testAddRoute() {
        Route route = new Route("41");
        stop.addRoute(route);
        assertEquals(1, stop.getRoutes().size());
        assertTrue(stop.getRoutes().contains(route));
        assertTrue(route.hasStop(stop));
    }

    @Test
    void testRemoveStop() {
        Route r1 = new Route("41");
        Route r2 = new Route("43");
        stop.addRoute(r1);
        stop.addRoute(r2);
        assertEquals(2, stop.getRoutes().size());
        stop.removeRoute(r1);
        assertEquals(1, stop.getRoutes().size());
        assertTrue(stop.getRoutes().contains(r2));
        assertTrue(r2.hasStop(stop));
        assertFalse(r1.hasStop(stop));
    }

    @Test
    void testOnRoute() {
        Route r1 = new Route("41");
        Route r2 = new Route("43");
        stop.addRoute(r1);
        assertTrue(stop.onRoute(r1));
        assertFalse(stop.onRoute(r2));
    }

    @Test
    void testAddArrival() {
        Route route = new Route("41");
        Arrival a1 = new Arrival(23, "Home", route);
        Arrival a2 = new Arrival(20, "Home", route);
        stop.addArrival(a1);
        stop.addArrival(a2);

    }

    @Test
    void testAddBusOnRouteHasStop() {
        Route route = new Route("41");
        route.addStop(stop);
        Bus bus = new Bus(route, 49.282729, -123.1, "UBC", "13:13");
        try {
            stop.addBus(bus);
        } catch (RouteException e) {
            fail("RouteException should not have been thrown");
        }
    }

    @Test
    void testAddBusOnRouteNoSuchStop() {
        Route route = new Route("41");
        Bus bus = new Bus(route, 49.282729, -123.1, "UBC", "13:13");
        try {
            stop.addBus(bus);
            fail("RouteException should have been thrown");
        } catch (RouteException e) {
            //expected
        }
    }

    @Test
    void testGetBuses() {
        Route route = new Route("41");
        route.addStop(stop);
        Bus bus = new Bus(route, 49.282729, -123.1, "UBC", "13:13");
        try {
            stop.addBus(bus);
        } catch (RouteException e) {
            fail("RouteException should not have been thrown");
        }
        assertEquals(1, stop.getBuses().size());
    }

    @Test
    void testClearBuses() {
        Route route = new Route("41");
        route.addStop(stop);
        Bus bus = new Bus(route, 49.282729, -123.1, "UBC", "13:13");
        try {
            stop.addBus(bus);
        } catch (RouteException e) {
            fail("RouteException should not have been thrown");
        }
        assertEquals(1, stop.getBuses().size());
        stop.clearBuses();
        assertEquals(0, stop.getBuses().size());
    }

    @Test
    void testEquals() {
        LatLon latLon = new LatLon(49.0, -123.12);
        Stop s1 = new Stop(50289, "RBC", latLon);
        Stop s2 = new Stop(50239, "RBC", latLon);
        assertTrue(stop.equals(s1));
        assertFalse(stop.equals(s2));
        Stop s3 = null;
        assertFalse(stop.equals(s3));
        assertFalse(stop.equals(latLon));
    }

    @Test
    void testHashCode() {
        LatLon latLon = new LatLon(49.0, -123.12);
        Stop s1 = new Stop(50289, "RBC", latLon);
        Stop s2 = new Stop(50239, "RBC", latLon);
        assertEquals(s1.hashCode(), stop.hashCode());
        assertFalse(s2.hashCode() == stop.hashCode());
    }

    @Test
    void testSetName() {
        stop.setName("Joyce Station");
        assertEquals("Joyce Station", stop.getName());
    }

    @Test
    void testSetLocn() {
        LatLon latLon = new LatLon(49.0, -123.12);
        stop.setLocn(latLon);
        assertEquals(latLon, stop.getLocn());
    }
}
