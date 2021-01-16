package com.example.employeemanagement.service;

import java.util.List;

public interface DefaultService<T> {
    T save(T obj);
    T saveAndFlush(T obj);
    T findById(Long id);
    List<T> findAll();
    void deleteById(Long id);
}
