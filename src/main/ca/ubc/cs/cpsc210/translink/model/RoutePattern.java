package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: Task 2: Complete all the methods in this class

/**
 * A description of one pattern of a route
 * Each pattern has a name, destination, direction, list of points (of class LatLon), and Route
 */
public class RoutePattern {
    private String name;
    private String destination;
    private String direction;
    private Route route;
    private List<LatLon> path;

    /**
     * Construct a new RoutePattern with the given information and empty list of LatLon points.
     * @param name          the name of the pattern
     * @param destination   the destination
     * @param direction     the direction
     * @param route         the Route of which this is a pattern
     */
    public RoutePattern(String name, String destination, String direction, Route route) {
        this.name = name;
        this.destination = destination;
        this.direction = direction;
        this.route = route;
    }

    /**
     * Get the pattern name
     * @return      the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the pattern destination
     * @return      the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Get the pattern direction
     * @return      the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Decide if two RoutePatterns are equal. Two route patterns are equal if their names are equal.
     * @param o         the other route pattern to compare to
     * @return          true if this is equal to o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoutePattern routePattern = (RoutePattern) o;
        return name.equals(routePattern.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Set the pattern path: list of coordinates
     * @param path      the path
     */
    public void setPath(List<LatLon> path) {
        this.path = path;
    }

    /**
     * Return the list of coordinates making up this pattern
     *
     * @return      an unmodifiable list of the coordinates on this route pattern
     */
    public List<LatLon> getPath() {
        return path;
    }

    /**
     * Set the direction
     * @param direction     the direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Set the destination
     * @param destination     the destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
}
