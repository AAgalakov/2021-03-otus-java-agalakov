package ru.otus.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;
    private long currentMaxNumberOfId;

    public DataTemplateJdbc(
            DbExecutor dbExecutor,
            EntitySQLMetaData entitySQLMetaData,
            EntityClassMetaData entityClassMetaData
    ) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                resultSet -> {
                    try {
                        if (resultSet.next()) {
                            return createEntity(resultSet);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return null;
                }
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                rs -> {
                    final ArrayList<T> ts = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            ts.add(createEntity(rs));
                        }
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                    return ts;
                }
        )
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        if (currentMaxNumberOfId == 0) {
            currentMaxNumberOfId = getCurrentMaxNumberOfId(connection);
        }
        final List<Object> list = getListObjects(client);
        currentMaxNumberOfId++;

        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                list
        );
    }

    @Override
    public void update(Connection connection, T client) {
        final List<Object> list = getListObjects(client);
        final Field idField = entityClassMetaData.getIdField();
        Object id = null;
        try {
            id = idField.get(client);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        final String updateSql = entitySQLMetaData.getUpdateSql() + id;
        dbExecutor.executeStatement(connection, updateSql, list);
    }

    private T createEntity(ResultSet rs) {
        Object instance = null;
        try {
            instance = entityClassMetaData.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        final Field idField = entityClassMetaData.getIdField();
        long aLong = 0L;
        try {
            aLong = rs.getLong(idField.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            idField.set(instance, aLong);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        final List<Field> allFields = entityClassMetaData.getFieldsWithoutId();
        for (Field field : allFields) {
            final String name = field.getName();
            try {
                field.set(instance, rs.getString(name));
            } catch (IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
        }

        return (T) instance;
    }

    private long getCurrentMaxNumberOfId(Connection connection) {
        final List<T> all = findAll(connection);
        if (all.isEmpty()) {
            return 1L;
        }
        long aLong = 0;
        for (T t : all) {
            final Field idField = entityClassMetaData.getIdField();
            try {
                aLong = (Long) idField.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (aLong > currentMaxNumberOfId) {
                currentMaxNumberOfId = aLong;
            }
        }
        return currentMaxNumberOfId;
    }

    private List<Object> getListObjects(T client) {
        return entityClassMetaData
                .getFieldsWithoutId()
                .stream()
                .map(field -> {
                    try {
                        return field.get(client);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}
