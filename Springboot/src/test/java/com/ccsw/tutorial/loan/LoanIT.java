package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.ccsw.tutorial.loan.model.LoanDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long NOT_EXISTS_LOAN_ID = 0L;
    private static final Long NOT_EXISTS_GAME_ID = 0L;
    private static final Long EXISTS_GAME_ID = 4L; // Barrage
    private static final Long NOT_EXISTS_CLIENT_ID = 0L;
    private static final Long EXISTS_CLIENT_ID = 5L; // Ray Ruiz

    private static final String GAME_ID_PARAM = "idGame";
    private static final String CLIENT_ID_PARAM = "idClient";
    // sin fechas, solo juego y cliente

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(GAME_ID_PARAM, "{" + GAME_ID_PARAM + "}")
                .queryParam(CLIENT_ID_PARAM, "{" + CLIENT_ID_PARAM + "}").encode().toUriString();
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansInDB() {

        int LOANS_WITH_FILTER = 20;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, null);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameShouldReturnLoans() {

        int LOANS_WITH_FILTER = 3; // Juego llamado Barrage: Hay 3 en DDBB

        Map<String, Long> params = new HashMap<>();
        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsClientShouldReturnLoans() {

        int LOANS_WITH_FILTER = 3; // Cliente llamado Ray Ruiz: 3 en DDBB

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, null);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);
        System.out.println(getUrlWithParams());

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameAndClientShouldReturnLoans() {

        // DESPUES AÑADIR FECHAS AQUI y otro metodo de fechas solo

        int LOANS_WITH_FILTER = 1;
        /*
         * Prestamos de Barrage a Ray Ruiz hay 1
         */

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsGameShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, NOT_EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, null);
        params.put(CLIENT_ID_PARAM, NOT_EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsTitleOrClientShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        // no existe ningún parámetro
        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, NOT_EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, NOT_EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null,
                responseType, params);
        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());

        // no existe ningún cliente
        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, NOT_EXISTS_CLIENT_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());

        // no existe ningún juego
        params.put(GAME_ID_PARAM, NOT_EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

}