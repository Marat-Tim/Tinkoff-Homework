package ru.marat;

import java.util.Collection;

public interface VectorRepository {
    void addVector(String name, Vector3d vector3d);

    Collection<Named<Vector3d>> getAll();

    Vector3d getByName(String name);
}
