package ru.marat.repository;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import ru.marat.Vector3d;
import ru.marat.exception.NameNotFoundException;
import ru.marat.exception.VectorRepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
@Log4j2
public class PostgresVectorRepository implements VectorRepository {
    private final DataSource dataSource;

    private static final String INSERT_SQL = """
            INSERT INTO vector (name, x, y, z)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (name)
            DO UPDATE
            SET x = EXCLUDED.x, y = EXCLUDED.y, z = EXCLUDED.z;
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT name, x, y, z FROM vector;
            """;

    private static final String SELECT_ONE_SQL = """
            SELECT name, x, y, z FROM vector WHERE name = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM vector WHERE name = ?;
            """;

    @Override
    public void addVector(String name, Vector3d vector3d) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, name);
            statement.setDouble(2, vector3d.x());
            statement.setDouble(3, vector3d.y());
            statement.setDouble(4, vector3d.z());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                log.warn("При вставке элемента в базу данных было изменено {} строчек, а не 1",
                        affectedRows);
            }
        } catch (SQLException e) {
            throw new VectorRepositoryException(e);
        }
    }

    @Override
    public Collection<Named<Vector3d>> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            Collection<Named<Vector3d>> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new Named<>(resultSet.getString("name"),
                        new Vector3d(
                                resultSet.getDouble("x"),
                                resultSet.getDouble("y"),
                                resultSet.getDouble("z")
                        )
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new VectorRepositoryException(e);
        }
    }

    @Override
    public Vector3d getByName(String name) throws NameNotFoundException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ONE_SQL)) {
            statement.setString(1, name);
            @Cleanup ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Vector3d(
                        resultSet.getDouble("x"),
                        resultSet.getDouble("y"),
                        resultSet.getDouble("z"));
            } else {
                throw NameNotFoundException.defaultException(name);
            }
        } catch (SQLException e) {
            throw new VectorRepositoryException(e);
        }
    }

    @Override
    public void deleteByName(String name) throws NameNotFoundException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setString(1, name);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw NameNotFoundException.defaultException(name);
            } else if (affectedRows != 1) {
                log.warn("При удалении элемента из базы данных было изменено {} строчек, а не 1",
                        affectedRows);
            }
        } catch (SQLException e) {
            throw new VectorRepositoryException(e);
        }
    }
}
