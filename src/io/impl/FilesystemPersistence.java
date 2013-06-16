package io.impl;

import io.Persistence;
import io.PersistenceException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import model.Basis;
import model.Connection;
import model.Node;

public class FilesystemPersistence implements Persistence {

  private File saveDirectory;
  private static final String BASIS_FILE_EXTENSION = "obf";

  @SuppressWarnings("unused")
  private FilesystemPersistence() {}

  public FilesystemPersistence(String saveDirectory) {
    setSaveDirectory(saveDirectory);
  }

  @Override
  public void setSaveDirectory(String saveDirectory) {
    File dir = new File(saveDirectory);
    if (!(dir.exists() && dir.isDirectory())) {
      throw new IllegalArgumentException("File path didn't exist or wasn't a directory");
    }

    this.saveDirectory = dir;
  }

  /**
   * Save directory must be set first.
   */
  @Override
  public void save(Basis basis) throws PersistenceException {
    if (saveDirectory == null) {
      throw new IllegalStateException("Save directory was not set");
    }

    File saveFile = getFile(basis.getName());
    PrintWriter fileWriter = null;

    saveFile.delete();
    try {
      saveFile.createNewFile();
      fileWriter = new PrintWriter(saveFile);
    }
    catch (IOException e) {
      System.err.println("File couldn't be created");
      e.printStackTrace();
    }

    for (Node n : basis.getVertices()) {
      fileWriter.println(n.getName());
      fileWriter.println(n.getContent());
    }
    fileWriter.println();
    for (Connection c : basis.getArrows()) {
      fileWriter.println(c.getFromId() + " -> " + c.getToId());
    }

    fileWriter.close();
  }

  @Override
  public Basis load(String name) throws PersistenceException {
    File loadFile = getFile(name);
    BufferedReader fileReader = null;

    try {
      fileReader = new BufferedReader(new FileReader(loadFile));
    }
    catch (FileNotFoundException e) {
      System.err.println("File couldn't be loaded");
      e.printStackTrace();
    }

    Set<Node> vertices = new HashSet<Node>();
    Set<Connection> arrows = new HashSet<Connection>();

    String line = null;
    try {
      while (!"".equals((line = fileReader.readLine()))) {
        Node newNode = new Node(line, fileReader.readLine());
        vertices.add(newNode);
      }

      while ((line = fileReader.readLine()) != null && line.contains(" -> ")) {
        String[] parts = line.split(" -> ");
        Connection newConnection = new Connection(parts[0].trim(), parts[1].trim());
        arrows.add(newConnection);
      }
    }
    catch (IOException e) {
      System.err.println("IO error");
      e.printStackTrace();
    }
    catch (ArrayIndexOutOfBoundsException e) {
      System.err.println("File was misformatted");
      e.printStackTrace();
    }

    try {
      fileReader.close();
    }
    catch (IOException e) {
      System.err.println("OS is being a bitch");
      e.printStackTrace();
    }

    return new Basis(name, vertices, arrows);
  }

  private File getFile(String name) {
    return new File(saveDirectory, name + "." + BASIS_FILE_EXTENSION);
  }

}
