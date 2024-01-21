package com.lucidity.assignment.helper;

import com.lucidity.assignment.data.LocationInfoDO;
import com.lucidity.assignment.data.enums.LocationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lucidity.assignment.util.LucidityUtils.equalsIgnoreCase;
import static com.lucidity.assignment.util.LucidityUtils.nullSafeList;

@Component
@RequiredArgsConstructor
public class RouteHelper {

    public static boolean isValidLocation(String node, List<String> path, List<LocationInfoDO> locationInfoList) {
        Map<String, LocationInfoDO> locationDataMap = nullSafeList(locationInfoList).stream()
                .collect(Collectors.toMap(LocationInfoDO::getLocationLabel, Function.identity(), (a, b) -> a));
        LocationInfoDO locationInfo = locationDataMap.get(node);
        if (equalsIgnoreCase(locationInfo.getLocationType(), LocationType.CUSTOMER.name())) {
            return !path.contains(node) && path.contains(locationInfo.getOrderedFrom());
        }
        return !path.contains(node);
    }

    public static boolean isDestinationReached(List<LocationInfoDO> locationInfoList, List<String> path) {
        List<String> customerLocations = nullSafeList(locationInfoList).stream()
                .filter(location -> equalsIgnoreCase(location.getLocationType(), LocationType.CUSTOMER.name()))
                .map(LocationInfoDO::getLocationLabel)
                .collect(Collectors.toList());
        return new HashSet<>(path).containsAll(customerLocations);
    }
}
