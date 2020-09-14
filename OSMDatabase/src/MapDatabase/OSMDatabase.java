package MapDatabase;

import de.topobyte.osm4j.core.access.OsmHandler;
import de.topobyte.osm4j.core.model.iface.*;
import de.topobyte.osm4j.pbf.seq.PbfParser;
import javafx.util.Pair;
import java.io.*;
import java.util.TreeMap;

/**OSMDatabase is A Layer 2 API that Provides Access to Organized Entity Data in Open Street Map
 * <p>
 * OSMDatabase is built on top of OSM4J. It can load and parse data from an PBF format OSM file.
 * The loaded OSM entities will be sorted in binary trees by their OSM ID.
 * <p>
 * OSMDatabase will parse the following 3 types of OSM entities during <code>loadMap</code>:<br>
 * 1. Node:     A node represents a specific point on the earth's surface defined by its latitude and longitude.<br>
 * 2. Way:      A way is an ordered list of 2-2000 nodes that define a polyline on the map.<br>
 * 3. Relation: A relation is a data structure that documents a relationship between two or more OSM entities.
 * <p>
 * Every OSM entities in each type will have one unique OSM ID. However, different Types of OSM entities
 * may share the same ID. Please use <code>osmNodes.get(OSMID)</code> to find OSM entities by their ID.
 *
 * @see <a href="https://wiki.openstreetmap.org/wiki/Elements#Tag">Elements of Open Street Map</a>
 * @see <a href="https://github.com/topobyte/osm4j">OSM4J library on Github</a>
 */
public class OSMDatabase implements OsmHandler {
    private InputStream stream;
    private File file;

    /**A Binary Tree of OSMNode with OSMID as its Key*/
    public final TreeMap<Long,OsmNode>     osmNodes = new TreeMap<>();
    /**A Binary Tree of OsmWay with OSMID as its Key*/
    public final TreeMap<Long,OsmWay>      osmWays = new TreeMap<>();
    /**A Binary Tree of OsmRelation with OSMID as its Key*/
    public final TreeMap<Long,OsmRelation> osmRelations = new TreeMap<>();


    /**Construct a OSMDatabase Using a PBF Map File*/
    public OSMDatabase(File mapFile){ file = mapFile; }

    /**Construct a OSMDatabase Using the File Path to a PBF Map File*/
    public OSMDatabase(String pathName){ file = new File(pathName); }

    /**Construct a OSMDatabase Using an Input Stream*/
    public OSMDatabase(InputStream inputStream){ stream = inputStream; }


    /**Load and Parse Map File into OSMDatabase
     * @throws IOException when the targeted map file is not found, or failed to read map file/ input stream
     */
    public void loadMap() throws IOException{
        if (file!=null) stream = new FileInputStream(file);
        new PbfParser(this,false).parse(stream);
    }

//--------------------------------------------------------------------------------------------------------------------------------
    /**Get Number of Tags Contained in an OSMEntity*/
    public static int getTagNumber(OsmEntity osmEntity){ return osmEntity.getNumberOfTags(); }

    /**Get A Tag from an OSMEntity Specified by the Tag's Index
     * @return the key followed by the value of a tag as a pair of string
     * @see <a href="https://wiki.openstreetmap.org/wiki/Tags">Tag in Open Street Map</a>
     */
    public static Pair<String,String> getTag(OsmEntity osmEntity, int index){
        return new Pair<>(osmEntity.getTag(index).getKey(), osmEntity.getTag(index).getValue());
    }

    /**Get the Location of an OSMNode in Latitude and Longitude
     * @return Latitude followed by Longitude as a pair of double
     * @see <a href="https://wiki.openstreetmap.org/wiki/Node">Node in Open Street Map</a>
     */
    public static Pair<Double,Double> getLocation(OsmNode osmNode){
        return new Pair<>(osmNode.getLatitude(), osmNode.getLongitude());
    }

    /**Get OSMIDs of All Members of an OSMWay
     * @see <a href="https://wiki.openstreetmap.org/wiki/Way">Way in Open Street Map</a>
     */
    public static long[] getWayMemberID(OsmWay osmWay){
        long result[] = new long[osmWay.getNumberOfNodes()];
        for (int index=0; index<result.length; index++) result[index] = osmWay.getNodeId(index);
        return result;
    }

    /**Return True if the OSMWay is a Closed Way, False Otherwise<p>
     * A closed way is a way where the last node of the way is shared with the first node.
     */
    public boolean isClosedWay(OsmWay osmWay){
        return osmNodes.get(osmWay.getNodeId(0)).getLatitude()
                == osmNodes.get(osmWay.getNodeId(osmWay.getNumberOfNodes()-1)).getLatitude() &&
               osmNodes.get(osmWay.getNodeId(0)).getLongitude()
                == osmNodes.get(osmWay.getNodeId(osmWay.getNumberOfNodes()-1)).getLongitude();
    }

    /**Get OSMIDs of All Members of an OSMRelation
     * @see <a href="https://wiki.openstreetmap.org/wiki/Relation">Relation in Open Street Map</a>
     */
    public static long[] getRelationMemberID(OsmRelation osmRelation){
        long result[] = new long[osmRelation.getNumberOfMembers()];
        for (int index=0; index<result.length; index++) result[index] = osmRelation.getMember(index).getId();
        return result;
    }

    /**Get the Type of OSMEntity for All Members of an OSMRelation
     * @see <a href="https://wiki.openstreetmap.org/wiki/Relation">Relation in Open Street Map</a>
     */
    public static EntityType[] getRelationMemberType(OsmRelation osmRelation){
        EntityType result[] = new EntityType[osmRelation.getNumberOfMembers()];
        for (int index=0; index<result.length; index++) result[index] = osmRelation.getMember(index).getType();
        return result;
    }

    /**Get the Roles of All Members of an OSMRelation in Strings
     * @see <a href="https://wiki.openstreetmap.org/wiki/Relation">Relation in Open Street Map</a>
     */
    public static String[] getRelationMemberRole(OsmRelation osmRelation){
        String result[] = new String[osmRelation.getNumberOfMembers()];
        for (int index=0; index<result.length; index++) result[index] = osmRelation.getMember(index).getRole();
        return result;
    }

//--------------------------------------------------------------------------------------------------------------------------------
    @Override                                                   //Unused
    public void handle(OsmBounds osmBound){}

    @Override                                                   //Load OSM Nodes
    public void handle(OsmNode osmNode){ osmNodes.put(osmNode.getId(),osmNode); }

    @Override                                                   //Load OSM osmWays
    public void handle(OsmWay osmWay){ osmWays.put(osmWay.getId(),osmWay); }

    @Override                                                   //Load OSM osmRelations
    public void handle(OsmRelation osmRelation){ osmRelations.put(osmRelation.getId(),osmRelation); }

    @Override public void complete(){}
}