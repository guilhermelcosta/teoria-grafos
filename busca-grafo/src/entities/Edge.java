package entities;

public class Edge {

    private Vertex origin;
    private Vertex destiny;

    public Edge(Vertex origin, Vertex destiny) {
        this.origin = origin;
        this.destiny = destiny;
    }

    public Edge() {
        this.origin = this.destiny = null;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public Vertex getDestiny() {
        return destiny;
    }

    public void setDestiny(Vertex destiny) {
        this.destiny = destiny;
    }
}
