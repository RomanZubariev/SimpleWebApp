package com.mastery.java.task.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface Dao<T> {

  T getById(Long id);

  List<T> getAll();

  T save(T t);

  T update(T t);

  T deleteById(Long id);
}