package model;

public class Node {

  private String name;
  private String content;

  public Node(String name, String content) {
    this.name = name;
    this.content = content;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    return name.equals(((Node) obj).getName());
  }

}
