package com.uday.spring.springjwt.dto;

import javafx.util.Pair;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ApiInfo {
    private List<Pair<String, String>> apis;
}
