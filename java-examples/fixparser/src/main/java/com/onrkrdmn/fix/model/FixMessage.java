package com.onrkrdmn.fix.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FixMessage {
    private String timestamp;
    private Direction direction;
    private List<FixField> fixFields = new ArrayList<>();


    public void addFixField(FixField fixField) {
        fixFields.add(fixField);
    }


    public enum Direction {
        OUTGOING, INCOMING
    }


    public void prettyPrint() {
        fixFields.forEach(f -> {
            f.prettyPrint();
            System.out.println("\n");
        });
    }
}
