package com.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dto<E> {

    private E entity;

    public E toObject() {
        return entity;
    }
}
