package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test the parser for route pattern map information
 */
class RouteMapParserTest {
    @BeforeEach
    void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    void testRouteParserNormal() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        assertEquals(1232, countNumRoutePatterns());
    }

    @Test
    void testRouteParserOneRoute() {
        RouteMapParser p = new RouteMapParser("routemapsoneroute.txt");
        p.parse();
        assertEquals(1, countNumRoutePatterns());
        assertEquals(4, RouteManager.getInstance().getRouteWithNumber("C43").getPattern("EB2").getPath().size());
    }

    @Test
    void testRouteParseNoLatLons() {
        RouteMapParser p = new RouteMapParser("routemapsnolatlons.txt");
        p.parse();
        assertEquals(3, countNumRoutePatterns());
        assertEquals(0, RouteManager.getInstance().getRouteWithNumber("C43").getPattern("EB2").getPath().size());
    }

    /**
     * Helper to count number of route patterns
     * @return  total number of route patterns found in all routes in route manager
     */
    private int countNumRoutePatterns() {
        int count = 0;
        for (Route r : RouteManager.getInstance()) {
            for (RoutePattern rp : r.getPatterns()) {
                count ++;
            }
        }
        return count;
    }
}
