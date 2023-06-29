package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Route {
    private int route_id;
    private int trip_id;
    private String name;
    private String polyline;
}
