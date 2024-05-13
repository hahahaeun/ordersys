package com.example.ordersys.controller;

import com.example.ordersys.domain.user.dto.UserDto;
import com.example.ordersys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody UserDto userDto){
        Long rtnIdx = userService.join(userDto);
        return ResponseEntity.ok(rtnIdx);
    }
}
