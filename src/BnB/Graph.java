package BnB;

import java.io.*;
import java.util.*;

public class Graph {
    private final HashMap<Long, Vertex> vertices;
    private int delta; //max edge num from one vertex

    private Graph(long verticesNum) {
        vertices = new HashMap<>((int)verticesNum);
        delta = 0;
    }

    public boolean addVertex(Vertex vertex){
        if (!vertices.containsKey(vertex.getId())){
            vertices.put(vertex.getId(), vertex);
            return true;
        }
        else {
            return false;
        }
    }

    public int getVerticesNum(){
        return vertices.size();
    }

    public int getDelta(){
        return delta;
    }

    public boolean isRemovableVertex(Vertex vertex){
        List<Vertex> vertices = vertex.getFollowVertices();
        //看它所有followVertex还在不在vertices中 如果都在则可，有一个不在就不可
        for (Vertex v : vertices){
            if (!this.vertices.containsKey(v.getId())){
                return false;
            }
        }
        return true;
    }

    public boolean hasRemovableVertex(){
        for (Vertex v : this.vertices.values()){
            if (this.isRemovableVertex(v)){
                return true;
            }
        }
        return false;
    }

    public List<Vertex> getRemovableVertices(){
        List<Vertex> vertices = new ArrayList<>();
        for (Vertex v : this.vertices.values()){
            if (this.isRemovableVertex(v)){
                vertices.add(v);
            }
        }
        return vertices;
    }

    public Graph removeVertex(Vertex vertex){
        if (vertex == null){
            return this;
        }
        Graph graph = this.deepClone();
        graph.vertices.remove(vertex.getId());
        return graph;
    }

    public Vertex getVertex(long id){
        return vertices.getOrDefault(id, null);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(System.getProperty("user.dir"));
        Graph graph = Graph.read("src\\BnB\\delaunay_n10.graph");
    }

//    public static String genKey(Vertex u, Vertex v){
//        Vertex smaller;
//        Vertex larger;
//        if (u.getId() < v.getId()){
//            smaller = u;
//            larger = v;
//        }
//        else {
//            smaller = v;
//            larger = u;
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(smaller.getId());
//        sb.append("_");
//        sb.append(larger.getId());
//        return sb.toString();
//    }

    public static Graph read(String path) throws FileNotFoundException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);

        try (BufferedReader input = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = input.readLine();
            //get vertex number
            long numVertices = new Scanner(line).nextLong();

            //Initialize graph
            Graph graph = new Graph(numVertices);
            for (long vertex = 1; vertex <= numVertices; vertex++) {
                line = input.readLine();

                Vertex newVertex = graph.getVertex(vertex);
                if (newVertex == null) {
                    newVertex = new Vertex(vertex);
                    graph.addVertex(newVertex);
                }

                Scanner s = new Scanner(line);
                int edgeNum = 0;
                while (s.hasNext()) {
                    //add the follow vertices for current vertex
                    long followVertexId = s.nextLong();
                    Vertex followVertex = graph.getVertex(followVertexId);
                    if (followVertex == null) {
                        followVertex = new Vertex(followVertexId);
                        graph.addVertex(followVertex);
                    }

                    newVertex.addFollowVertex(followVertex);
                    edgeNum++;
                }
                graph.delta = Math.max(graph.delta, edgeNum);
            }

            return graph;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
