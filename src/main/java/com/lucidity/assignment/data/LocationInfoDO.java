package com.lucidity.assignment.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationInfoDO {
    /**
     * {@link com.lucidity.assignment.data.enums.LocationType}
     */
    private String locationType;
    private String locationLabel;
    private double lat;
    private double lon;
    private String orderedFrom;   // location label of the restaurant
    private List<String> connectedLocations;  // all connected roads to the location
}
