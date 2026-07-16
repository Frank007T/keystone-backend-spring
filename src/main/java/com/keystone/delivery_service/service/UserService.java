package com.keystone.delivery_service.service;

import java.util.List;

import com.keystone.delivery_service.dto.request.ChangeRoleRequest;
import com.keystone.delivery_service.dto.request.ChangeStatusRequest;
import com.keystone.delivery_service.dto.request.CreateUserRequest;
import com.keystone.delivery_service.dto.request.UpdateUserRequest;
import com.keystone.delivery_service.dto.response.MessageResponse;
import com.keystone.delivery_service.dto.response.UserResponse;

public interface UserService {

    /**
     * Get all users
     */
    List<UserResponse> getAllUsers();

    /**
     * Get user by id
     */
    UserResponse getUserById(Long id);

    /**
     * Create new user
     */
    UserResponse createUser(CreateUserRequest request);

    /**
     * Update existing user
     */
    UserResponse updateUser(
            Long id,
            UpdateUserRequest request);

    /**
     * Delete user
     */
    MessageResponse deleteUser(Long id);

    /**
     * Change user role
     */
    MessageResponse changeRole(
            Long id,
            ChangeRoleRequest request);

    /**
     * Enable / Disable user
     */
    MessageResponse changeStatus(
            Long id,
            ChangeStatusRequest request);

    /**
     * Search users
     */
    List<UserResponse> searchUsers(String keyword);
}