package com.lgm.demo.model.validation;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// spring validation does not work on List<E>
// change List to ValidList in controller parameter and validation will be applied
@Data
public class ValidList<E> implements List<E> {
    @Valid
    @Delegate
    private List<E> list = new ArrayList<>();
}
