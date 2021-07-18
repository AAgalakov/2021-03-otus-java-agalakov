package ru.otus.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final String selectQuery;
    private final String nameOfTable;
    private final String nameColumnOfId;
    private final List<Field> fieldsWithoutId;
    private final int countOfFieldsWithoutId;

    public EntitySQLMetaDataImpl(final EntityClassMetaData entityClassMetaData) {
        nameOfTable = entityClassMetaData.getName().toLowerCase(Locale.ROOT);
        selectQuery = String.format("SELECT * FROM %s ", nameOfTable);
        nameColumnOfId = entityClassMetaData.getIdField().getName();
        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        countOfFieldsWithoutId = fieldsWithoutId.size();
    }

    @Override
    public String getSelectAllSql() {
        return selectQuery;
    }

    @Override
    public String getSelectByIdSql() {
        return selectQuery.concat(String.format(" WHERE %s = ?", nameColumnOfId));
    }

    @Override
    public String getInsertSql() {
        StringBuilder stringBuilder = new StringBuilder();
        final String format = String.format("INSERT INTO %s ", nameOfTable);
        stringBuilder.append(format);
        for (int i = 0; i < countOfFieldsWithoutId; i++) {
            final String name = fieldsWithoutId.get(i).getName().toLowerCase();
            stringBuilder.append(" (").append(name).append(") ");
            if (i < countOfFieldsWithoutId - 1){
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" VALUES ");
        for (int i = 0; i < countOfFieldsWithoutId; i++) {
            stringBuilder.append(" (?) ");
            if (i < countOfFieldsWithoutId - 1){
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder stringBuilder = new StringBuilder();
        final String format = String.format(
                "UPDATE %s SET ",
                nameOfTable
        );
        stringBuilder.append(format);
        for (int i = 0; i < countOfFieldsWithoutId; i++) {
            final String name = fieldsWithoutId.get(i).getName().toLowerCase();
            stringBuilder.append(" ").append(name).append(" = ? ");
            if (i < countOfFieldsWithoutId - 1){
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(String.format(" WHERE %s = ", nameColumnOfId));
        return stringBuilder.toString();
    }
}
