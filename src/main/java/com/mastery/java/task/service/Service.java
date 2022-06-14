package com.mastery.java.task.service;

import java.util.List;

@org.springframework.stereotype.Service
public interface Service<T> {

  T getById(Long id);

  List<T> getAll();

  T save(T t);

  T update(T t);

  T deleteById(Long id);

}
