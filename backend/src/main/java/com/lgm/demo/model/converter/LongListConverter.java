package com.lgm.demo.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<Long> longs) {
        StringBuilder ans = new StringBuilder("");
        for(Long l : longs){
            ans.append(l).append(SPLIT_CHAR);
        }
        return ans.toString();
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        List<Long> ans = new ArrayList<>();
        String[] strings = string.split(SPLIT_CHAR);
        for (String s : strings) {
            ans.add(Long.valueOf(s));
        }
        return ans;
    }
}
