package com.example.ordersys.domain.user.dto;


public record UserDto(
        Long idx,
        String userId,
        String password
){
    public static UserDto of(Long idx, String loginId, String password) {
        return new UserDto(idx, loginId, password);
    }

}
