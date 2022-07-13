package com.prices.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ActionType {
    CYCLIC("Циклічна акція"),
    PERSONAL("Персональна акція"),
    WEEKEND("Акція вихідного дня"),
    SEASON("Сезонна акція"),
    DISCOUNT("Акція знижка"),
    ONEPLUSONE("Акція 1 + 1");

    private String value;

    ActionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ActionType fromValue(String value) {
        return Stream.of(ActionType.values())
                .filter(actionType -> actionType.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(getBadInputParameterExceptionSupplier(value));
    }

    public static Supplier<IllegalArgumentException> getBadInputParameterExceptionSupplier(String value) {
        final List<String> enumValues = Stream.of(ActionType.values())
                .map(ActionType::getValue)
                .collect(Collectors.toList());
        return () -> new IllegalArgumentException("Invalid action type value " + value + ". Valid values are: " + enumValues);
    }
}
