package com.ssafy.puzzlepop.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Picture {
    private int width;
    private int length;
    private String encodedString;
}