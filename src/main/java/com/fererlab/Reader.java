package com.fererlab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {

    private static final String DELIMITER = " ";
    private static final Logger log = Logger.getLogger(Mapper.class.getName());


    public Optional<DataType> readObject(String content, Class<DataType> typeClass, Map<Class<?>, Function<String, Object>> converters) {
        DataType type = null;
        if (content != null) {
            try {
                content = content.trim();
                String[] values = content.split(DELIMITER);
                type = typeClass.newInstance();
                for (int i = 0; i < values.length; i++) {
                    Field[] fields = typeClass.getDeclaredFields();
                    Field field = fields[i];
                    Optional<Object> fieldValueOptional = convert(values[i], field.getType(), converters);
                    if (fieldValueOptional.isPresent()) {
                        Object fieldValue = fieldValueOptional.get();
                        field.set(type, fieldValue);
                    }
                }
            } catch (Exception e) {
                log.log(Level.INFO, "could not create instance/set field values, exception: " + e, e);
            }
        }
        return Optional.ofNullable(type);
    }

    private Optional<Object> convert(String input, Class<?> type, Map<Class<?>, Function<String, Object>> converters) {
        Object value = null;
        try {
            if (converters.containsKey(type)) {
                Function<String, Object> function = converters.get(type);
                value = function.apply(input);
            } else {
                Constructor<?> constructor = type.getConstructor(String.class);
                value = constructor.newInstance(input);
            }
        } catch (NoSuchMethodException e) {
            log.log(Level.INFO, "no constructor with String parameter found for type: " + type + ", exception: " + e, e);
        } catch (Exception e) {
            log.log(Level.INFO, "could not convert value: " + input + " to type: " + type + ", exception: " + e, e);
        }
        return Optional.ofNullable(value);
    }
}
