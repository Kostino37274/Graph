import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Graph {
    private List<Node> nodes = new ArrayList<Node>();
    private List<Edge> edges = new ArrayList<Edge>();

    public int calculatePath(int p1, int p2){
        if (getNode(p1) != null && getNode(p2) != null) {
            for (Node i : nodes) {
                i.path = Integer.MAX_VALUE;
            }

            List<Node> unchecked = new ArrayList<Node>(nodes);
            List<Node> checked = new ArrayList<Node>();
            List<Edge> connections = new ArrayList<Edge>();

            Node current = getNode(p1);
            Node next = null;
            int lightestEdge = Integer.MAX_VALUE;

            while (!unchecked.isEmpty()) {
                if (current.id == p1) {
                    current.path = 0;
                }
                checked.add(current);
                unchecked.remove(current);
                connections = getEdges(current.id);
                for (Edge i : connections) {
                    if (i.v1 == current) {
                        if (!checked.contains(i)) {
                            if (i.weight < lightestEdge) {
                                lightestEdge = i.weight;
                                next = i.v2;
                            }
                        }
                        if (current.path + i.weight < i.v2.path) {
                            i.v2.path = current.path + i.weight;
                        }
                    } else {
                        if (!checked.contains(i)) {
                            if (i.weight < lightestEdge) {
                                lightestEdge = i.weight;
                                next = i.v1;
                            }
                        }
                        if (current.path + i.weight < i.v1.path) {
                            i.v1.path = current.path + i.weight;
                        }
                    }
                }
                if (next == null || next == current) {
                    Node potentialCurrent = null;
                    for (Node i : unchecked) {
                        if (potentialCurrent == null) {
                            potentialCurrent = i;
                        } else if (i.path < potentialCurrent.path) {
                            potentialCurrent = i;
                        }
                    }
                    if (potentialCurrent.path == Integer.MAX_VALUE) {
                        break;
                    } else {
                        current = potentialCurrent;
                    }
                } else {
                    next.previous = current;
                    current = next;
                }
            }

            if (getNode(p2).path == Integer.MAX_VALUE) {
                System.out.println("[Graph] Path not found. Equals: ∞");
            } else {
                System.out.println("[Graph] Path equals: " + getNode(p2).path);
            }
            return getNode(p2).path;
        }else {
            System.out.println("[Graph] Error! Wrong points!");
            return 0;
        }
    }

    public void addNode(int id){
        if (getNode(id) == null){
            nodes.add(new Node(id));
            System.out.println("[Graph] Added node with id " + id);
        }
        else{
            System.out.println("[Graph] There already is a node with id " + id);
        }
    }

    public void createEdge(int id1, int id2, int weight){
        Node v1 = getNode(id1);
        Node v2 = getNode(id2);

        if (v1 != null){
            if (v2 != null){
                if (v1!=v2){
                    if (getEdge(id1, id2) == null && getEdge(id2, id1) == null){
                        edges.add(new Edge(v1, v2, weight));
                        System.out.println("[Graph] Added edge with weight " + weight + ", and node id1: " + id1 + " and id2: " + id2);
                    }
                    else{
                        System.out.println("[Graph] There already is an edge with id1: " + id1 + " and id2: " + id2);
                    }
                }
                else{
                    System.out.println("[Graph] Couldn't create an edge with 2 same nodes");
                }
            }
            else{
                System.out.println("[Graph] Couldn't find node with id " + id2);
            }
        }
        else{
            System.out.println("[Graph] Couldn't find node with id " + id1);
        }
    }

    public void removeEdge(int id1, int id2){
        if (getEdge(id1, id2) != null){
            edges.remove(getEdge(id1, id2));
            System.out.println("[Graph] Removed edge with id1: " + id1 + " and id2:" + id2);
        } else if (getEdge(id2,id1) != null) {
            edges.remove(getEdge(id2, id1));
            System.out.println("[Graph] Removed edge with id1: " + id2 + " and id2: " + id1);
        } else{
            System.out.println("[Graph] There is no edge with id1: " + id1 + " and id2" + id2);
        }
    }

    public void removeNode(int id){
        Node node = getNode(id);

        if (node != null){
            List<Edge> edgesToRemove = getEdges(id);

            if (edgesToRemove != null){
                int i;
                for (i = 0; i < edgesToRemove.size(); i++){
                    System.out.println("[Graph] Removed edge (weight: " + edgesToRemove.get(i).weight + "; id1: " + edgesToRemove.get(i).v1.id + "; id2: " + edgesToRemove.get(i).v2.id);
                    edges.remove(edgesToRemove.get(i));
                }
            }

            nodes.remove(node);
            System.out.println("[Graph] Node with id " + id + " has been removed");
        }
        else{
            System.out.println("[Graph] Node with id " + id + " does not exist");
        }
    }

    private Node getNode(int id){
        if (nodes != null){
            int i;
            for (i = 0; i < nodes.size(); i++){
                if (id == nodes.get(i).id){
                    return nodes.get(i);
                }
            }
        }

        return null;
    }

    private List<Edge> getEdges(int id){
        List<Edge> edgesTemp = new ArrayList<Edge>();

        if (edges != null){
            int i = 0;
            for (i = 0; i < edges.size(); i++){
                if (id == edges.get(i).v1.id || id == edges.get(i).v2.id){
                    edgesTemp.add(edges.get(i));
                }
            }
        }
        else{
            return null;
        }

        return edgesTemp;
    }


    private Edge getEdge(int id1, int id2){
        if (edges != null){
            int i = 0;
            for (i = 0; i < edges.size(); i++){
                if (id1 == edges.get(i).v1.id && id2 == edges.get(i).v2.id){
                    return edges.get(i);
                }
            }
        }

        return null;
    }
    public int minimalChromaticNumber() {
        if (nodes.isEmpty()) return 0;

        HashMap<Node, Integer> colorMap = new HashMap<>();
        colorMap.put(nodes.getFirst(), 0);

        for (int i = 1; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Set<Integer> usedColors = new HashSet<>();
            for (Edge edge : edges) {
                if (edge.v1 == node) {
                    Node neighbor = edge.v2;
                    if (colorMap.containsKey(neighbor)) {
                        usedColors.add(colorMap.get(neighbor));
                    }
                } else if (edge.v2 == node) {
                    Node neighbor = edge.v1;
                    if (colorMap.containsKey(neighbor)) {
                        usedColors.add(colorMap.get(neighbor));
                    }
                }
            }

            int cr;
            for (cr = 0; cr < nodes.size(); cr++) {
                if (!usedColors.contains(cr)) {
                    break;
                }
            }

            colorMap.put(node, cr);
        }

        int maxColor = 0;
        for (int color : colorMap.values()) {
            if (color > maxColor) {
                maxColor = color;
            }
        }

        return maxColor + 1;
    }

    // Algorytm Kurkala
    private Node findNode(int id, ArrayList<Node> list) {
        for (Node node : list) {
            if (node.id == id) {
                return node;
            }
        }
        return null;
    }

    private Node findParent(Node node, HashMap<Long, Node> parent) {
        if (parent.get(node.id) != node) {
            parent.put(Long.valueOf((node.id)), findParent(parent.get(node.id), parent));
        }
        return parent.get(node.id);
    }

    private void union(Node node1, Node node2, HashMap<Long, Node> parent, HashMap<Long, Integer> rank) {
        Node root1 = findParent(node1, parent);
        Node root2 = findParent(node2, parent);

        if (root1 != root2) {
            if (rank.get(root1.id) < rank.get(root2.id)) {
                parent.put(Long.valueOf(root1.id), root2);
            } else if (rank.get(root1.id) > rank.get(root2.id)) {
                parent.put(Long.valueOf(root2.id), root1);
            } else {
                parent.put(Long.valueOf(root2.id), root1);
                rank.put(Long.valueOf(root1.id), rank.get(root1.id) + 1);
            }
        }
    }

    public ArrayList<Edge> kruskalMST() {
        ArrayList<Edge> mst = new ArrayList<>();

        edges.sort(Comparator.comparingInt(edge -> edge.weight));

        HashMap<Long, Node> parent = new HashMap<>();
        HashMap<Long, Integer> rank = new HashMap<>();

        for (Node node : nodes) {
            parent.put(Long.valueOf(node.id), node);
            rank.put(Long.valueOf(node.id), 0);
        }

        for (Edge edge : edges) {
            Node root1 = findParent(edge.v1, parent);
            Node root2 = findParent(edge.v2, parent);

            if (root1 != root2) {
                mst.add(edge);
                union(edge.v1, edge.v2, parent, rank);
            }
        }

        return mst;
    }

    // Algorytm Prima
    public ArrayList<Edge> primMST() {
        ArrayList<Edge> mst = new ArrayList<>();
        if (nodes.isEmpty()) return mst;

        HashMap<Node, Boolean> inMST = new HashMap<>();
        for (Node node : nodes) {
            inMST.put(node, false);
        }

        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        Node startNode = nodes.getFirst();

        addEdgesToQueue(startNode, edgeQueue, inMST);

        while (!edgeQueue.isEmpty()) {
            Edge minEdge = edgeQueue.poll();
            if (!inMST.get(minEdge.v2)) {
                mst.add(minEdge);
                addEdgesToQueue(minEdge.v2, edgeQueue, inMST);
            }
        }

        return mst;
    }

    private void addEdgesToQueue(Node node, PriorityQueue<Edge> edgeQueue, HashMap<Node, Boolean> inMST) {
        inMST.put(node, true);
        for (Edge edge : edges) {
            if (edge.v1 == node && !inMST.get(edge.v2)) {
                edgeQueue.add(edge);
            } else if (edge.v2 == node && !inMST.get(edge.v1)) {
                edgeQueue.add(new Edge(edge.v2, edge.v1, edge.weight));
            }
        }
    }
}
