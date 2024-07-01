package com.example.ordersys.controller.user;

import com.example.ordersys.domain.user.dto.UserDto;
import com.example.ordersys.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody UserDto userDto) {
        Long rtnIdx = userService.join(userDto);
        return ResponseEntity.ok(rtnIdx);
    }
}