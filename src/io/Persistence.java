package io;

import model.Basis;

public interface Persistence {

  void setSaveDirectory(String saveDirectory);

  void save(Basis basis) throws PersistenceException;

  Basis load(String name) throws PersistenceException;

}
