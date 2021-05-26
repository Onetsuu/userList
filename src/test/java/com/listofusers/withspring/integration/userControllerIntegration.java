package com.listofusers.withspring.integration;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.domain.user;
import com.listofusers.withspring.repository.userRepository;
import com.listofusers.withspring.util.uerPostRequestBodyCreator;
import com.listofusers.withspring.util.userCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class userControllerIntegration {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private userRepository userRepository;

    @Test
    @DisplayName("ListAll returns list of users when sucessful")
    void ListAll_ReturnsListOfUsers_whenSucessfull(){
        user savedUser = userRepository.save(userCreator.createUserToBeSaved());
        String expectedName = savedUser.getName();

        List<user> users = testRestTemplate.exchange("/users/ListOfAllUsers", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<user>>() {
                }).getBody();

        Assertions.assertThat(users).
                isNotNull().isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);

    }
    @Test
    @DisplayName("findById returns an users when sucessful")
    void findById_ReturnsAnUser_whenSucessfull(){
        user savedUser = userRepository.save(userCreator.createUserToBeSaved());
        Long expectedId = savedUser.getId();

        user user = testRestTemplate.getForObject("/users/{id}", user.class, expectedId);

        Assertions.assertThat(user).
                isNotNull();

        Assertions.assertThat(user.getId()).isEqualTo(expectedId);

    }
    @Test
    @DisplayName("findByName returns user list when Sucessful")
    void findByName_ReturnsUserList_whenSucessfull(){
        user savedUser = userRepository.save(userCreator.createUserToBeSaved());

        String expectedName = savedUser.getName();

        String url= String.format("/users/findByName?name=%s", expectedName);

        List<user> users = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<user>>() {
                }).getBody();

        Assertions.assertThat(users).
                isNotNull().isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName returns an empty user list when unsucessful")
    void findByName_ReturnsEmptyUserList_whenUnsucessfull(){


        List<user> users = testRestTemplate.exchange("/users/findByName?name=QualquerCoisa", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<user>>() {
                }).getBody();

        Assertions.assertThat(users).
                isNotNull().isEmpty();

    }

    @Test
    @DisplayName("findByCpf returns user list when Sucessful")
    void findByCpf_ReturnsUserList_whenSucessfull(){
        user savedUser = userRepository.save(userCreator.createUserToBeSaved());

        String expectedCpf = savedUser.getCpf();

        String url= String.format("/users/findByCpf?cpf=%s", expectedCpf);

        List<user> users = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<user>>() {
                }).getBody();

        Assertions.assertThat(users).
                isNotNull().isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getCpf()).isEqualTo(expectedCpf);
    }
    @Test
    @DisplayName("findByCpf returns an empty user list when unsucessful")
    void findByCpf_ReturnsEmptyUserList_whenUnsucessfull(){

        List<user> users = testRestTemplate.exchange("/users/findByCpf?cpf=999998888777", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<user>>() {
                }).getBody();

        Assertions.assertThat(users).
                isNotNull().isEmpty();

    }
    @Test
    @DisplayName("save returns an user when sucessful")
    void save_ReturnsAnUser_whenUnsucessfull(){
        userPostRequestBody userPostRequestBody = uerPostRequestBodyCreator.CreateUserPostRequestBody();

        ResponseEntity<user> userResponseEntity = testRestTemplate.postForEntity
                ("/users", userPostRequestBody, user.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(userResponseEntity.getBody().getId()).isNotNull();


    }
    @Test
    @DisplayName("replace updates an user when sucessful")
    void replace_updatesAnUser_whenUnsucessfull(){
        user savedUser = userRepository.save(userCreator.createUserToBeSaved());

        savedUser.setName("novo nome");


        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users",
                HttpMethod.PUT, new HttpEntity<>(savedUser), Void.class
        );

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Delete removes an user when sucessful")
    void Delete_removes_whenUnsucessfull(){

        user savedUser = userRepository.save(userCreator.createUserToBeSaved());

        savedUser.setName("novo nome");


        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users/{id}",
                HttpMethod.DELETE, null, Void.class, savedUser.getId()
        );

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
