package ru.marat.repository;

import ru.marat.Vector3d;
import ru.marat.exception.NameNotFoundException;

import java.util.Collection;

public interface VectorRepository {
    void addVector(String name, Vector3d vector3d);

    Collection<Named<Vector3d>> getPage(int pageSize, int pageNumber);

    Collection<Named<Vector3d>> getAll();

    Vector3d getByName(String name) throws NameNotFoundException;

    void deleteByName(String name) throws NameNotFoundException;
}
