package com.mercadolibre.desafio_java.util.sorter;

import java.util.Comparator;
import java.util.List;

public interface Sorter<T> {
    void sort(List<T> arrayList, Comparator<T> comparator);
}
