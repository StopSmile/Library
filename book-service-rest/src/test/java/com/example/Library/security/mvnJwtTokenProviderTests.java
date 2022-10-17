package com.example.Library.security;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class mvnJwtTokenProviderTests {

    @Test
    public void extractJwtFromBearer_whenCorrectBearerValueProvided_shouldReturnJwtToken() {
        UserDetailsService userDetailsServiceMock = Mockito.mock(UserDetailsService.class);
        // given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(userDetailsServiceMock);
        String expectedJWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE2NjQyODU2NjMsImV4cCI6MTY2NDg5MDQ2M30.Bho6cr_srSZPB5vZuODqSdCmEECsdLTJamM2f-lNiXg";
        String testJWTString = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE2NjQyODU2NjMsImV4cCI6MTY2NDg5MDQ2M30.Bho6cr_srSZPB5vZuODqSdCmEECsdLTJamM2f-lNiXg";
        // when
        String extractedJWT = jwtTokenProvider.extractJwtFromBearer(testJWTString);
        // then
        assertEquals(expectedJWT, extractedJWT);
    }
}
