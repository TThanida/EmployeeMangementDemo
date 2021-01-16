package com.example.employeemanagement.service.impl;


import com.example.employeemanagement.service.DefaultService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class DefaultServiceImpl<T> implements DefaultService<T> {
    protected JpaRepository<T,Long> repository;

    @Override
    public T save(T obj) {
        return repository.save(obj);
    }

    @Override
    public T saveAndFlush(T obj) {
        return repository.saveAndFlush(obj);
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
