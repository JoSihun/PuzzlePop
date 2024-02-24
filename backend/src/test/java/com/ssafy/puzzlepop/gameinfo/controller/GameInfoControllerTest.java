package com.ssafy.puzzlepop.gameinfo.controller;

import com.ssafy.puzzlepop.gameinfo.domain.GameInfo;
import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoService;
import com.ssafy.puzzlepop.gameinfo.service.GameInfoServiceImpl;
import com.ssafy.puzzlepop.user.filter.TokenAuthenticationProcessingFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(GameInfoController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class GameInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameInfoServiceImpl gameInfoService;

    @MockBean
    TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("게임 정보 조회")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void readGameInfo() throws Exception {
        // given
        GameInfo gameInfo = GameInfo.builder()
                .id(1L)
                .type("battle")
                .isCleared(false)
                .curPlayerCount(0)
                .maxPlayerCount(4)
                .totalPieceCount(100)
                .limitTime(null)
                .passedTime(null)
                .startedTime(null)
                .finishedTime(null)
                .build();
        GameInfoDto responseDto = new GameInfoDto(gameInfo);
        given(gameInfoService.readGameInfo(anyLong())).willReturn(responseDto);

        // when and then
        mockMvc.perform(get("/gameinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("gameId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("gameinfo-read",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("gameId").description("게임 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게임 ID"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("게임 종류"),
                                fieldWithPath("isCleared").type(JsonFieldType.NULL).description("클리어 여부"),
                                fieldWithPath("curPlayerCount").type(JsonFieldType.NUMBER).description("현재 플레이어 수"),
                                fieldWithPath("maxPlayerCount").type(JsonFieldType.NUMBER).description("최대 플레이어 수"),
                                fieldWithPath("totalPieceCount").type(JsonFieldType.NUMBER).description("총 조각 수"),
                                fieldWithPath("limitTime").type(JsonFieldType.NULL).description("제한 시간"),
                                fieldWithPath("passedTime").type(JsonFieldType.NULL).description("경과 시간"),
                                fieldWithPath("startedTime").type(JsonFieldType.NULL).description("시작 시간"),
                                fieldWithPath("finishedTime").type(JsonFieldType.NULL).description("종료 시간")
                        )
                ));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void readAllGameInfo() throws Exception {
        // given

        // when and then
        mockMvc.perform(get("/gameinfo/all")
                        .contentType(MediaType.APPLICATION_JSON))
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