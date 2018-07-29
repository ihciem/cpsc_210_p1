package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.model.exception.RouteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Parser for bus data
public class BusParser {
    private static String routeNumber;
    private static String destination;
    private static double lat;
    private static double lon;
    private static String time;

    /**
     * Parse buses from JSON response produced by TransLink query.  All parsed buses are
     * added to the given stop.  Bus location data that is missing any of the required
     * fields (RouteNo, Latitude, Longitude, Destination, RecordedTime) is silently
     * ignored and not added to stop.  Bus that is on route that does not pass through
     * this stop is silently ignored and not added to stop.
     *
     * @param stop            stop to which parsed buses are to be added
     * @param jsonResponse    the JSON response produced by Translink
     * @throws JSONException  when:
     * <ul>
     *     <li>JSON response does not have expected format (JSON syntax problem)</li>
     *     <li>JSON response is not a JSON array</li>
     * </ul>
     */
    public static void parseBuses(Stop stop, String jsonResponse) throws JSONException {
        JSONArray buses = new JSONArray(jsonResponse);
        for (int i = 0; i < buses.length(); i++) {
            JSONObject bus = buses.getJSONObject(i);
            parseBus(bus);
            if (routeNumber != null && lat != 0.0d & lon != 0.0d && destination != null && time != null) {
                Route route = RouteManager.getInstance().getRouteWithNumber(routeNumber);
                Bus b = new Bus(route, lat, lon, destination, time);
                try {
                    stop.addBus(b);
                } catch (RouteException e) {
                    //nothing
                }
                clearFields();
            }
        }
    }

    /**
     * Parse bus from JSON response produced by TransLink query
     *
     * @param bus   JSONObject bus
     */
    private static void parseBus(JSONObject bus) {
        try {
            routeNumber = bus.getString("RouteNo");
            destination = bus.getString("Destination");
            lat = bus.getDouble("Latitude");
            lon = bus.getDouble("Longitude");
            time = bus.getString("RecordedTime");
        } catch (JSONException e) {
            //nothing
        }
    }

    /**
     * clear fields for next bus
     */
    private static void clearFields() {
        routeNumber = null;
        lat = 0.0d;
        lon = 0.0d;
        destination = null;
        time = null;
    }
}
