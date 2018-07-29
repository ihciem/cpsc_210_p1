package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {
    private static Stop stop;

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo and an
     * array of Schedules.  If RouteNo or array of Schedules is missing for a particular route,
     * then none of the arrivals for that route are added to the stop.
     *
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop             stop to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by Translink
     * @throws JSONException  when:
     * <ul>
     *     <li>JSON response does not have expected format (JSON syntax problem)</li>
     *     <li>JSON response is not an array</li>
     * </ul>
     * @throws ArrivalsDataMissingException  when no arrivals are added to the stop
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {
        ArrivalsParser.stop = stop;
        JSONArray arrivals = new JSONArray(jsonResponse);
        for (int i = 0; i < arrivals.length(); i++) {
            JSONObject arrival = arrivals.getJSONObject(i);
            parseArrival(arrival);
        }
        int n = 0;
        for (Arrival arrival : stop) {
            n++;
        }
        if (n == 0) {
            throw new ArrivalsDataMissingException();
        }
    }

    private static void parseArrival(JSONObject arrival) {
        try {
            String routeNo = arrival.getString("RouteNo");
            JSONArray schedules = arrival.getJSONArray("Schedules");
            parseSchedule(routeNo, schedules);
        } catch (JSONException e) {
            //do nothing
        }
    }

    private static void parseSchedule(String routeNo, JSONArray schedules) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNo);
        for (int i = 0; i < schedules.length(); i++) {
            try {
                String destination = schedules.getJSONObject(i).getString("Destination");
                int expectedCountdown = schedules.getJSONObject(i).getInt("ExpectedCountdown");
                String status = schedules.getJSONObject(i).getString("ScheduleStatus");
                Arrival arrival = new Arrival(expectedCountdown, destination, r);
                arrival.setStatus(status);
                stop.addArrival(arrival);
            } catch (JSONException e) {
                //do nothing
            }
        }
    }
}
