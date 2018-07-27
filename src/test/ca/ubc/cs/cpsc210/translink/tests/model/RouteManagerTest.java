package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test the RouteManager
 */
class RouteManagerTest {

    @BeforeEach
    void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    void testBasic() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
    }

    @Test
    void testGetRouteWithNumberOnlyExisting() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(1, RouteManager.getInstance().getNumRoutes());
        assertEquals(r43, r);
        assertEquals(r.getName(), "");
        r.setName("Joyce Station / UBC");
        Route r2 = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(1, RouteManager.getInstance().getNumRoutes());
        assertEquals(r, r2);
        assertFalse(r43.equals(r2));
    }

    @Test
    void testGetRouteWithNumberAndNameNonExisting() {
        Route r43 = new Route("43");
        r43.setName("Joyce Station / UBC");
        Route r = RouteManager.getInstance().getRouteWithNumber("43", "Joyce Station / UBC");
        assertEquals(r43, r);
    }

    @Test
    void testGetRouteWIthNumberAndNameExistingOnlyNum() {
        Route r1 = RouteManager.getInstance().getRouteWithNumber("43");
        Route r2 = RouteManager.getInstance().getRouteWithNumber("43", "Joyce Station / UBC");
        assertEquals(r1, r2);
        assertEquals(r1.getName(), "");
        assertEquals(r2.getName(), "");
    }

    @Test
    void testGetRouteWithNumberAndNameExistingWithNumAndName() {
        Route r43 = new Route( "43");
        r43.setName("Joyce Station / UBC");
        Route r = RouteManager.getInstance().getRouteWithNumber("43", "Joyce Station / UBC");
        assertEquals(1, RouteManager.getInstance().getNumRoutes());
        assertEquals(r43, r);
        Route r2 = RouteManager.getInstance().getRouteWithNumber("43", "Joyce Station / UBC");
        assertEquals(1, RouteManager.getInstance().getNumRoutes());
        assertTrue(r == r2);
    }

    @Test
    void testGetNumRoutesNoRoutes() {
        assertEquals(0, RouteManager.getInstance().getNumRoutes());
    }

    @Test
    void testGetNumRoutesManyRoutes() {
        RouteManager.getInstance().getRouteWithNumber("41");
        RouteManager.getInstance().getRouteWithNumber("43", "Joyce Station / UBC");
        assertEquals(2, RouteManager.getInstance().getNumRoutes());
        RouteManager.getInstance().getRouteWithNumber("480", "Bridgeport Station");
    }

    @Test
    void testClearRoutesNoRoutes() {
        RouteManager.getInstance().clearRoutes();
        assertEquals(0, RouteManager.getInstance().getNumRoutes());
    }

    @Test
    void testClearRoutesExistingRoutes() {
        RouteManager.getInstance().getRouteWithNumber("41");
        RouteManager.getInstance().getRouteWithNumber("43", "Joyce Station / UBC");
        RouteManager.getInstance().getRouteWithNumber("480", "Bridgeport Station");
        assertEquals(3, RouteManager.getInstance().getNumRoutes());
        RouteManager.getInstance().clearRoutes();
        assertEquals(0, RouteManager.getInstance().getNumRoutes());
    }
}
