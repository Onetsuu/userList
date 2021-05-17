package com.listofusers.withspring.util;

import com.listofusers.withspring.domain.user;

public class userCreator {
    public static user createUserToBeSaved(){
        return user.builder().
                name("Htest").
                cpf("9999999").
                rg("888888").
                build();
    }
    public static user createValidUser(){
        return user.builder().
                id(1L).
                name("testt").
                cpf("9999999").
                rg("888888").
                build();
    }
    public static user createValidUpdateUser(){
        return user.builder().
                id(1L).
                name("testtt").
                cpf("9999999").
                rg("888888").
                build();
    }

}
