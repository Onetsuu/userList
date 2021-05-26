package com.listofusers.withspring.service;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.Requests.userPutRequestBody;
import com.listofusers.withspring.domain.user;
import com.listofusers.withspring.exceptions.BadRequestException;
import com.listofusers.withspring.repository.userRepository;
import com.listofusers.withspring.util.uerPostRequestBodyCreator;
import com.listofusers.withspring.util.userCreator;
import com.listofusers.withspring.util.userPutRequestBodyCreator;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class userServiceTest {
    @InjectMocks
    private userService userService;
    @Mock
    private userRepository userRepositoryMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(userRepositoryMock.findAll())
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findById(
                ArgumentMatchers.anyLong())).
                thenReturn(Optional.of(userCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(user.class)))
                .thenReturn(userCreator.createValidUser());


        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(user.class));

    }
    @Test
    @DisplayName("ListAll returns list of users when sucessful")
    void ListAll_ReturnsListOfUsers_whenSucessfull(){
        String expectedName = userCreator.createValidUser().getName();

        List<user> users = userService.listNonPageable();

        Assertions.assertThat(users).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns an users when sucessful")
    void findById_ReturnsAnUser_whenSucessfull(){
        Long expectedId = userCreator.createValidUser().getId();

        user user = userService.findById(1L);

        Assertions.assertThat(user).
                isNotNull();

        Assertions.assertThat(user.getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("findById returns badRequestException when Unsucessful")
    void findByIdBy_ReturnsABadRequestExceptionT_whenUnsucessful(){
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class).
                isThrownBy(()->userService.findById(1L));

    }

    @Test
    @DisplayName("findByName returns user list when Sucessful")
    void findByName_ReturnsUserList_whenSucessfull(){
        String expectedName = userCreator.createValidUser().getName();

        List<user> users = userService.findByName("");

        Assertions.assertThat(users).
                isNotNull().
                isNotEmpty().
                hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName returns an empty user list when unsucessful")
    void findByName_ReturnsEmptyUserList_whenUnsucessfull(){
        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<user> users = userService.findByName("");

        Assertions.assertThat(users).
                isNotNull().
                isEmpty();
    }

    @Test
    @DisplayName("findByCpf returns user list when Sucessful")
    void findByCpf_ReturnsUserList_whenSucessfull(){
        String expectedCpf = userCreator.createValidUser().getCpf();

        List<user> users = userService.findByCpf("");

        Assertions.assertThat(users).
                isNotNull().
                isNotEmpty().
                hasSize(1);

        Assertions.assertThat(users.get(0).getCpf()).isEqualTo(expectedCpf);
    }
    @Test
    @DisplayName("findByCpf returns an empty user list when unsucessful")
    void findByCpf_ReturnsEmptyUserList_whenUnsucessfull(){
        BDDMockito.when(userRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<user> users = userService.findByCpf("");

        Assertions.assertThat(users).
                isNotNull().
                isEmpty();
    }
    @Test
    @DisplayName("save returns an user when sucessful")
    void save_ReturnsAnUser_whenUnsucessfull(){
        user user = userService.save(uerPostRequestBodyCreator.CreateUserPostRequestBody());
        Assertions.assertThat(user).isNotNull().isEqualTo(userCreator.createValidUser());

    }
    @Test
    @DisplayName("replace updates an user when sucessful")
    void replace_updatesAnUser_whenUnsucessfull(){

        Assertions.assertThatCode(()->userService.replace(userPutRequestBodyCreator.CreateUserPutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Delete removes an user when sucessful")
    void Delete_removes_whenUnsucessfull(){

        Assertions.assertThatCode(()->userService.delete(1L))
                .doesNotThrowAnyException();

    }


}