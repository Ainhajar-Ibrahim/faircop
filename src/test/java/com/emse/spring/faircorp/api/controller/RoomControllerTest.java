package com.emse.spring.faircorp.api.controller;

import static com.emse.spring.faircorp.SpringSecurityConfig.ROLE_ADMIN;
import com.emse.spring.faircorp.api.dto.RoomDto;
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

@WebMvcTest(RoomController.class)
class RoomControllerTest {

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
    void shouldLoadRooms() throws Exception {
        given(roomDao.findAll()).willReturn(List.of(
                createRoom("room 1"),
                createRoom("room 2")
        ));

        mockMvc.perform(get("/api/rooms").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("room 1", "room 2")));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldLoadARoomAndReturnNullIfNotFound() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/rooms/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldLoadARoom() throws Exception {
        given(roomDao.findById(999L)).willReturn(Optional.of(createRoom("room 1")));

        mockMvc.perform(get("/api/rooms/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("room 1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldSwitchWindowInRoom() throws Exception {
        Room expectedRoom = createRoom("room 1");

        Assertions.assertThat(expectedRoom.getWindows().stream().findFirst().get().getWindowStatus()).isEqualTo(WindowStatus.OPEN);

        given(roomDao.findById(999L)).willReturn(Optional.of(expectedRoom));

        mockMvc.perform(put("/api/rooms/999/switchWindow").accept(APPLICATION_JSON).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("room 1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldSwitchHeaterInRoom() throws Exception {
        Room expectedRoom = createRoom("room 1");

        Assertions.assertThat(expectedRoom.getHeaters().stream().findFirst().get().getHeaterStatus()).isEqualTo(HeaterStatus.ON);

        given(roomDao.findById(999L)).willReturn(Optional.of(expectedRoom));

        mockMvc.perform(put("/api/rooms/999/switchHeater").accept(APPLICATION_JSON).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("room 1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldUpdateRoom() throws Exception {
        Room expectedRoom = createRoom("room 1");
        expectedRoom.setId(1L);
        String json = objectMapper.writeValueAsString(new RoomDto(expectedRoom));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedRoom.getBuilding());
        given(roomDao.getReferenceById(anyLong())).willReturn(expectedRoom);

        mockMvc.perform(post("/api/rooms").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("room 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldCreateRoom() throws Exception {
        Room expectedRoom = createRoom("room 1");
        expectedRoom.setId(null);
        String json = objectMapper.writeValueAsString(new RoomDto(expectedRoom));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedRoom.getBuilding());
        given(roomDao.save(any())).willReturn(expectedRoom);

        mockMvc.perform(post("/api/rooms").content(json).contentType(APPLICATION_JSON_VALUE).with(csrf()))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("room 1"));
    }

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldDeleteRoom() throws Exception {
        given(roomDao.getReferenceById(999L)).willReturn(createRoom("room 1"));
        mockMvc.perform(delete("/api/rooms/999").with(csrf()))
                .andExpect(status().isOk());
    }


    private Room createRoom(String name) {
        Building building = new Building("B1", "saint-etienne");
        Set<Heater> heaters=  new HashSet<>();
        Set<Window> windows= new HashSet<>();
        Heater heater=new Heater("heater 1", 10L,HeaterStatus.ON );
        heaters.add(heater);
        windows.add(new Window("window 1", WindowStatus.OPEN));
        return new Room(name, 7,10.0, 18.0,heaters,windows,building );
    }

}