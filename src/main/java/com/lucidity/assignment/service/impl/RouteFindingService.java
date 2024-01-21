package com.lucidity.assignment.service.impl;

import com.lucidity.assignment.data.BestRouteDO;
import com.lucidity.assignment.data.LocationInfoDO;
import com.lucidity.assignment.data.RouteInfoDO;
import com.lucidity.assignment.service.IRouteFindingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lucidity.assignment.helper.RouteHelper.isDestinationReached;
import static com.lucidity.assignment.helper.RouteHelper.isValidLocation;
import static com.lucidity.assignment.util.LucidityUtils.haversineDistance;
import static com.lucidity.assignment.util.LucidityUtils.nullSafeList;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
@RequiredArgsConstructor
public class RouteFindingService implements IRouteFindingService {

    @Override
    public BestRouteDO findBestWay(List<LocationInfoDO> locationInfoList) {
        Map<String, List<RouteInfoDO>> routeAdjMap = getRouteAdjInfo(locationInfoList);
        List<List<String>> allCandidatePaths = getAllCandidatePaths(locationInfoList, routeAdjMap);
        return findShortestPath(locationInfoList, allCandidatePaths);
    }

    public BestRouteDO findShortestPath(List<LocationInfoDO> locationInfoList, List<List<String>> allCandidatePaths) {
        BestRouteDO bestRouteDO = null;
        Map<String, LocationInfoDO> locationDataMap = nullSafeList(locationInfoList).stream()
                .collect(Collectors.toMap(LocationInfoDO::getLocationLabel, Function.identity(), (a, b) -> a));
        double minimumDistance = Double.MAX_VALUE;
        for (List<String> path : allCandidatePaths) {
            double distance = 0D;
            for (int i = 1; i < path.size(); i++) {
                LocationInfoDO location = locationDataMap.get(path.get(i));
                LocationInfoDO prevLocation = locationDataMap.get(path.get(i - 1));
                distance += haversineDistance(location.getLat(), location.getLon(), prevLocation.getLat(),
                        prevLocation.getLon());
            }
            if (distance < minimumDistance) {
                minimumDistance = distance;
                bestRouteDO = BestRouteDO.builder().distance(minimumDistance).bestRoute(path).build();
            }
        }
        return bestRouteDO;
    }

    public List<List<String>> getAllCandidatePaths(List<LocationInfoDO> locationInfoList,
                                                   Map<String, List<RouteInfoDO>> routeAdjMap) {
        List<List<String>> allCandidatePaths = new ArrayList<>();
        Queue<List<String>> queue = new LinkedList<>();
        List<String> path = new ArrayList<>();
        path.add("Aman");
        queue.add(path);

        while (!queue.isEmpty()) {
            path = queue.poll();
            String node = path.get(path.size() - 1);
            if (isDestinationReached(locationInfoList, path)) {
                allCandidatePaths.add(path);
            }

            List<RouteInfoDO> routes = isNotBlank(node) && routeAdjMap.containsKey(node) ? routeAdjMap.get(node)
                    : new ArrayList<>();
            for (RouteInfoDO route : nullSafeList(routes)) {
                if (isValidLocation(route.getLocationLabel(), path, locationInfoList)) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(route.getLocationLabel());
                    queue.add(newPath);
                }
            }
        }
        return allCandidatePaths;
    }

    public Map<String, List<RouteInfoDO>> getRouteAdjInfo(List<LocationInfoDO> locationInfoList) {
        Map<String, LocationInfoDO> locationDataMap = nullSafeList(locationInfoList).stream()
                .collect(Collectors.toMap(LocationInfoDO::getLocationLabel, Function.identity(), (a, b) -> a));
        Map<String, List<RouteInfoDO>> routeAdjMap = new HashMap<>();
        for (LocationInfoDO location : nullSafeList(locationInfoList)) {
            List<RouteInfoDO> routes = new ArrayList<>();
            for (String connectedLocation : nullSafeList(location.getConnectedLocations())) {
                if (locationDataMap.containsKey(connectedLocation)) {
                    LocationInfoDO locationInfo = locationDataMap.get(connectedLocation);
                    RouteInfoDO routeInfo = RouteInfoDO.builder().locationType(locationInfo.getLocationType())
                            .locationLabel(locationInfo.getLocationLabel()).orderedFrom(locationInfo.getOrderedFrom())
                            .distance(haversineDistance(locationInfo.getLat(), locationInfo.getLon(), location.getLat(),
                                    location.getLon())).build();
                    routes.add(routeInfo);
                }
            }
            routeAdjMap.put(location.getLocationLabel(), routes);
        }
        return routeAdjMap;
    }
}
