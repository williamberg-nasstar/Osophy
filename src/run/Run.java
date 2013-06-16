package run;

import gui.DefaultBasisEditor;
import io.Persistence;
import io.PersistenceException;
import io.impl.FilesystemPersistence;

public class Run {

  public static void main(String... args) throws PersistenceException {
    String dir = args[0];
    String basis = args[1];
    Persistence store = new FilesystemPersistence(dir);

//    Set<Node> vertices = new HashSet<Node>();
//    Set<Connection> arrows = new HashSet<Connection>();
//    
//    vertices.add(new Node("A", "Test data"));
//    vertices.add(new Node("B", "More test data"));
//    
//    arrows.add(new Connection("A", "B"));
//    
//    store.save(new Basis("TestBasis", vertices, arrows));
    
    new DefaultBasisEditor(store.load(basis));
  }

}
