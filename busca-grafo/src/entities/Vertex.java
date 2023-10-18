package entities;

public class Vertex {

    private long id;

    public Vertex(long id) {
        this.id = id;
    }

    public Vertex() {
        this.id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
