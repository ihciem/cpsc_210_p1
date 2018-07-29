package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {
    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the file and add all stops to stop manager.
     *
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)</li>
     *     <li>JSON data is not an array</li>
     * </ul>
     * @throws StopDataMissingException when
     * <ul>
     *     <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     * </ul>
     * If a StopDataMissingException is thrown, all stops for which all required data is available
     * are first added to the stop manager.
     */
    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {
        JSONArray stops = new JSONArray(jsonResponse);
        boolean thrown = false;
        for (int i = 0; i < stops.length(); i++) {
            try {
                JSONObject stop = stops.getJSONObject(i);
                parseStop(stop);
            } catch (StopDataMissingException e) {
                thrown = true;
            }
        }
        if (thrown) {
            throw new StopDataMissingException();
        }
    }

    /**
     * @param stop                           JSONObject stop
     * @throws StopDataMissingException      StopDataMissingException
     */
    private void parseStop(JSONObject stop) throws StopDataMissingException {
        try {
            String name = stop.getString("Name");
            int number = stop.getInt("StopNo");
            double lat = stop.getDouble("Latitude");
            double lon = stop.getDouble("Longitude");
            LatLon latLon = new LatLon(lat, lon);
            String routeNumbers = stop.getString("Routes");
            Stop s = StopManager.getInstance().getStopWithNumber(number, name, latLon);
            addStopToRoute(s, routeNumbers);
        } catch (JSONException e) {
            throw new StopDataMissingException();
        }
    }

    /**
     * @param stop               stop
     * @param routeNumbers       route names separated by commas and spaces
     */
    private void addStopToRoute(Stop stop, String routeNumbers) {
        String[] routes = routeNumbers.split(",");
        for (String route : routes) {
            route = route.trim();
            RouteManager.getInstance().getRouteWithNumber(route).addStop(stop);
        }
    }

}
