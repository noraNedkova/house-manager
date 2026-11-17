package com.housemanager.api;

import com.housemanager.dto.ApartmentDTO;
import com.housemanager.service.ApartmentService;
import com.housemanager.web.api.ApartmentApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApartmentApiController.class)
public class ApartmentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApartmentService apartmentService;

    private ApartmentDTO createApartmentDTO(int number, int floor, String area) {
        ApartmentDTO dto = new ApartmentDTO();
        dto.setApartmentNumber(number);
        dto.setFloor(floor);
        dto.setApartmentArea(new BigDecimal(area));
        dto.setNumberOfPetsUsingCommonParts(1);
        dto.setBuildingId(1L);
        return dto;
    }

    @Test
    void getApartmentsStatusOk() throws Exception {
        given(apartmentService.getAllApartments()).willReturn(List.of());

        mockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk());
    }

    @Test
    void getApartmentsTest() throws Exception {
        ApartmentDTO apartment1 = createApartmentDTO(101, 1, "85.50");
        ApartmentDTO apartment2 = createApartmentDTO(102, 1, "92.00");

        given(apartmentService.getAllApartments())
                .willReturn(List.of(apartment1, apartment2));

        mockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apartmentNumber", is(101)))
                .andExpect(jsonPath("$[0].floor", is(1)))
                .andExpect(jsonPath("$[0].apartmentArea", is(85.5)))
                .andExpect(jsonPath("$[0].buildingId", is(1)))
                .andExpect(jsonPath("$[1].apartmentNumber", is(102)))
                .andExpect(jsonPath("$[1].apartmentArea", is(92.0)))
                .andDo(print());
    }

    @Test
    void getApartmentByIdTest() throws Exception {
        given(apartmentService.getApartmentById(1L))
                .willReturn(createApartmentDTO(101, 1, "85.50"));

        mockMvc.perform(get("/api/apartments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartmentNumber", is(101)))
                .andExpect(jsonPath("$.numberOfPetsUsingCommonParts", is(1)));
    }

    @Test
    void createApartmentTest() throws Exception {
        given(apartmentService.createApartment(any(ApartmentDTO.class)))
                .willReturn(createApartmentDTO(101, 1, "85.50"));

        String body = """
                {
                  "apartmentNumber": 101,
                  "floor": 1,
                  "apartmentArea": 85.50,
                  "numberOfPetsUsingCommonParts": 1,
                  "buildingId": 1,
                  "ownerIds": [6, 7]
                }
                """;

        mockMvc.perform(post("/api/apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apartmentNumber", is(101)))
                .andExpect(jsonPath("$.buildingId", is(1)));
    }

    @Test
    void updateApartmentTest() throws Exception {
        given(apartmentService.updateApartment(any(ApartmentDTO.class), eq(11L)))
                .willReturn(createApartmentDTO(101, 2, "90.00"));

        String body = """
                {
                  "apartmentNumber": 101,
                  "floor": 2,
                  "apartmentArea": 90.00,
                  "numberOfPetsUsingCommonParts": 1
                }
                """;

        mockMvc.perform(put("/api/apartments/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.floor", is(2)))
                .andExpect(jsonPath("$.apartmentArea", is(90.0)));
    }

    @Test
    void deleteApartmentTest() throws Exception {
        mockMvc.perform(delete("/api/apartments/5"))
                .andExpect(status().isOk());

        verify(apartmentService).deleteApartment(5L);
    }

    @Test
    void getNumberOfApartmentsByBuildingTest() throws Exception {
        given(apartmentService.getNumberOfApartmentsByBuilding(1L)).willReturn(3);

        mockMvc.perform(get("/api/apartments/number_of_apartments_by_building/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    void getApartmentsByBuildingTest() throws Exception {
        given(apartmentService.getApartmentsByBuilding(1L))
                .willReturn(List.of(createApartmentDTO(101, 1, "85.50")));

        mockMvc.perform(get("/api/apartments/apartments_by_building/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apartmentNumber", is(101)));
    }
}