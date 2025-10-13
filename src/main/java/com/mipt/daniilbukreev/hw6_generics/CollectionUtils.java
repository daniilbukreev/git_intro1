package com.mipt.daniilbukreev.hw6_generics;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> mergeLists(List<? extends T> list1,
                                         List<? extends T> list2) {
        List<T> list = new ArrayList<>();

        if (list1 != null) {
            list.addAll(list1);
        }
        if (list2 != null) {
            list.addAll(list2);
        }

        return list;
    }

    public static <T> void addAll(List<? super T> destination,
                                  List<? extends T> source) {
        if (source == null || destination == null) {
            return;
        }
        destination.addAll(source);
    }
}
