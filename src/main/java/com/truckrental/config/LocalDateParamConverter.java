package com.truckrental.config;

import jakarta.ws.rs.ext.ParamConverter;

import java.time.LocalDate;

public class LocalDateParamConverter implements ParamConverter<LocalDate> {

    @Override
    public LocalDate fromString(String value) {
        if (value == null || value.isEmpty()) return null;
        return LocalDate.parse(value); // ISO-8601 (yyyy-MM-dd)
    }

    @Override
    public String toString(LocalDate value) {
        return value != null ? value.toString() : null;
    }
}