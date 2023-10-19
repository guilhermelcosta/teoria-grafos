package entities;

public class Edge {

    private Vertex origin;
    private Vertex destiny;
    private String classification;

    public Edge(Vertex origin, Vertex destiny, String classification) {
        this.origin = origin;
        this.destiny = destiny;
        this.classification = classification;
    }

    public Edge(Vertex origin, Vertex destiny) {
        this.origin = origin;
        this.destiny = destiny;
        this.classification = null;
    }

    public Edge() {
        this.origin = this.destiny = null;
        this.classification = null;
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
