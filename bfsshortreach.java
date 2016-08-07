import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Solution {

    public static final int DISTANCE_PER_HOP = 6;

    static class Node {

        final private List<Node> neighbors = new ArrayList<Node>();
        private Integer distance = -1;
        private Boolean visited = false;

        public void addNeighbor(Node node) {
            neighbors.add(node);
        }

        public List<Node> getNeighbors() {
            return neighbors;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Boolean getVisited() {
            return visited;
        }

        public void setVisited(Boolean visited) {
            this.visited = visited;
        }
    }

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);

        final int caseCount = in.nextInt();
        for (Integer caseNum : IntStream.range(0, caseCount).toArray()) {
            final int n = in.nextInt();
            final int m = in.nextInt();

            final List<Node> nodes = IntStream.range(0, n).boxed()
                .map(i -> new Node())
                .collect(Collectors.toList());

            IntStream.range(0, m).boxed()
                .forEach(i -> {
                        final int leftIndex = in.nextInt() - 1;
                        final int rightIndex = in.nextInt() - 1;
                        nodes.get(leftIndex).addNeighbor(nodes.get(rightIndex));
                        nodes.get(rightIndex).addNeighbor(nodes.get(leftIndex));
                    });

            final Node startNode = nodes.get(in.nextInt() - 1);
            solve(nodes, startNode);

            // print results
            System.out.println(nodes.stream()
                               .filter(i -> i != startNode)
                               .map(i -> i.getDistance().toString())
                               .collect(Collectors.joining(" "))
                               );
        }

    }

    public static void solve(List<Node> nodes, Node startNode) {
        startNode.setDistance(0);
        final List<Node> currentLayer = new ArrayList<Node>();

        startNode.getNeighbors().stream()
            .forEach(n -> currentLayer.add(n));

        int hops = 1;
        while (!currentLayer.isEmpty()) {
            final int h = hops;
            final List<Node> nextLayer = new ArrayList<Node>();
            currentLayer.stream()
                .forEach(node -> {
                        if (!node.getVisited()) {
                            node.setDistance(h * DISTANCE_PER_HOP);
                            node.setVisited(true);
                            node.getNeighbors().stream()
                                .forEach(n -> nextLayer.add(n));
                        }
                    });

            currentLayer.clear();
            currentLayer.addAll(nextLayer);
            hops++;
        }
    }
}
