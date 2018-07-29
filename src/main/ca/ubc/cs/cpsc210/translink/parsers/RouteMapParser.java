package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser for routes stored in a compact format in a txt file
 */
public class RouteMapParser {
    private String fileName;
    private List<LatLon> elements;

    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse one route pattern, adding it to the route that is named within it
     * @param str  string representing one route pattern (single line of data from source file)
     *
     * Each line begins with a capital N, which is not part of the route number, followed by the
     * bus route number, a dash, the pattern name, a semicolon, and a series of 0 or more real
     * numbers corresponding to the latitude and longitude (in that order) of a point in the pattern,
     * separated by semicolons. The 'N' that marks the beginning of the line is not part of the bus
     * route number.
     */
    private void parseOnePattern(String str) {
        String[] array = str.split(";", 2);
        String[] rnpn = array[0].split("-", 2);
        String routeNumber = rnpn[0].substring(1, rnpn[0].length());
        String patternName = rnpn[1];
        elements = new ArrayList<>();
        if (array[1].contains(";")) {
            parseLatLon(array[1]);
        }
        storeRouteMap(routeNumber, patternName, elements);
    }

    private void parseLatLon(String array) {
        double lat = 0.0;
        for (String element : array.split(";")) {
            if (lat == 0.0) {
                lat = Double.parseDouble(element);
            } else {
                double lon = Double.parseDouble(element);
                LatLon latLon = new LatLon(lat, lon);
                elements.add(latLon);
                lat = 0.0;
            }
        }
    }

    /**
     * Store the parsed pattern into the named route
     * Your parser should call this method to insert each route pattern into the corresponding route object
     * There should be no need to change this method
     *
     * @param routeNumber       the number of the route
     * @param patternName       the name of the pattern
     * @param elements          the coordinate list of the pattern
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}
