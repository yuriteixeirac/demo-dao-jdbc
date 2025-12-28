package model.dao;

import java.util.List;

public interface DataAccessObject<T> {
    void insert(T t);
    void update(T t);
    void deleteById(int id);
    T findById(int id);
    List<T> findAll();
}
