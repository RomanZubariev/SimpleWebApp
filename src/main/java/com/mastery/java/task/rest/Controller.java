package com.mastery.java.task.rest;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface Controller<T> {

  @GetMapping("/{id}")
  T get(@PathVariable Long id);

  @GetMapping("/")
  List<T> getAll();

  @PutMapping("/{id}")
  void update(@RequestBody T t, @PathVariable Long id);

  @PostMapping("/")
  void save(@RequestBody T t);

  @DeleteMapping("/{id}")
  void delete(@PathVariable Long id);

}
