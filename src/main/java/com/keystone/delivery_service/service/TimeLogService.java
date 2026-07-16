package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.CreateTimeLogRequest;
import com.keystone.delivery_service.dto.response.TimeLogResponse;

public interface TimeLogService {

    TimeLogResponse createTimeLog(CreateTimeLogRequest request);

    List<TimeLogResponse> getAllTimeLogs();

    TimeLogResponse getTimeLogById(Long id);

    void deleteTimeLog(Long id);

}