package com.example.ordersys.domain.user;

import com.example.ordersys.domain.user.dto.UserDto;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        // given
        UserDto dto = UserDto.of("test", "1234");
        User savedUser = User.builder()
                .idx(1L)
                .userId(dto.userId())
                .password(dto.password())
                .build();

        // mocking
        when(userRepository.findByUserId(dto.userId())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        Long result = userService.join(dto);

        // then
        assertNotNull(result);
        assertEquals(1L, result);
    }
}