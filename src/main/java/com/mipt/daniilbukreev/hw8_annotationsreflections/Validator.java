package com.mipt.daniilbukreev.hw8_annotationsreflections;

import java.lang.reflect.Field;

public class Validator {
    public static final String emailTemplate = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

    public static ValidationResult validate(Object object) {
        ValidationResult result = new ValidationResult();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (validateNotNull(field, object) != null) {
                result.addError(validateNotNull(field, object) + ", ");
            }
            if (validateSize(field, object) != null) {
                result.addError(validateSize(field, object) + ", ");
            }
            if (validateRange(field, object) != null) {
                result.addError(validateRange(field, object) + ", ");
            }
            if (validateEmail(field, object) != null) {
                result.addError(validateEmail(field, object) + ", ");
            }

        }
        return result;
    }

    private static String validateNotNull(Field field, Object object) {
        if (!field.isAnnotationPresent(NotNull.class)) {
            return null;
        }

        NotNull annotation = field.getAnnotation(NotNull.class);
        field.setAccessible(true);
        try {
            Object value = field.get(object);
            if (value != null) {
                return null;
            } else {
                return annotation.message();
            }
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static String validateSize(Field field, Object object) {
        if (!field.isAnnotationPresent(Size.class)) {
            return null;
        }
        Size annotation = field.getAnnotation(Size.class);
        int min = annotation.min();
        int max = annotation.max();
        try {
            field.setAccessible(true);
            String value = (String) field.get(object);
            if (value == null) {
                return annotation.message();
            }
            if (value.length() < min || value.length() > max) {
                return annotation.message();
            } else {
                return null;
            }
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static String validateRange(Field field, Object object) {
        if (!field.isAnnotationPresent(Range.class)) {
            return null;
        }
        Range annotation = field.getAnnotation(Range.class);
        int min = annotation.min();
        int max = annotation.max();
        try {
            field.setAccessible(true);
            Integer value = (Integer) field.get(object);
            if (value == null || value > max || value < min) {
                return annotation.message();
            } else {
                return null;
            }
        } catch (IllegalAccessException e) {
            return null;
        }

    }

    private static String validateEmail(Field field, Object object) {
        if (!field.isAnnotationPresent(Email.class)) {
            return null;
        }
        Email annotation = field.getAnnotation(Email.class);
        try {
            field.setAccessible(true);
            String value = (String) field.get(object);
            if (value == null) {
                return annotation.message();
            }
            if (value.matches(emailTemplate)) {
                return null;
            } else {
                return annotation.message();
            }
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}