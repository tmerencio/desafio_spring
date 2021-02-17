package com.mercadolibre.desafio_java.util.sorter;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class SorterImpl implements Sorter{
    @Override
    public void sort(List arrayList, Comparator comparator) {
        arrayList.sort(comparator);
    }
}
