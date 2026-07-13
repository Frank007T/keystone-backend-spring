package com.keystone.delivery_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keystone.delivery_service.dto.response.DashboardStatsResponse;
import com.keystone.delivery_service.repository.UserRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/stats")
    public DashboardStatsResponse getDashboardStats() {

        long customers = userRepository.count();

        return new DashboardStatsResponse(
                customers,
                0,
                0,
                0
        );
    }
}