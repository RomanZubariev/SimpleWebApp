package com.mastery.java.task.service;

import java.util.List;

public interface Service<T> {

  T getById(Long id);

  List<T> getAll();

  void save(T t);

  void update(T t);

  void deleteById(Long id);

}
