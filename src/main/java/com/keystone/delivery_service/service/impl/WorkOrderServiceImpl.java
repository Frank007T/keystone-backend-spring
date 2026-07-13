package com.keystone.delivery_service.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.delivery_service.dto.request.CreateWorkOrderRequest;
import com.keystone.delivery_service.dto.request.UpdateWorkOrderRequest;
import com.keystone.delivery_service.dto.response.WorkOrderResponse;
import com.keystone.delivery_service.entity.Customer;
import com.keystone.delivery_service.entity.User;
import com.keystone.delivery_service.entity.WorkOrder;
import com.keystone.delivery_service.enums.WorkOrderStatus;
import com.keystone.delivery_service.repository.CustomerRepository;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.repository.WorkOrderRepository;
import com.keystone.delivery_service.service.WorkOrderService;

@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public WorkOrderServiceImpl(
            WorkOrderRepository workOrderRepository,
            CustomerRepository customerRepository,
            UserRepository userRepository) {

        this.workOrderRepository = workOrderRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WorkOrderResponse createWorkOrder(CreateWorkOrderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User technician = null;

        if (request.getTechnicianId() != null) {
            technician = userRepository.findById(request.getTechnicianId())
                    .orElseThrow(() -> new RuntimeException("Technician not found"));
        }

        WorkOrder workOrder = new WorkOrder();

        workOrder.setWorkOrderNumber(generateWorkOrderNumber());
        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setPriority(request.getPriority());
        workOrder.setStatus(WorkOrderStatus.NEW);
        workOrder.setCustomer(customer);
        workOrder.setTechnician(technician);
        workOrder.setExpectedCompletionDate(request.getExpectedCompletionDate());

        workOrderRepository.save(workOrder);

        return mapToResponse(workOrder);
    }

    @Override
    public List<WorkOrderResponse> getAllWorkOrders() {

        return workOrderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WorkOrderResponse getWorkOrderById(Long id) {

        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Work Order not found"));

        return mapToResponse(workOrder);
    }

    @Override
    public WorkOrderResponse updateWorkOrder(
            Long id,
            UpdateWorkOrderRequest request) {

        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Work Order not found"));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        User technician = null;

        if (request.getTechnicianId() != null) {
            technician = userRepository.findById(request.getTechnicianId())
                    .orElseThrow(() ->
                            new RuntimeException("Technician not found"));
        }

        User manager = null;

        if (request.getManagerId() != null) {
            manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() ->
                            new RuntimeException("Manager not found"));
        }

        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setPriority(request.getPriority());
        workOrder.setCustomer(customer);
        workOrder.setTechnician(technician);
        workOrder.setManager(manager);
        workOrder.setExpectedCompletionDate(
                request.getExpectedCompletionDate());

        workOrderRepository.save(workOrder);

        return mapToResponse(workOrder);
    }

    @Override
    public void deleteWorkOrder(Long id) {

        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Work Order not found"));

        workOrderRepository.delete(workOrder);
    }

    private String generateWorkOrderNumber() {

        return "WO-"
                + LocalDate.now().toString().replace("-", "")
                + "-"
                + UUID.randomUUID()
                        .toString()
                        .substring(0, 5)
                        .toUpperCase();
    }

    private WorkOrderResponse mapToResponse(WorkOrder workOrder) {

        return WorkOrderResponse.builder()
                .id(workOrder.getId())
                .workOrderNumber(workOrder.getWorkOrderNumber())
                .title(workOrder.getTitle())
                .description(workOrder.getDescription())
                .priority(workOrder.getPriority())
                .status(workOrder.getStatus())

                .customerId(workOrder.getCustomer().getId())
                .customerName(workOrder.getCustomer().getCustomerName())

                .managerId(
                        workOrder.getManager() != null
                                ? workOrder.getManager().getId()
                                : null)

                .managerName(
                        workOrder.getManager() != null
                                ? workOrder.getManager().getFullName()
                                : null)

                .technicianId(
                        workOrder.getTechnician() != null
                                ? workOrder.getTechnician().getId()
                                : null)

                .technicianName(
                        workOrder.getTechnician() != null
                                ? workOrder.getTechnician().getFullName()
                                : null)

                .expectedCompletionDate(workOrder.getExpectedCompletionDate())

                .createdAt(workOrder.getCreatedAt())

                .build();
    }
}