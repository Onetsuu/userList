package com.listofusers.withspring.controller;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.Requests.userPutRequestBody;
import com.listofusers.withspring.service.userService;
import com.listofusers.withspring.util.userCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class userControllerTest {
    @InjectMocks
    private userController userController;
    @Mock
    private userService userServiceMock;

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


}