package com.lucidity.assignment.controller;

import com.lucidity.assignment.data.BestRouteDO;
import com.lucidity.assignment.data.LocationInfoDO;
import com.lucidity.assignment.service.IRouteFindingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lucidity")
public class LucidityAPI {

    private final IRouteFindingService routeFindingService;

    @PostMapping("/find-best-route")
    public BestRouteDO findBestRoute(@RequestBody List<LocationInfoDO> locationInfoDOList) {
        return routeFindingService.findBestWay(locationInfoDOList);
    }
}
