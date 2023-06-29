package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WayPoint {
    private int waypoint_id;
    private int trip_id;
    private String name;
    private String latitude;
    private String longitude;
    private int order_index;
}
