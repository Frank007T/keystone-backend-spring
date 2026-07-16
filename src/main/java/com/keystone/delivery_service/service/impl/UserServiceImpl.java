package com.keystone.delivery_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keystone.delivery_service.dto.request.ChangeRoleRequest;
import com.keystone.delivery_service.dto.request.ChangeStatusRequest;
import com.keystone.delivery_service.dto.request.CreateUserRequest;
import com.keystone.delivery_service.dto.request.UpdateUserRequest;
import com.keystone.delivery_service.dto.response.MessageResponse;
import com.keystone.delivery_service.dto.response.UserResponse;
import com.keystone.delivery_service.entity.User;
import com.keystone.delivery_service.repository.UserRepository;
import com.keystone.delivery_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setEnabled(request.isEnabled());

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public MessageResponse deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.delete(user);

        return new MessageResponse("User deleted successfully");
    }

    @Override
    public MessageResponse changeRole(
            Long id,
            ChangeRoleRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setRole(request.getRole());

        userRepository.save(user);

        return new MessageResponse("Role updated successfully");
    }

    @Override
    public MessageResponse changeStatus(
            Long id,
            ChangeStatusRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setEnabled(request.getEnabled());

        userRepository.save(user);

        return new MessageResponse("Status updated successfully");
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {

        return userRepository.findAll()
                .stream()
                .filter(user ->
                        user.getFullName().toLowerCase().contains(keyword.toLowerCase())
                                || user.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}