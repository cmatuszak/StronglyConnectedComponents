import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.IllegalArgumentException;

/**
 * Class used to run the Strongly Connected Components algorithm given an input file
 */
public class Main {

    /**
     * Main method to run the Strongly Connected Components algorithm given an input file
     * @param args The name of the input file located in the current directory
     * @throws FileNotFoundException If a valid file name is not given. Assumes the file will be in the same directory
     * as the program is running from
     * @throws IllegalArgumentException If the input file is formatted incorrectly
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Must provide at least one argument: input graph file");
        }
        long startTime = System.currentTimeMillis();
        boolean recursive = false;
        boolean timing = false;

        String filePath = new File("").getAbsolutePath();
        FileReader fileReader = new FileReader(filePath + "/" + args[0]);
        BufferedReader reader = new BufferedReader(fileReader);

        if (args.length > 1) {
            timing = Boolean.parseBoolean(args[1]);
            if (args.length > 2) {
                recursive = Boolean.parseBoolean(args[2]);
            }
        }

        String firstLine = reader.readLine();
        validateLine(firstLine, null, null);
        int vertices = new Integer(firstLine.split(" ")[0]);
        int edges = new Integer(firstLine.split(" ")[1]);

        // Construct the graph g
        Graph g = new Graph(vertices);
        for (int i = 0; i < edges; i++) {
            String line = reader.readLine();
            validateLine(line, vertices, edges);
            int u = new Integer(line.split(" ")[0]);
            int v = new Integer(line.split(" ")[1]);
            g.addEdge(u, v);
        }
        reader.close();

        if (timing) {
            System.out.println("Finished constructing graph: " + (System.currentTimeMillis() - startTime));
        }

        // Find the strongly connected components of g
        MyArrayList<MyArrayList<Node>> stronglyConnectedComponents = g.findStronglyConnectedComponents(recursive, timing);

        if (timing) {
            System.out.println("Finished finding strongly connected components.");
            System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
        }

        //Write output to file in desired format
        File outputFile = new File(filePath + "/" + "components.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (int i = 0; i < stronglyConnectedComponents.size(); i++) {
            MyArrayList<Node> component = stronglyConnectedComponents.get(i);
            writer.write("Component\n");
            for (int j = 0; j < component.size(); j++) {
                Node node = component.get(j);
                writer.write(Integer.toString(node.getName()));
                if (j != component.size() - 1 || i != stronglyConnectedComponents.size() - 1) {
                    writer.write("\n");
                }
            }
        }
        writer.close();
    }

    /**
     * Validates the given line
     * @param line the line to be validated
     * @param vertices the number of vertices in the graph
     * @param edges the number of edges in the graph
     * @throws IllegalArgumentException if the line is badly formatted in some way - See the README for
     *        input file requirements
     */
    private static void validateLine(String line, Integer vertices, Integer edges) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException("Invalid input file format - not enough edge lines.");
        }
        try {
            int u = new Integer(line.split(" ")[0]);
            int v = new Integer(line.split(" ")[1]);
            if (u < 0 || v < 0) {
                throw new IllegalArgumentException("Invalid input file format - entries must be nonnegative.");
            }
            if (vertices != null && edges != null && (u > vertices || v > vertices)) {
                    throw new IllegalArgumentException("Invalid input file format - an edge line refers " +
                            "to an invalid vertex.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input file format - all entries must be integers.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid input file format - each line must contain two integers.");
        }
    }
}
