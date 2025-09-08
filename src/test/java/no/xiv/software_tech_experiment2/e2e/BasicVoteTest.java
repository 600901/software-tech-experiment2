package no.xiv.software_tech_experiment2.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BasicVoteTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    private static String pollId;
    private static String blueOptionId;
    private static String redOptionId;
    private static String firstVoteId;

    @BeforeEach
    void setupBaseUrl() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    void createFirstUserIsSuccess() {
        Map<String, String> body = Map.of(
            "username", "User01",
            "email", "user01@mail.com",
            "password", "secretpass123"
        );

        var response = restTemplate.postForEntity(baseUrl + "/users", body, Map.class);
        assertEquals(201, response.getStatusCode().value());
        assertEquals("User01", response.getBody().get("username"));
    }

    @Test
    @Order(2)
    void usersShouldContainFirstUser() {
        var response = restTemplate.getForEntity(baseUrl + "/users", List.class);
        assertEquals(200, response.getStatusCode().value());
        List<Map<String, Object>> users = response.getBody();
        assertTrue(users.stream().anyMatch(u -> "User01".equals(u.get("username"))));
    }

    @Test
    @Order(3)
    void createSecondUserIsSuccess() {
        Map<String, String> body = Map.of(
                "username", "User02",
                "email", "user02@mail.com",
                "password", "notsosafepass!"
        );

        var response = restTemplate.postForEntity(baseUrl + "/users", body, Map.class);
        assertEquals(201, response.getStatusCode().value());
        assertEquals("User02", response.getBody().get("username"));
    }

    @Test
    @Order(4)
    void usersShouldContainBothFirstUserAndSecondUser() {
        var response = restTemplate.getForEntity(baseUrl + "/users", List.class);
        assertEquals(200, response.getStatusCode().value());
        List<Map<String, Object>> users = response.getBody();
        assertTrue(users.stream().anyMatch(u -> "User01".equals(u.get("username"))));
        assertTrue(users.stream().anyMatch(u -> "User02".equals(u.get("username"))));
    }

    @Test
    @Order(5)
    void pollCreatedByFirstUserIsSuccess() {
        Map<String, Object> body = Map.of(
                "question", "What's your favorite color?",
                "publishedAt", "2025-09-06T12:00:00Z",
                "validUntil", "2025-09-07T12:00:00Z",
                "options", List.of(
                        Map.of("caption", "Blue", "representationOrder", 1),
                        Map.of("caption", "Red", "representationOrder", 2)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        var response = restTemplate.exchange(
            baseUrl + "/polls?username=User01",
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        assertEquals(201, response.getStatusCode().value());

        var poll = response.getBody();
        assertNotNull(poll);

        pollId = poll.get("id").toString();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> options = (List<Map<String, Object>>) poll.get("options");

        blueOptionId = options.get(0).get("id").toString();
        redOptionId = options.get(1).get("id").toString();
    }

    @Test
    @Order(6)
    void pollsShouldContainCreatedPoll() {
        var response = restTemplate.getForEntity(baseUrl + "/polls", List.class);
        assertEquals(200, response.getStatusCode().value());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> polls = response.getBody();

        assertNotNull(polls);
        assertTrue(polls.stream().anyMatch(p -> pollId.equals(p.get("id").toString())));
    }

    @Test
    @Order(7)
    void secondUserVotesOnFirstOptionIsSuccess() {
        Map<String, String> body = Map.of(
                "voterUsername", "User02",
                "voteOptionId", blueOptionId
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        var response = restTemplate.postForEntity(baseUrl + "/polls/" + pollId + "/votes", request, Map.class);
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, Object> vote = response.getBody();

        assertEquals("User02", vote.get("voterUsername"));
        firstVoteId = vote.get("id").toString();
    }

    @Test
    @Order(8)
    void secondUserChangesVoteOptionIsSuccess() {
        Map<String, String> body = Map.of("voteOptionId", redOptionId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        var response = restTemplate.exchange(baseUrl + "/polls/" + pollId + "/votes/" + firstVoteId, HttpMethod.PUT, request, Map.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(redOptionId, response.getBody().get("voteOptionId").toString());
    }

    @Test
    @Order(9)
    @SuppressWarnings("unchecked")
    void checkThatSecondUserOnlyHasOneVote() {
        var response = restTemplate.getForEntity(baseUrl + "/polls/" + pollId + "/votes", List.class);
        assertEquals(200, response.getStatusCode().value());
        List<Map<String, Object>> votes = response.getBody();
        assertNotNull(votes);
        assertTrue(votes.stream().anyMatch(v -> "User02".equals(v.get("voterUsername")) &&
                redOptionId.equals(v.get("voteOptionId").toString())));
    }

    @Test
    @Order(10)
    void deletePollIsSuccess() {
        restTemplate.delete(baseUrl + "/polls/" + pollId);
    }

    @Test
    @Order(11)
    void pollShouldBeDeleted() {
        var response = restTemplate.getForEntity(baseUrl + "/polls/" + pollId + "/votes", List.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

}

