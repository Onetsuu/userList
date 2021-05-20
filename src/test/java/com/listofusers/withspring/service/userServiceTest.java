package com.listofusers.withspring.service;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.Requests.userPutRequestBody;
import com.listofusers.withspring.repository.userRepository;
import com.listofusers.withspring.util.userCreator;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;


class userServiceTest {
    @InjectMocks
    private userService userService;
    @Mock
    private userRepository userRepository;

    @BeforeEach
    void setUp(){
        BDDMockito.when(userService.listNonPageable())
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userService.findById(
                ArgumentMatchers.anyLong())).
                thenReturn(userCreator.createValidUser());

        BDDMockito.when(userService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userService.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(List.of(userCreator.createValidUser()));

        BDDMockito.when(userService.save(ArgumentMatchers.any(userPostRequestBody.class)))
                .thenReturn(userCreator.createValidUser());

        BDDMockito.doNothing().when(userService).replace(ArgumentMatchers.any(userPutRequestBody.class));

        BDDMockito.doNothing().when(userService).delete(ArgumentMatchers.anyLong());

    }

}