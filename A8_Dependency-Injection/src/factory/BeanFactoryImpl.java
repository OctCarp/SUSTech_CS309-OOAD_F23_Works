package factory;

import annotations.Inject;
import annotations.Value;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.Properties;

public class BeanFactoryImpl implements BeanFactory {
    private Properties injectProperties = new Properties();
    private Properties valueProperties = new Properties();

    private static Properties loadProp(String path) {
        Properties p = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            p.load(in);
            return p;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadInjectProperties(String path) {
        injectProperties = loadProp(path);
    }

    @Override
    public void loadValueProperties(String path) {
        valueProperties = loadProp(path);
    }

    @Override
    public <T> T createInstance(Class<T> clazz) {
        try {
            Class<?> implClass = getImplementationClass(clazz);
            Constructor<?> constructor = findInjectConstructor(implClass);
            Object[] params = buildConstructorParams(constructor);

            T instance;
            try {
                instance = (T) constructor.newInstance(params);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create instance of " + implClass.getName(), e);
            }

            injectFields(instance);
            injectMethods(instance);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of class: " + clazz.getName(), e);
        }
    }

    private Class<?> getImplementationClass(Class<?> clazz) {
        String implClassName = injectProperties.getProperty(clazz.getName());
        if (implClassName != null) {
            try {
                return Class.forName(implClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Implementation class not found: " + implClassName, e);
            }
        }
        return clazz;
    }

    private Constructor<?> findInjectConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                return constructor;
            }
        }
        return constructors[0];
    }

    private Object[] buildConstructorParams(Constructor<?> constructor) {
        Parameter[] parameters = constructor.getParameters();
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            if (param.isAnnotationPresent(Value.class)) {
                params[i] = getParameterValue(param);
            } else {
                params[i] = createInstance(param.getType());
            }
        }
        return params;
    }

    private Object getParameterValue(Parameter param) {
        Value valueAnnotation = param.getAnnotation(Value.class);
        String key = valueAnnotation.value();
        String valueStr = valueProperties.getProperty(key);
        return convertValue(valueStr, param.getType(), valueAnnotation.min(), valueAnnotation.max());
    }

    private Object convertValue(String value, Class<?> type, int min, int max) {
        if (type == boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == int.class) {
            int intValue = Integer.parseInt(value);
            if (intValue < min || intValue > max) {
                return 0;
//                throw new IllegalArgumentException("Value " + intValue + " out of range: " + "(" + min + "," + max + ")");
            }
            return intValue;
        } else if (type == double.class) {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue < min || doubleValue > max) {
                return 0;
//                throw new IllegalArgumentException("Value " + doubleValue + " out of range: " + "(" + min + "," + max + ")");
            }
            return doubleValue;
        } else if (type == String.class) {
            if (value.length() < min || value.length() > max) {
                return "default_value";
//                throw new IllegalArgumentException("Value size " + value.length() + " out of range: " + "(" + min + "," + max + ")");
            }
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported parameter type: " + type.getName());
        }
    }

    private void injectFields(Object instance) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                injectField(instance, field);
            } else if (field.isAnnotationPresent(Value.class)) {
                injectValueField(instance, field);
            }
        }
    }

    private void injectField(Object instance, Field field) {
        field.setAccessible(true);
        try {
            field.set(instance, createInstance(field.getType()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to inject field: " + field.getName(), e);
        } finally {
            field.setAccessible(false);
        }
    }

    private void injectValueField(Object instance, Field field) {
        Value valueAnnotation = field.getAnnotation(Value.class);
        String key = valueAnnotation.value();
        String valueStr = valueProperties.getProperty(key);
        Object value = convertValue(valueStr, field.getType(), valueAnnotation.min(), valueAnnotation.max());
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to inject value field: " + field.getName(), e);
        } finally {
            field.setAccessible(false);
        }
    }

    private <T> void injectMethods(T instance) {
        for (Method method : instance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inject.class)) {
                method.setAccessible(true);
                try {
                    Parameter[] parameters = method.getParameters();
                    Object[] args = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter param = parameters[i];
                        if (param.isAnnotationPresent(Value.class)) {
                            args[i] = getParameterValue(param);
                        } else {
                            args[i] = createInstance(param.getType());
                        }
                    }
                    method.invoke(instance, args);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to inject method: " + method.getName(), e);
                } finally {
                    method.setAccessible(false);
                }
            }
        }
    }
}
