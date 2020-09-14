import MapDatabase.OSMDatabase;
import java.io.IOException;

/* This is A Test Program that Count Number of OSM Nodes, Ways, and Relations
 *
 * The program loads all data from a PFD format OSM file and output the size of
 * each TreeMap that contains OSM Nodes, Ways, and Relations
 */
public class Main {
    public static void main(String[] args) {                    //Construct OSMDatabase with your Map File

        OSMDatabase mapData = new OSMDatabase("<YourMapFile>");

        System.out.println("Start Loading Map...");

        try {
            mapData.loadMap();                                  //Load and Parse Data in the Map File

        }catch (IOException e){

            System.out.println("Error: Failed to Load the Map File");
        }

        System.out.println("Loading Completed, Processing Map Data...");

        System.out.println(mapData.osmNodes.size());            //Print Number of OSM Nodes, Ways, and Relations
        System.out.println(mapData.osmWays.size());
        System.out.println(mapData.osmRelations.size());
    }
}