package org.example.service;

import java.util.List;

public interface ServiceInterface<T> {

    void add(T dto) throws Exception;

    List<T> findAll();

    void update(T dto) throws Exception;

}
