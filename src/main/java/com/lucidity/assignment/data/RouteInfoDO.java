package com.lucidity.assignment.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfoDO {
    /**
     * {@link com.lucidity.assignment.data.enums.LocationType}
     */
    private String locationType;
    private String locationLabel;
    private Double distance;
    private String orderedFrom;
}
