package com.jygoh.heartspace.global.security.auth.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.io.BaseEncoding;
import com.jygoh.heartspace.domain.user.dto.SetInfoReqDto;
import com.jygoh.heartspace.domain.user.service.UsersService;
import com.jygoh.heartspace.global.security.auth.dto.GoogleUserDto;
import com.jygoh.heartspace.global.security.auth.dto.IdTokenDto;
import com.jygoh.heartspace.global.security.auth.service.AuthService;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsersService usersService;
    private final AuthService authService;

    public AuthController(UsersService usersService, AuthService authService) {
        this.usersService = usersService;
        this.authService = authService;
    }

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${client-id}")
    private String CLIENT_ID;

    @PostMapping("/google/login")
    public ResponseEntity<String> googleLogin(@RequestBody IdTokenDto idToken, HttpServletResponse response) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList(CLIENT_ID)).build();

            String cleanedIdToken = idToken.getIdToken();
            GoogleIdToken token = verifier.verify(cleanedIdToken);

            if (token != null) {
                GoogleIdToken.Payload payload = token.getPayload();
                String email = payload.getEmail();
                String profileImagUrl = (String) payload.get("picture");
                String subjectId = payload.getSubject();

                GoogleUserDto userDto = GoogleUserDto.builder()
                    .email(email)
                    .profileImgUrl(profileImagUrl)
                    .subjectId(subjectId)
                    .build();

                TokenResponseDto tokenResponseDto = usersService.processingGoogleUser(userDto);
                if (tokenResponseDto.getAccessToken() != null && tokenResponseDto.getRefreshToken() != null) {
                    response.setHeader("Authorization", "Bearer " + tokenResponseDto.getAccessToken());
                    response.setHeader("Refresh-Token", "Bearer " + tokenResponseDto.getRefreshToken());
                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    response.setStatus(HttpStatus.PRECONDITION_REQUIRED.value());
                    response.setHeader("X-User-ID", tokenResponseDto.getEncodeUserId());
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (BaseEncoding.DecodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/set-info")
    public ResponseEntity<String> setNickname(@RequestBody SetInfoReqDto reqDto, HttpServletResponse response) {
        TokenResponseDto tokenResponseDto = authService.setUserInfo(reqDto);
        if (tokenResponseDto.getAccessToken() != null && tokenResponseDto.getRefreshToken() != null) {
            response.setHeader("Authorization", "Bearer " + tokenResponseDto.getAccessToken());
            response.setHeader("Refresh-Token", "Bearer " + tokenResponseDto.getRefreshToken());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
