package model;

import java.util.Set;

public class Basis {

  private String name;
  private Set<Node> vertices;
  private Set<Connection> arrows;

  public Basis(String name, Set<Node> vertices, Set<Connection> arrows) {
    this.name = name;
    this.vertices = vertices;
    this.arrows = arrows;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Node> getVertices() {
    return vertices;
  }

  public void setVertices(Set<Node> vertices) {
    this.vertices = vertices;
  }

  public Set<Connection> getArrows() {
    return arrows;
  }

  public void setArrows(Set<Connection> arrows) {
    this.arrows = arrows;
  }

}
