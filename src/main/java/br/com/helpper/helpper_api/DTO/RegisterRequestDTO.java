package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.TipoUsuario;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequestDTO {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String confirmarSenha;

    @AssertTrue
    private Boolean termosAceitos;

    @NotNull
    private TipoUsuario tipo; // CLIENTE ou PROFISSIONAL

    public RegisterRequestDTO() {}

}
