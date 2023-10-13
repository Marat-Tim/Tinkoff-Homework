package ru.marat.repository;

import ru.marat.Vector3d;
import ru.marat.exception.NameNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryVectorRepository implements VectorRepository {
    private final Map<String, Vector3d> vectors = new HashMap<>();

    @Override
    public void addVector(String name, Vector3d vector3d) {
        vectors.put(name, vector3d);
    }

    @Override
    public Collection<Named<Vector3d>> getPage(int pageSize, int pageNumber) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Named<Vector3d>> getAll() {
        //noinspection Convert2Diamond
        return vectors.entrySet()
                .stream().map(entry -> new Named<Vector3d>(entry.getKey(), entry.getValue())).toList();
    }

    @Override
    public Vector3d getByName(String name) throws NameNotFoundException {
        var value = vectors.get(name);
        if (value == null) {
            throw new NameNotFoundException("Вектор с именем %s не существует".formatted(name));
        }
        return value;
    }

    @Override
    public void deleteByName(String name) throws NameNotFoundException {
        throw new UnsupportedOperationException();
    }
}
