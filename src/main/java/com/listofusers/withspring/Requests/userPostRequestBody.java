package com.listofusers.withspring.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class userPostRequestBody {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String cpf;
    @NotNull
    @NotEmpty
    private String rg;
}
