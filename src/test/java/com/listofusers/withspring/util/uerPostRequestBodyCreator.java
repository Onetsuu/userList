package com.listofusers.withspring.util;

import com.listofusers.withspring.Requests.userPostRequestBody;

public class uerPostRequestBodyCreator {

    public static userPostRequestBody CreateAnimePostRequestBody(){
        return userPostRequestBody.
                builder().
                name(userCreator.createUserToBeSaved().getName()).
                cpf(userCreator.createUserToBeSaved().getCpf()).
                rg(userCreator.createUserToBeSaved().getRg())
                .build();
    }
}
