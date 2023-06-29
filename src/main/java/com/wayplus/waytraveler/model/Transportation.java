package com.wayplus.waytraveler.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transportation {

    private int transportation_id;
    private int trip_id;
    private String mode;
    private String details;

}
