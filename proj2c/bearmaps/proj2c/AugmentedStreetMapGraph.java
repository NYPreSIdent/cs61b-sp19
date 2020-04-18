package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.MyTrieSet;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Duck Flame
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private MyTrieSet words;
    private HashMap<String, List<Long>> ID;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        words = new MyTrieSet();
        ID = new HashMap<>();
        for (Node n : getNodes()) {
            String name = n.name();
            if (name != null) {
                String cleaName = cleanString(name);
                if (ID.containsKey(cleaName)) {
                    ID.get(cleaName).add(n.id());
                }
                LinkedList<Long> ids = new LinkedList<>();
                ids.add(n.id());
                ID.put(cleaName, ids);
                words.add(cleaName);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        HashMap<Integer, Long> pointToNode = new HashMap<>();
        List<Point> points = new LinkedList<>();
        for (Node n : getNodes()) {
            if (neighbors(n.id()).size() == 0) {
                continue;
            }
            Point point = new Point(n.lon(), n.lat());
            pointToNode.put(point.hashCode(), n.id());
            points.add(point);
        }
        KDTree kd = new KDTree(points);
        Point closetPoint = kd.nearest(lon, lat);
        return pointToNode.get(closetPoint.hashCode());
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> result = new LinkedList<>();
        String name = cleanString(prefix);
        for (String elem : words.keysWithPrefix(name)) {
            String trueName = name(ID.get(elem).get(0));
            result.add(trueName);
        }
        return result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cName = cleanString(locationName);
        List<Map<String, Object>> locationInfo = new LinkedList<>();
        for (Long key : ID.get(cName)) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("lat", lat(key));
            temp.put("lon", lon(key));
            temp.put("name", name(key));
            temp.put("id", key);
            locationInfo.add(temp);
        }
        return locationInfo;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
