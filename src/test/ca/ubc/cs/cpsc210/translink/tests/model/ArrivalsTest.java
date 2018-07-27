package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ArrivalsTest {
    private Route route;
    private Arrival arrival;

    @BeforeEach
    void setup() {
        route = RouteManager.getInstance().getRouteWithNumber("43");
        arrival = new Arrival(23, "Home", route);
    }

    @Test
    void testConstructor() {
        assertEquals(23, arrival.getTimeToStopInMins());
        assertEquals("Home", arrival.getDestination());
        assertEquals(route, arrival.getRoute());
    }

    @Test
    void testGetStatusOnSchedule() {
        arrival.setStatus(" ");
        assertEquals(" ", arrival.getStatus());
    }

    @Test
    void testGetStatusScheduledTime() {
        arrival.setStatus("*");
        assertEquals("*", arrival.getStatus());
    }

    @Test
    void testGetStatusEarly() {
        arrival.setStatus("+");
        assertEquals("+", arrival.getStatus());
    }

    @Test
    void testGetStatusLate() {
        arrival.setStatus("-");
        assertEquals("-", arrival.getStatus());
    }
}
