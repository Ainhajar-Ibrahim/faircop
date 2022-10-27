package com.emse.spring.faircorp.api.controller;

import static com.emse.spring.faircorp.SpringSecurityConfig.ROLE_ADMIN;


import com.emse.spring.faircorp.api.dto.BuildingDto;
import com.emse.spring.faircorp.api.dto.HeaterDto;
import com.emse.spring.faircorp.api.dto.RoomDto;
import com.emse.spring.faircorp.api.dto.WindowDto;
import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BuildingDao buildingDao;

    @MockBean
    private HeaterDao heaterDao;


    @MockBean
    private WindowDao windowDao;


    @MockBean
    private RoomDao roomDao;

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldLoadBuildings() throws Exception {
        given(buildingDao.findAll()).willReturn(List.of(
                createBuilding("building 1"),
                createBuilding("building 2")
        ));

        mockMvc.perform(get("/api/buildings").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("building 1", "building 2")));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldLoadABuildingAndReturnNullIfNotFound() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldLoadABuilding() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.of(createBuilding("building 1")));

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("building 1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldCreateBuilding() throws Exception {
        Building expectedBuilding = createBuilding("building 1");
        expectedBuilding.setId(null);
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedBuilding));

        given(buildingDao.save(any())).willReturn(expectedBuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("building 1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldDeleteBuilding() throws Exception {
        given(buildingDao.getReferenceById(999L)).willReturn(createBuilding("building 1"));
        mockMvc.perform(delete("/api/buildings/999").with(csrf()))
                .andExpect(status().isOk());
    }

    private Building createBuilding(String name) {
        Set<Room> rooms=  new HashSet<>();
        return new Building(name, "Paris", rooms );
    }
}