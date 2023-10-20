package entities;

public class Vertex {

    private int id;

    /**
     * Construtor padrao do vertice
     *
     * @param id id do vertice
     */
    public Vertex(int id) {
        this.id = id;
    }

    public Vertex() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
