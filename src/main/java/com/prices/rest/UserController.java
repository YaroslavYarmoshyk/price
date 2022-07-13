package com.prices.rest;

import com.prices.mapper.OrikaBeanMapper;
import com.prices.model.User;
import com.prices.model.dto.UserRequestDto;
import com.prices.model.dto.UserResponseDto;
import com.prices.security.annotation.AdminAccessLevel;
import com.prices.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserService userService;
    private final OrikaBeanMapper orikaBeanMapper;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> orikaBeanMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    @AdminAccessLevel
    public UserResponseDto saveUser(@RequestBody UserRequestDto dto) {
       User user = userService.save(orikaBeanMapper.map(dto, User.class));

       return orikaBeanMapper.map(user, UserResponseDto.class);
    }

    @GetMapping("/role/add-to-user")
    @AdminAccessLevel
    public void refreshToken(HttpServletRequest request,
                                          HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
    }

}
