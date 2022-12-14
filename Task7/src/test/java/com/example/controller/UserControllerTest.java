package com.example.controller;

import com.example.exceptions.UserNotFoundException;
import com.example.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:SQLScripts/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:SQLScripts/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_create_one_user() throws Exception {
        final File jsonFile = new ClassPathResource("SQLScripts/user.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());
        System.out.println(userToCreate);

        this.mockMvc.perform(post("/user/create")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(3)));

        assertThat(this.repository.findAll()).hasSize(6);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:SQLScripts/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:SQLScripts/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_retrieve_one_user() throws Exception {
        this.mockMvc.perform(get("/user/fetch/{id}", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Julien Mael"))
                .andExpect(jsonPath("$.email").value("laura.royer@yahoo.fr"));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:SQLScripts/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:SQLScripts/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void should_returnException_whenTryGetUserWithIncorrectId() throws Exception {
        this.mockMvc.perform(get("/user/fetch/{id}", 222))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("User with id : 222 not found", result.getResolvedException().getMessage()));
    }


    @Test
    @SqlGroup({
            @Sql(value = "classpath:SQLScripts/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:SQLScripts/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_retrieve_all_users() throws Exception {
        this.mockMvc.perform(get("/user/fetchAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[3].id").value(4))
                .andExpect(jsonPath("$.[4].id").value(5));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:SQLScripts/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:SQLScripts/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_delete_one_user() throws Exception {
        this.mockMvc.perform(delete("/user/delete/{id}", 2))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(this.repository.findAll()).hasSize(4);
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:SQLScripts/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:SQLScripts/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_returnException_whenTryDeleteUserWithIncorrectId() throws Exception {
        this.mockMvc.perform(delete("/user/delete/{id}", 222))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("User with id : 222 not found", result.getResolvedException().getMessage()));
        assertThat(this.repository.findAll()).hasSize(5);
    }

}
