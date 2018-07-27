package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the RoutePattern class
 */
public class RoutePatternTest {
    private Route route;
    private RoutePattern routePattern;

    @BeforeEach
    void setup() {
        route = RouteManager.getInstance().getRouteWithNumber("43");
        routePattern = new RoutePattern("Express", "UBC"
                , "West", route);
    }

    @Test
    void testConstructor() {
        assertEquals("Express", routePattern.getName());
        assertEquals("UBC", routePattern.getDestination());
        assertEquals("West", routePattern.getDirection());
    }

    @Test
    void testEqualsSameRoutePattern() {
        assertTrue(routePattern.equals(routePattern));
    }

    @Test
    void testEqualsDifferentClasses() {
        assertFalse(routePattern.equals(route));
    }

    @Test
    void testEqualsSameNameOthersDifferent() {
        RoutePattern other = new RoutePattern("Express", "Joyce"
                , "East", route);
        assertTrue(routePattern.equals(other));
    }

    @Test
    void testEqualsDifferentNameOthersSame() {
        RoutePattern other = new RoutePattern("Other", "UBC"
                , "West", route);
        assertFalse(routePattern.equals(other));
    }

    @Test
    void testHashCode() {
        Route r = new Route("49");
        RoutePattern other = new RoutePattern("Express", "Joyce"
                , "East", r);
        assertEquals(routePattern.hashCode(), other.hashCode());
    }

    @Test
    void testSetPath() {
        List<LatLon> path = new ArrayList<>();
        LatLon ll1 = new LatLon(49, 20);
        LatLon ll2 = new LatLon(49, 21);
        LatLon ll3 = new LatLon(48, 50);
        path.add(ll1);
        path.add(ll2);
        path.add(ll3);
        assertEquals(null, routePattern.getPath());
        routePattern.setPath(path);
        assertEquals(path, routePattern.getPath());
    }

    @Test
    void testSetDirection() {
        routePattern.setDirection("East");
        assertEquals("East", routePattern.getDirection());
    }

    @Test
    void testSetDestinaiton() {
        routePattern.setDestination("Joyce");
        assertEquals("Joyce", routePattern.getDestination());
    }
}
