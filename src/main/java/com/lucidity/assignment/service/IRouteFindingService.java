package com.lucidity.assignment.service;

import com.lucidity.assignment.data.BestRouteDO;
import com.lucidity.assignment.data.LocationInfoDO;

import java.util.List;

public interface IRouteFindingService {

    BestRouteDO findBestWay(List<LocationInfoDO> adjList);
}
