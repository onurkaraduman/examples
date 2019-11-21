package com.onrkrdmn.fix.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FixField {
    private int tag;
    private String name;
    private String value;
    private String valueName;
    private boolean required;

    public void prettyPrint() {
        System.out.format("%s%40s%30s", tag, name, value);
    }
}
