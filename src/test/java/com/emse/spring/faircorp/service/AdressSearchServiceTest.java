package com.emse.spring.faircorp.service;

import com.emse.spring.faircorp.service.dto.ApiGouvAdressDto;
import com.emse.spring.faircorp.service.dto.ApiGouvFeatureDto;
import com.emse.spring.faircorp.service.dto.ApiGouvResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import static com.emse.spring.faircorp.SpringSecurityConfig.ROLE_ADMIN;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(AdressSearchService.class)
class AdressSearchServiceTest {
    @Autowired
    private AdressSearchService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer server;

    @Test
    @WithMockUser(roles = ROLE_ADMIN)
    void shouldFindAdresses() throws JsonProcessingException {
        // Arrange
        ApiGouvResponseDto expectedResponse = simulateApiResponse();

        String expectedUrl = UriComponentsBuilder
                .fromUriString("/search")
                .queryParam("q", "cours+fauriel")
                .queryParam("limit", 15)
                .build()
                .toUriString();

        this.server
                .expect(requestTo(expectedUrl))
                .andRespond(withSuccess(objectMapper.writeValueAsString(expectedResponse), MediaType.APPLICATION_JSON));
        // Act
        List<ApiGouvAdressDto> adresses = this.service.findAdress(List.of("cours", "fauriel"));

        // Assert
        Assertions
                .assertThat(adresses)
                .hasSize(1)
                .extracting(ApiGouvAdressDto::getCity)
                .contains("Saint Etienne");
    }

    private ApiGouvResponseDto simulateApiResponse() {
        ApiGouvAdressDto expectedAdress = new ApiGouvAdressDto();
        expectedAdress.setCity("Saint Etienne");

        ApiGouvFeatureDto expectedFeature = new ApiGouvFeatureDto();
        expectedFeature.setProperties(expectedAdress);

        ApiGouvResponseDto expectedResponse = new ApiGouvResponseDto();
        expectedResponse.setFeatures(List.of(expectedFeature));

        return expectedResponse;
    }
}
