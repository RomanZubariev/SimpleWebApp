package com.mastery.java.task.dao;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface Dao<T> {

  T getById(Long id);

  List<T> getAll();

  void save(T t);

  void update(T t);

  void deleteById(Long id);
}