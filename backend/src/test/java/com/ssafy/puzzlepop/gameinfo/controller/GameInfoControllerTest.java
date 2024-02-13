package com.ssafy.puzzlepop.gameinfo.controller;

import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import com.ssafy.puzzlepop.user.filter.TokenAuthenticationProcessingFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(GameInfoController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class GameInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameInfoService gameInfoService;

    @MockBean
    TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readGameInfo() {
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void readAllGameInfo() throws Exception {
        // given

        // when
        // then

        mockMvc.perform(get("/gameinfo/all"))
                .andExpect(status().isOk())
                .andDo(document("gameinfo-all"));
    }

    @Test
    void findAllByFilter() {
    }

    @Test
    void createGameInfo() {
    }

    @Test
    void updateGameInfo() {
    }

    @Test
    void deleteGameInfo() {
    }
}