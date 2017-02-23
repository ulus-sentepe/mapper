package com.fererlab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mapper {

    private static final Logger log = Logger.getLogger(Mapper.class.getName());
    private static final Map<Class<?>, Function<String, Object>> CONVERTERS = new HashMap<Class<?>, Function<String, Object>>() {{
        put(Integer.class, Integer::new);
        put(Long.class, Long::new);
        put(Short.class, Short::new);
        put(Byte.class, Byte::new);
        put(String.class, s -> s);
        put(Boolean.class, s -> s.equals("T") ? Boolean.TRUE : Boolean.FALSE);
    }};

    private Reader reader = new Reader();
    private int currentLine = 0;

    public Map<Class<? extends DataType>, Map<Object, DataType>> mapWithKey(List<String> lines, List<Class<? extends DataType>> types, Map<Class<?>, Function<String, Object>> converters) {
        if (converters == null) {
            converters = CONVERTERS;
        }
        Map<Class<? extends DataType>, Map<Object, DataType>> map = new HashMap<>();
        for (Class<? extends DataType> typeClass : types) {
            if (!map.containsKey(typeClass)) {
                map.put(typeClass, new LinkedHashMap<>());
            }
            Map<Object, DataType> keyTypeMap = map.get(typeClass);
            Optional<Integer> lineValueOptional = parseLineValue(Integer.class, currentLine, lines);
            currentLine = currentLine + 1;
            if (lineValueOptional.isPresent()) {
                Integer numberOfObjects = lineValueOptional.get();
                int objectLine = currentLine;
                for (int i = 0; i < numberOfObjects; i++) {
                    Optional<DataType> dataTypeOptional = reader.readObject(lines.get(objectLine), (Class<DataType>) typeClass, converters);
                    if (dataTypeOptional.isPresent()) {
                        DataType dataType = dataTypeOptional.get();
                        Optional<Object> key = getKeyValue(dataType);
                        if (key.isPresent()) {
                            keyTypeMap.put(key.get(), dataType);
                        }
                    }
                    objectLine = objectLine + 1;
                }
                currentLine = objectLine;
            }
        }
        return map;
    }

    private Optional<Object> getKeyValue(DataType dataType) {
        Object fieldObject = null;
        for (Field field : dataType.getClass().getDeclaredFields()) {
            Key declaredAnnotation = field.getDeclaredAnnotation(Key.class);
            if (declaredAnnotation != null) {
                try {
                    fieldObject = field.get(dataType);
                    break;
                } catch (IllegalAccessException e) {
                    log.log(Level.SEVERE, "could not get field value, field: " + field + ", exception: " + e, e);
                }
            }
        }
        return Optional.ofNullable(fieldObject);
    }

    private <T> Optional<T> parseLineValue(Class<T> aClass, int lineNumber, List<String> lines) {
        T t = null;
        try {
            String lineValueString = lines.get(lineNumber).trim();
            Constructor<T> constructor = aClass.getDeclaredConstructor(String.class);
            t = constructor.newInstance(lineValueString);
        } catch (Exception e) {
            log.log(Level.SEVERE, "could not get/create String constructor of: " + aClass);
        }
        return Optional.ofNullable(t);
    }

    public Map<Class<? extends DataType>, List<DataType>> mapWithOutKey(List<String> lines, List<Class<? extends DataType>> types, Map<Class<?>, Function<String, Object>> converters) {
        if (converters == null) {
            converters = CONVERTERS;
        }
        Map<Class<? extends DataType>, List<DataType>> map = new HashMap<>();
        for (Class<? extends DataType> typeClass : types) {
            if (!map.containsKey(typeClass)) {
                map.put(typeClass, new LinkedList<>());
            }
            List<DataType> typeList = map.get(typeClass);
            Optional<Integer> lineValueOptional = parseLineValue(Integer.class, currentLine, lines);
            currentLine = currentLine + 1;
            if (lineValueOptional.isPresent()) {
                Integer numberOfObjects = lineValueOptional.get();
                int objectLine = currentLine;
                for (int i = 0; i < numberOfObjects; i++) {
                    Optional<DataType> dataTypeOptional = reader.readObject(lines.get(objectLine), (Class<DataType>) typeClass, converters);
                    if (dataTypeOptional.isPresent()) {
                        DataType dataType = dataTypeOptional.get();
                        typeList.add(dataType);
                    }
                    objectLine = objectLine + 1;
                }
                currentLine = objectLine;
            }
        }
        return map;
    }
}
