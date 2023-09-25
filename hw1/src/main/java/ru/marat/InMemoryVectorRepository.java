package ru.marat;

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
    public Collection<Named<Vector3d>> getAll() {
        //noinspection Convert2Diamond
        return vectors.entrySet()
                .stream().map(entry -> new Named<Vector3d>(entry.getKey(), entry.getValue())).toList();
    }

    @Override
    public Vector3d getByName(String name){
        if (!vectors.containsKey(name)) {
            throw new NameNotFoundException();
        }
        return vectors.get(name);
    }
}
