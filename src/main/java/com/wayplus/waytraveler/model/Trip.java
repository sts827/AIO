package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trip {

    private int trip_id;
    private int user_id;
    private String title;
    private String description;
    private String start_date;
    private String end_date;
    private String create_date;

}
