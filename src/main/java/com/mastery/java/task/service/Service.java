package com.mastery.java.task.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {

  Optional<T> getById(Long id);

  List<T> getAll();

  void save(T t);

  void update(T t);

  void deleteById(Long id);

}
