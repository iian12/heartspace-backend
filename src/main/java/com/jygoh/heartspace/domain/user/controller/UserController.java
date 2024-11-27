package com.jygoh.heartspace.domain.user.controller;

import com.jygoh.heartspace.domain.user.dto.ProfileResDto;
import com.jygoh.heartspace.domain.user.service.UsersService;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<ProfileResDto> getProfile(@PathVariable String profileId) {
        return ResponseEntity.ok().body(usersService.getUserProfile(profileId));
    }

    @PutMapping("/update/profileImg")
    public ResponseEntity<Void> updateProfileImg(@RequestBody String profileImgUrl, HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);
        usersService.updateProfileImg(profileImgUrl, token);
        return ResponseEntity.ok().build();
    }
}
