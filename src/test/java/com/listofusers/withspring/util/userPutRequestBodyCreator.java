package com.listofusers.withspring.util;

import com.listofusers.withspring.Requests.userPutRequestBody;

public class userPutRequestBodyCreator {
    public static userPutRequestBody CreateUserPutRequestBody(){
        return userPutRequestBody.builder().
                name(userCreator.createValidUpdateUser().getName()).
                cpf(userCreator.createValidUpdateUser().getCpf()).
                rg(userCreator.createValidUpdateUser().getRg()).build();
    }
}
