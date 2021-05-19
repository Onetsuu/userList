package com.listofusers.withspring.controller;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.Requests.userPutRequestBody;
import com.listofusers.withspring.domain.user;
import com.listofusers.withspring.exceptions.BadRequestException;
import com.listofusers.withspring.handler.RestExceptionHandler;
import com.listofusers.withspring.repository.userRepository;
import com.listofusers.withspring.service.userService;
import com.listofusers.withspring.util.uerPostRequestBodyCreator;
import com.listofusers.withspring.util.userCreator;
import com.listofusers.withspring.util.userPutRequestBodyCreator;
import javassist.tools.web.BadHttpRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class userControllerTest {
    @InjectMocks
    private userController userController;
    @Mock
    private userService userServiceMock;
    @Mock
    private userRepository userRepository;

    @BeforeEach
    void setUp(){
        BDDMockito.when(userServiceMock.listNonPageable())
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userServiceMock.findById(
                ArgumentMatchers.anyLong())).
                thenReturn(userCreator.createValidUser());

        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userServiceMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(userPostRequestBody.class)))
                .thenReturn(userCreator.createValidUser());

        BDDMockito.doNothing().when(userServiceMock).replace(ArgumentMatchers.any(userPutRequestBody.class));

        BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.anyLong());


    }
    @Test
    @DisplayName("ListAll returns list of users when sucessful")
    void ListAll_ReturnsListOfUsers_whenSucessfull(){
        String expectedName = userCreator.createValidUser().getName();
        String expectedCpf = userCreator.createValidUser().getCpf();
        String expectedRg = userCreator.createValidUser().getRg();

        List<user> users = userController.ListOfAllUsers().getBody();

        Assertions.assertThat(users).
                isNotNull().
                isNotEmpty().
                hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
        Assertions.assertThat(users.get(0).getCpf()).isEqualTo(expectedCpf);
        Assertions.assertThat(users.get(0).getRg()).isEqualTo(expectedRg);

    }
    @Test
    @DisplayName("findById returns an users when sucessful")
    void findById_ReturnsAnUser_whenSucessfull(){
        Long expectedId = userCreator.createValidUser().getId();


        user user = userController.findById(1L).getBody();

        Assertions.assertThat(user).
                isNotNull();

        Assertions.assertThat(user.getId()).isEqualTo(expectedId);

    }
    @Test
    @DisplayName("findByIdByController returns badRequestException.title when Unsucessful")
    void findByIdByController_ReturnsABadRequestExceptionTtitle_whenUnsucessful(){
        BDDMockito.when(userRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        user user = userController.findById(1L).getBody();

        Assertions.assertThat(user).withFailMessage("Bad Request Exception, check the documentation");

    }
    @Test
    @DisplayName("findByName returns user list when Sucessful")
    void findByName_ReturnsUserList_whenSucessfull(){
        String expectedName = userCreator.createValidUser().getName();

        List<user> users = userController.findByName("").getBody();

        Assertions.assertThat(users).
                isNotNull().
                isNotEmpty().
                hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName returns an empty user list when unsucessful")
    void findByName_ReturnsEmptyUserList_whenUnsucessfull(){
        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<user> users = userController.findByName("").getBody();

        Assertions.assertThat(users).
                isNotNull().
                isEmpty();

    }
    @Test
    @DisplayName("findByCpf returns user list when Sucessful")
    void findByCpf_ReturnsUserList_whenSucessfull(){
        String expectedCpf = userCreator.createValidUser().getCpf();

        List<user> users = userController.findByCpf("").getBody();

        Assertions.assertThat(users).
                isNotNull().
                isNotEmpty().
                hasSize(1);

        Assertions.assertThat(users.get(0).getCpf()).isEqualTo(expectedCpf);
    }
    @Test
    @DisplayName("findByCpf returns an empty user list when unsucessful")
    void findByCpf_ReturnsEmptyUserList_whenUnsucessfull(){
        BDDMockito.when(userServiceMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<user> users = userController.findByCpf("").getBody();

        Assertions.assertThat(users).
                isNotNull().
                isEmpty();
    }
    @Test
    @DisplayName("save returns an user when sucessful")
    void save_ReturnsAnUser_whenUnsucessfull(){
        user user = userController.save(uerPostRequestBodyCreator.CreateAnimePostRequestBody()).getBody();
        Assertions.assertThat(user).isNotNull().isEqualTo(userCreator.createValidUser());

    }
    @Test
    @DisplayName("replace updates an user when sucessful")
    void replace_updatesAnUser_whenUnsucessfull(){

        Assertions.assertThatCode(()->userController.put(userPutRequestBodyCreator.CreateUserPutRequestBody()))
                .doesNotThrowAnyException();

    }
    @Test
    @DisplayName("delete removes an user when sucessful")
    void delete_removes_an_user_whenSucessful() {

        Assertions.assertThatCode(()-> userController.delete(1)).doesNotThrowAnyException();

    }




}