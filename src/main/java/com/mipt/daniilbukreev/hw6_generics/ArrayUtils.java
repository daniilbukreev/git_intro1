package com.mipt.daniilbukreev.hw6_generics;

public class ArrayUtils {
    public static <T> int findFirst(T[] array, T element) {
        if (array == null || array.length == 0) {
            return -1;
        }
        if (element == null) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
}
