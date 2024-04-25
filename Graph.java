import java.util.ArrayList;
import java.util.List;

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
                        System.out.println("[Graph] Added edge with weight " + weight + ", and node ids " + id1 + " and " + id2);
                    }
                    else{
                        System.out.println("[Graph] There already is an edge with ids " + id1 + " and " + id2);
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
            System.out.println("[Graph] Removed edge with ids " + id1 + " and " + id2);
        } else if (getEdge(id2,id1) != null) {
            edges.remove(getEdge(id2, id1));
            System.out.println("[Graph] Removed edge with ids " + id2 + " and " + id1);
        } else{
            System.out.println("[Graph] There is no edge with ids " + id1 + " and " + id2);
        }
    }

    public void removeNode(int id){
        Node node = getNode(id);

        if (node != null){
            List<Edge> edgesToRemove = getEdges(id);

            if (edgesToRemove != null){
                int i;
                for (i = 0; i < edgesToRemove.size(); i++){
                    System.out.println("[Graph] Removed edge with weight " + edgesToRemove.get(i).weight + " and ids " + edgesToRemove.get(i).v1.id + " and " + edgesToRemove.get(i).v2.id);
                    edges.remove(edgesToRemove.get(i));
                }
            }

            nodes.remove(node);
            System.out.println("[Graph] Removed node with id " + id);
        }
        else{
            System.out.println("[Graph] There is no node with id " + id);
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
}