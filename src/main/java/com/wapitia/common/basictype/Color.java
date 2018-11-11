package com.wapitia.common.basictype;

import lombok.Data;
import lombok.Getter;

/** Color container for service layer, expecting java.awt not to be available.*/
@Data
public class Color {

    @Getter
    private final int r;
    @Getter
    private final int g;
    @Getter
    private final int b;
}
