package algo;

import base.Vertex;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SAVC extends LSVC implements SAInstance{

    private float A;
    private float B;
    private HashMap<Long, Integer> neighbor = null;
    private int neighborSize = -1;
    long flippedVertexId = -1;
    float flippingFactor = 1.0f;
    boolean useProCost;
    private List<Long> vertexIds;

    public SAVC(){
        super();
        A = 1.0f;
        B = 1.0f;
        useProCost = false;
    }

    public SAVC(String filePath) throws FileNotFoundException {
        super(filePath);
        A = 1.0f;
        B = 1.0f;
        useProCost = false;
        vertexIds = graph.getVertexIds();
    }

    public SAVC(String filePath, float A, float B, boolean useProCost) throws FileNotFoundException {
        super(filePath);
        this.A = A;
        this.B = B;
        this.useProCost = useProCost;
        vertexIds = graph.getVertexIds();
    }

    @Override
    public float getCost() {
        if(useProCost)
            return costPro(false);
        return cost(false);
    }

    @Override
    public void init(Random rd) {
        initVCFast(rd);
    }

    @Override
    public void genNeighbor(Random rd) {
        if(neighbor == null){
            neighbor = new HashMap<>(vc);
        }
        else {
            neighbor.putAll(vc);
        }

        flippedVertexId = vertexIds.get(rd.nextInt(vertexIds.size()));
        neighborSize = getSize();
        flippingFactor = graph.getVertex(flippedVertexId).getFollowVertices().size() * 1.0f / graph.getNumEdges();
        if(vc.get(flippedVertexId) == 1){
            boolean isStillVC = true;
            List<Vertex> followFlippedVertices = graph.getVertex(flippedVertexId).getFollowVertices();
            for(Vertex followVertex : followFlippedVertices){
                if(vc.get(followVertex.getId()) == 0){
                    isStillVC = false;
                    break;
                }
            }
            if(isStillVC){
                neighbor.put(flippedVertexId, 0);
                neighborSize = getSize() - 1;
            }
            flippingFactor = 1.f + flippingFactor;
        } else {
            neighbor.put(flippedVertexId, 1);
            neighborSize = getSize() + 1;
            flippingFactor = 1.f - flippingFactor;
        }

    }

    @Override
    public float getNeighborCost() {
        if(useProCost)
            return costPro(true);
        return cost(true);
    }

    @Override
    public float getProbFactor() {

        return flippingFactor;
    }

    @Override
    public void update() {
        vc.putAll(neighbor);
        size = neighborSize;
    }

    public float cost(boolean isNeighbor) {
        // The simple objective F = \sum_i vi = coverSize
        if(isNeighbor)
            return neighborSize;
        return getSize();
    }

    // TODO: Implement objectives
    public float costPro(boolean isNeighbor) {
        // The complete objective F = A \sum_i vi  + B \sum_i \sum_j dij + B \sum_i \sum_j dij(vi*vj-vi-vj)
        //                          = A coverSize  + B edgeSize          + B
        int coverSize = getSize();
        if(isNeighbor)
            coverSize =  neighborSize;

        int edgeSize = graph.getNumEdges();

        return A*coverSize + B * edgeSize;
    }

    public void run(float cutoff, int seed) {
        this.run(cutoff, seed, false);
    }

    public void run(float cutoff, int seed, boolean verbose) {
        SA algo = new SA();
        algo.run(this, cutoff, seed, verbose);
    }

    public void printResult(){
//        System.out.println("SA algorithms for Minimum Vertex Cover not implemented yet!");
        System.out.println("SA algorithms Results: " + getSize());
    }


}
