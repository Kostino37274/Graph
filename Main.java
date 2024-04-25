public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addNode(8);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(6);
        graph.addNode(5);

        graph.createEdge(8,2,5);
        graph.createEdge(3,6,2);
        graph.createEdge(5,3,2);
        graph.createEdge(5,8,7);

        graph.calculatePath(6,8);
        graph.calculatePath(5,3);
        graph.calculatePath(2,6);

    }
}