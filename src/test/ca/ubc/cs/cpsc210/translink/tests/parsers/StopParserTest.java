package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
