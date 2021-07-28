package ru.otus.mapper;

import ru.otus.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl implements EntityClassMetaData {

    private static final Class<Id> ID_ANNOTATION_NAME = Id.class;
    private final Class<?> clazz;
    private final Field fieldId;
    private final List<Field> fieldList;
    private final List<Field> fieldsWithoutId;
    private final Constructor constructor;

    public EntityClassMetaDataImpl(final Class<?> clazz) throws NoSuchMethodException {
        this.clazz = clazz;
        this.fieldList = Arrays.stream(clazz.getDeclaredFields())
                               .map(field -> {
                                   field.setAccessible(true);
                                   return field;
                               })
                               .collect(Collectors.toList());
        fieldId = fieldList
                .stream()
                .filter(field -> field.isAnnotationPresent(ID_ANNOTATION_NAME))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет поля, помеченного аннотацией Id" +
                                      " в классе - %s", clazz.getSimpleName())));
        fieldsWithoutId = fieldList
                .stream()
                .filter(field -> !field.equals(fieldId))
                .collect(Collectors.toList());
        constructor = clazz.getConstructor();;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public <T> Constructor getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return fieldId;
    }

    @Override
    public List<Field> getAllFields() {
        return fieldList;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
