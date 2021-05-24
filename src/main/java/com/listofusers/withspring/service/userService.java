package com.listofusers.withspring.service;

import com.listofusers.withspring.Requests.userPostRequestBody;
import com.listofusers.withspring.Requests.userPutRequestBody;
import com.listofusers.withspring.domain.user;
import com.listofusers.withspring.exceptions.BadRequestException;
import com.listofusers.withspring.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userService {
    private final userRepository repository;

    public List<user> listNonPageable(){
        return repository.findAll();
    }

    public user findById(Long id){
        return repository.findById(id).orElseThrow(()->new BadRequestException("User not found!")
        );
    }
    @Transactional
    public user save(userPostRequestBody userPostRequestBody){
        return repository.save(user.builder()
                .name(userPostRequestBody.getName())
                .cpf(userPostRequestBody.getCpf())
                .rg(userPostRequestBody.getRg())
                .build());
    }
    public void delete(Long id){
        repository.delete(findById(id));
    }

    public void replace(userPutRequestBody userPutRequestBody){
        findById(userPutRequestBody.getId());

        user User = user.builder()
                .id(userPutRequestBody.getId())
                .name(userPutRequestBody.getName())
                .rg(userPutRequestBody.getRg())
                .cpf(userPutRequestBody.getCpf())
                .build();

        repository.save(User);

    }
    public List<user> findByName(String nome){
        return repository.findByName(nome);
    }

    public List<user> findByCpf(String cpf){
        return repository.findByCpf(cpf);
    }

}
