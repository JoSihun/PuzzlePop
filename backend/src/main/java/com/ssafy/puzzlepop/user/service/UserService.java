package com.ssafy.puzzlepop.user.service;

import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.exception.UserNotFoundException;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        // 전체 유저들 목록 반환
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserDto getUserByIdAndEmail(Long id, String email) {
        // 해당 id를 가진 유저를 반환
        User user = userRepository.findByIdAndEmail(id, email).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + id + " with " + email)
        );
        return new UserDto(user);
    }

    public UserDto getUserByEmail(String email) {
        // 해당 email을 가진 유저를 반환
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with email: " + email)
        );
        return new UserDto(user);
    }

    public List<UserDto> getUsersByNickname(String nickname) {
        // 해당 닉네임을 가진 유저들 목록 반환
        List<User> users = userRepository.findAllByNickname(nickname);
//        .orElseThrow(
//                () -> new UserRuntimeException("Users not found with nickname: " + nickname)
//        );
        return users.stream().map(UserDto::new).collect(Collectors.toList());
//        return new users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public Long createUser(UserDto requestDto) {
        User user = userRepository.save(requestDto.toEntity());
        return user.getId();
    }

    public Long updateUser(UserDto requestDto) {
        User user = userRepository.findById(requestDto.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + requestDto.getId()));
        user.update(requestDto);
        return userRepository.save(user).getId();
    }

    public void deleteUser(UserDto requestDto) {
        User user = userRepository.findById(requestDto.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + requestDto.getId()));
        userRepository.delete(user);
    }
}