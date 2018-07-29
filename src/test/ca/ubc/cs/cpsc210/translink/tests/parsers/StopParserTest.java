package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Tests for the StopParser
 */
class StopParserTest {
    @BeforeEach
    void setup() {
        StopManager.getInstance().clearStops();
    }

    @Test
    void testStopParserNormal() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stops.json");
        p.parse();
        assertEquals(8524, StopManager.getInstance().getNumStops());
    }

    @Test
    void testStopSmallerVersion() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stopssmallerversion.json");
        p.parse();
        assertEquals(4, StopManager.getInstance().getNumStops());
        LatLon latLon = new LatLon(49.28646, -123.14043);
        Stop stop = new Stop(50001, "WB DAVIE ST FS BIDWELL ST", latLon);
        assertTrue(stop.equals(StopManager.getInstance().getStopWithNumber(50001)));
        assertTrue(RouteManager.getInstance().getRouteWithNumber("C23").hasStop(stop));
    }

    @Test
    void testStopParserMissingInfo() {
        StopParser p = new StopParser("stopsmissinginfo.json");
        try {
            p.parse();
            fail("StopDataMissingException should have been thrown");
        } catch (StopDataMissingException e) {
            //expected
        } catch (Exception e) {
            fail("Other exceptions should not have been thrown");
        }
        assertEquals(2, StopManager.getInstance().getNumStops());
    }

    @Test
    void testStopParserJSONDataError() throws StopDataMissingException, IOException {
        StopParser p = new StopParser("stopsjsonerror.json");
        try {
            p.parse();
            fail("JSONException should have been thrown");
        } catch (JSONException e) {
            //expected
        } catch (Exception e) {
            fail("Other exceptions should not have been thrown");
        }
        assertEquals(0, StopManager.getInstance().getNumStops());
    }

}
