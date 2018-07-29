package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;

    public RouteParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse route data from the file and add all route to the route manager.
     *
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }

    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.  A pattern that
     * is missing any one of PatternNo, Destination or Direction is silently ignored
     * and not added to the route.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException   when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)
     *     <li>JSON data is not an array
     * </ul>
     * If a JSONException is thrown, no routes should be added to the route manager
     *
     * @throws RouteDataMissingException when
     * <ul>
     *  <li>JSON data is missing RouteNo, Name, or Patterns element for any route</li>
     *  <li>The value of the Patterns element is not an array for any route</li>
     * </ul>
     *
     * If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */
    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {
        JSONArray routes = new JSONArray(jsonResponse);
        boolean thrown = false;
        for (int i = 0; i < routes.length(); i++) {
            try {
                JSONObject route = routes.getJSONObject(i);
                parseRoute(route);
            } catch (RouteDataMissingException e) {
                thrown = true;
            }
        }
        if (thrown) {
            throw new RouteDataMissingException();
        }
    }

    /**
     * @param route                           JSONObject route
     * @throws RouteDataMissingException      RouteDataMissingException
     */
    private void parseRoute(JSONObject route) throws RouteDataMissingException {
        try {
            String routeName = route.getString("Name");
            String routeNo = route.getString("RouteNo");
            JSONArray patterns = route.getJSONArray("Patterns");
            parsePatterns(routeNo, routeName, patterns);
        } catch (JSONException e) {
            throw new RouteDataMissingException();
        }
    }

    /**
     * @param routeNo                       route number
     * @param routeName                     route name
     * @param patterns                      patterns
     */
    private void parsePatterns(String routeNo, String routeName, JSONArray patterns) {
        for (int i = 0; i < patterns.length(); i++) {
            try {
                String destination = patterns.getJSONObject(i).getString("Destination");
                String direction = patterns.getJSONObject(i).getString("Direction");
                String name = patterns.getJSONObject(i).getString("PatternNo");
                Route r = RouteManager.getInstance().getRouteWithNumber(routeNo, routeName);
                r.getPattern(name, destination, direction);
            } catch (JSONException e) {
                //nothing
            }
        }
    }
}
