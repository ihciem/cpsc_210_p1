package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test the StopManager
 */
class StopManagerTest {

    @BeforeEach
    void setup() {
        StopManager.getInstance().clearStops();
        StopManager.getInstance().clearSelectedStop();
    }

    @Test
    void testBasic() {
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123.2));
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);
    }

    @Test
    void testGetStopWithNumberNumberOnlyNotExisting() {
        Stop s9999 = new Stop(9999, "", new LatLon(49.282729, -123.120738));
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);
    }

    @Test
    void testGetStopWithNumberNumberOnlyExisting() {
        Stop s9999 = new Stop(9999, "", new LatLon(49.282729, -123.120738));
        Stop s1 = StopManager.getInstance().getStopWithNumber(9999, "Home", new LatLon(-49.2, 123.2));
        Stop s2 = StopManager.getInstance().getStopWithNumber(9999);
        assertTrue(s9999.equals(s2));
        assertEquals(s1, s2);
    }

    @Test
    void testGetStopWithNumberNotExisting() {
        Stop s9899 = new Stop(9899, "Home", new LatLon(-49.2, 123.2));
        Stop s = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.2, 123.2));
        assertEquals(s9899, s);
    }

    @Test
    void testGetStopWithNumberExisting() {
        Stop s9899 = new Stop(9899, "Home", new LatLon(-49.0, 123.0));
        Stop s1 = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.1, 123.0));
        Stop s2 = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.2, 123.2));
        assertTrue(s1.equals(s2));
        assertEquals(s9899, s1);
        assertEquals(s9899, s2);
        assertEquals(1, StopManager.getInstance().getNumStops());
    }

    @Test
    void testSetSelectedContainsStop() {
        Stop s = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.0, 123.0));
        try {
            StopManager.getInstance().setSelected(s);
        } catch (StopException e) {
            fail("StopException should not have been thrown");
        }
        assertEquals(s, StopManager.getInstance().getSelected());

    }

    @Test
    void testSetSelectedDoesNotContainStop() {
        Stop s = new Stop(9899, "Home", new LatLon(-49.0, 123.0));
        try {
            StopManager.getInstance().setSelected(s);
            fail("StopException should not have been thrown");
        } catch (StopException e) {
            //expected
        }
        assertEquals(null, StopManager.getInstance().getSelected());
    }

    @Test
    void testClearSelectedStop() {
        Stop s = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.0, 123.0));
        try {
            StopManager.getInstance().setSelected(s);
        } catch (StopException e) {
            fail("StopException should not have been thrown");
        }
        assertEquals(s, StopManager.getInstance().getSelected());
        StopManager.getInstance().clearSelectedStop();
        assertEquals(null, StopManager.getInstance().getSelected());
    }

    @Test
    void testFindNearestToNoneWithinRadius() {
        LatLon pt = new LatLon(-49.2, 123.2);
        Stop s1 = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.0, 123.0));
        Stop s2 = StopManager.getInstance().getStopWithNumber(9999, "Home", new LatLon(-50.200001, 124.0));
        assertEquals(null, StopManager.getInstance().findNearestTo(pt));
    }

    @Test
    void testFindNearestToOneWithinRadius() {
        LatLon pt = new LatLon(-49.2, 123.2);
        Stop s1 = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.2001, 123.2001));
        assertEquals(s1, StopManager.getInstance().findNearestTo(pt));
    }

    @Test
    void testFindNearestToTwoWithinRadius() {
        LatLon pt = new LatLon(-49.2, 123.2);
        Stop s1 = StopManager.getInstance().getStopWithNumber(9899, "Home", new LatLon(-49.2001, 123.2001));
        Stop s2 = StopManager.getInstance().getStopWithNumber(9999, "Home", new LatLon(-49.200001, 123.2));
        assertEquals(s2, StopManager.getInstance().findNearestTo(pt));
    }
}
