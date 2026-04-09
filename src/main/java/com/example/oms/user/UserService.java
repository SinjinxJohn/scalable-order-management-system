package com.example.oms.user;


import com.example.oms.exceptions.ResourceAlreadyExists;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public UserService(ModelMapper modelMapper,UserRepository userRepository){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){

        boolean exists = userRepository.existsByEmail(userRequestDTO.getEmail());
        if(exists){
            throw new ResourceAlreadyExists("User with the email already exists");
        }
        User user = User.builder().email(userRequestDTO.getEmail()).phone(userRequestDTO.getPhone())
                .name(userRequestDTO.getName()).build();
        try {
            User savedUser = userRepository.save(user);
            return modelMapper.map(savedUser, UserResponseDTO.class);
        }
        catch(DataIntegrityViolationException e){
            throw new ResourceAlreadyExists("Email already exists");
            }
    }

    public UserResponseDTO getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id not found"));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> modelMapper.map(user,UserResponseDTO.class));
    }


}
