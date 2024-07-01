package com.example.ordersys.domain.user;

import com.example.ordersys.domain.user.dto.UserDto;
import com.example.ordersys.domain.user.exception.UserJoinException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public Long join(UserDto userdto){
        userRepository.findByUserId(userdto.userId()).ifPresent(user -> {
            throw new UserJoinException("이미 가입되어 있습니다.");
        });

        User newUser = User.of(userdto.userId(), userdto.password());
        User user = userRepository.save(newUser);
        return user.getIdx();
    }
}
