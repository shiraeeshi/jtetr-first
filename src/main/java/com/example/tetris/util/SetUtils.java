package com.example.tetris.util;

import java.util.HashSet;
import java.util.Set;

public class SetUtils {

    public static <T> Set<T> set(T ... items) {
        Set<T> result = new HashSet<>();
        for (T item: items) {
            result.add(item);
        }
        return result;
    }

}
