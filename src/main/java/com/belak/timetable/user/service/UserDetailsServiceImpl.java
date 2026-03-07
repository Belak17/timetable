package com.belak.timetable.user.service;


import com.belak.timetable.admin.dto.AdminDto;
import com.belak.timetable.admin.entity.AdminEntity;
import com.belak.timetable.user.dto.UserDto;
import com.belak.timetable.user.entity.UserEntity;
import com.belak.timetable.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                authorities
        );
    }
    public UserDto createUserDto(String userId)
    {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé pour userId : " + userId));

        return UserDto.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstname())
                .lastName(userEntity.getLastname())
                .email(userEntity.getEmail())
                .build();
    }
    public Long getProfessorId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé pour userId : " + userId))
                .getId();
    }
}
