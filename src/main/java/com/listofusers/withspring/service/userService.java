package com.listofusers.withspring.service;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.domain.user;
import com.listofusers.withspring.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userService {
    private final userRepository repository;

    public List<user> listNonPageable(){
        return repository.findAll();
    }

//    public user findById(Long id){
//        return repository.findById(id).orElseThrow();
//    }
    @Transactional
    public user save(userPostRequestBody userPostRequestBody){
        return repository.save(user.builder()
                .name(userPostRequestBody.getName())
                .cpf(userPostRequestBody.getCpf())
                .rg(userPostRequestBody.getRg())
                .build());
    }
//    public void delete(Long id){
//        repository.delete(findById(id));
//    }
//    public void replace(user user){
//        findById(user.getId());
//
//        user user1 = user.builder()
//                .nome(user.getNome())
//                .cpf(user.getCpf())
//                .rg(user.getRg())
//                .build();
//
//        repository.save(user1);
//    }
//    public List<user> findByName(String nome){
//        return repository.findByName(nome);
//    }

}
