package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.parsers.RouteParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for the RouteParser
 */
class RouteParserTest {
    @BeforeEach
    void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    void testRouteParserNormal() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        assertEquals(229, RouteManager.getInstance().getNumRoutes());
    }

    @Test
    void testRouteParserMissingInfo() {
        RouteParser p = new RouteParser("routesmissinginfo.json");
        try {
            p.parse();
        } catch (Exception e) {
            fail("No exception should have been thrown");
        }
        assertEquals(2, RouteManager.getInstance().getNumRoutes());
        assertEquals(2, RouteManager.getInstance().getRouteWithNumber("002").getPatterns().size());
        assertEquals(4, RouteManager.getInstance().getRouteWithNumber("003").getPatterns().size());
    }

    @Test
    void testRouteParserJSONDataError() throws RouteDataMissingException, IOException {
        RouteParser p = new RouteParser("routesjsonerror.json");
        try {
            p.parse();
            fail("JSONException should have been thrown");
        } catch (JSONException e) {
            //expected
        }
        assertEquals(0, RouteManager.getInstance().getNumRoutes());
    }

    @Test
    void testRouteParserRouteDataMissing() throws IOException {
        RouteParser p = new RouteParser("routesroutedatamissing.json");
        try {
            p.parse();
            fail("RouteDataMissingException should have been thrown");
        } catch (JSONException e) {
            fail("JSONException should not have been thrown");
        } catch (RouteDataMissingException e) {
            //expected
        }
        assertEquals(1, RouteManager.getInstance().getNumRoutes());
        assertEquals("ROBSON/DOWNTOWN                ", RouteManager.getInstance().getRouteWithNumber("005").getName());
    }
}
