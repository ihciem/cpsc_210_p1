package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the Route class
 */
public class RouteTest {
    private Route testRoute;

    @BeforeEach
    void runBefore() {
        testRoute = new Route("41");
    }

    @Test
    void testConstructor() {
        assertEquals("41", testRoute.getNumber());
        assertEquals("", testRoute.getName());
        assertEquals(0, testRoute.getPatterns().size());
        assertEquals(0, testRoute.getStops().size());
    }

    @Test
    void testSetNameEmptyName() {
        testRoute.setName("UBC");
        assertEquals("UBC", testRoute.getName());
    }

    @Test
    void testSetNameHasName() {
        testRoute.setName("UBC");
        assertEquals("UBC", testRoute.getName());
        testRoute.setName("Dunbar & 41st");
        assertEquals("Dunbar & 41st", testRoute.getName());
    }

    @Test
    void testAddPatternNotExisting() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "UBC", "Westbound", testRoute);
        testRoute.addPattern(routePattern);
        assertEquals(1, testRoute.getPatterns().size());
    }

    @Test
    void testAddPatternExisting() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "UBC", "Westbound", testRoute);
        testRoute.addPattern(routePattern);
        assertEquals(1, testRoute.getPatterns().size());
        testRoute.addPattern(routePattern);
        assertEquals(1, testRoute.getPatterns().size());
    }

    @Test
    void testAddStop() {
        LatLon latlon = new LatLon(50, 49);
        Stop stop = new Stop(50289, "41st & West Blvd", latlon);
        testRoute.addStop(stop);
        assertEquals(1, testRoute.getStops().size());
    }

    @Test
    void testRemoveStop() {
        LatLon latlon1 = new LatLon(50, 49);
        Stop stop1 = new Stop(50270, "Maple Street", latlon1);
        LatLon latlon2 = new LatLon(51, 50);
        Stop stop2 = new Stop(50289, "41st & West Blvd", latlon2);
        testRoute.addStop(stop1);
        testRoute.addStop(stop2);
        assertEquals(2, testRoute.getStops().size());
        testRoute.removeStop(stop1);
        assertEquals(1, testRoute.getStops().size());
    }

    @Test
    void testGetStopsHasStops() {
        LatLon latlon1 = new LatLon(50, 49);
        Stop stop1 = new Stop(50270, "Maple Street", latlon1);
        LatLon latlon2 = new LatLon(51, 50);
        Stop stop2 = new Stop(50289, "41st & West Blvd", latlon2);
        testRoute.addStop(stop1);
        testRoute.addStop(stop2);
        List<Stop> stops = testRoute.getStops();
        assertEquals(2, stops.size());
        assertTrue(stop1.equals(stops.get(0)));
        assertTrue(stop2.equals(stops.get(1)));
    }

    @Test
    void testHasStopHasNoStops() {
        LatLon latlon1 = new LatLon(50, 49);
        Stop stop1 = new Stop(50270, "Maple Street", latlon1);
        assertFalse(testRoute.hasStop(stop1));
    }

    @Test
    void testHasStopDoesNotHaveStop() {
        LatLon latlon1 = new LatLon(50, 49);
        Stop stop1 = new Stop(50270, "Maple Street", latlon1);
        LatLon latlon2 = new LatLon(51, 50);
        Stop stop2 = new Stop(50289, "41st & West Blvd", latlon2);
        testRoute.addStop(stop1);
        assertFalse(testRoute.hasStop(stop2));
    }

    @Test
    void testHasStopHasStop() {
        LatLon latlon1 = new LatLon(50, 49);
        Stop stop1 = new Stop(50270, "Maple Street", latlon1);
        testRoute.addStop(stop1);
        assertTrue(testRoute.hasStop(stop1));
    }

    @Test
    void testEquals() {
        Route anotherRoute = new Route("41");
        assertTrue(testRoute.equals(anotherRoute));
    }

    @Test
    void testEqualsNotEquals() {
        Route anotherRoute = null;
        assertFalse(testRoute.equals(anotherRoute));
        RoutePattern routePattern = new RoutePattern("ABC", "UBC", "West", testRoute);
        assertFalse(testRoute.equals(routePattern));
    }

    @Test
    void testToString() {
        String rnum = "Route " + 41;
        assertTrue(testRoute.toString().equals(rnum));
    }

    @Test
    void testGetPatternThreeParamsExisting() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "UBC", "Westbound", testRoute);
        testRoute.addPattern(routePattern);
        assertTrue(testRoute.getPattern("Express", "UBC", "Westbound").equals(routePattern));
    }

    @Test
    void testGetPatternThreeParamsNotExisting() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "UBC", "Westbound", testRoute);
        assertTrue(testRoute.getPattern("Express", "UBC", "Westbound").equals(routePattern));
    }

    @Test
    void testGetPatternThreeParamsUpdates() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "UBC", "Westbound", testRoute);
        assertTrue(testRoute.getPattern("Express", "Joyce", "Eastbound").equals(routePattern));
        RoutePattern newRoutePattern;
        newRoutePattern = new RoutePattern("Express", "Joyce", "Eastbound", testRoute);
        assertTrue(testRoute.getPattern("Express", "Joyce", "Eastbound").equals(newRoutePattern));
    }

    @Test
    void testGetPatternNotExisting() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "", "", testRoute);
        assertTrue(testRoute.getPattern("Express").equals(routePattern));
        assertEquals(1, testRoute.getPatterns().size());
    }

    @Test
    void testGetPatternExisting() {
        RoutePattern routePattern;
        routePattern = new RoutePattern("Express", "UBC", "Westbound", testRoute);
        testRoute.addPattern(routePattern);
        assertTrue(testRoute.getPattern("Express").equals(routePattern));
        assertEquals(1, testRoute.getPatterns().size());
    }

    @Test
    void testIterator() {
        assertFalse(testRoute.iterator().hasNext());
    }
}
