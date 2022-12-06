package com.example.dropwizard_form_sql.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    public Utils() {
    }

    public static List<Integer> sectorList(String sectors) {
        return Arrays.stream(sectors.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static String concatSectors(List<Integer> sectors) {
        return StringUtils.join(sectors, ",");
    }

    public static String generateUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getSectors(List<String> sectors) {
        return String.join(",", sectors);
    }
}
