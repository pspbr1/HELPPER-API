/*
 * RegisterRequestDTO.java
 * DTO para cadastro de novos usuários (Contratante ou Prestador)
 * Contém validações de campos obrigatórios e aceite de termos
 * Usado no endpoint de registro POST /api/auth/register
 */
package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.TipoUsuario;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    private String confirmarSenha;

    @AssertTrue(message = "Você deve aceitar os termos de uso")
    private Boolean termosAceitos;

    @NotNull(message = "Tipo de usuário é obrigatório")
    private TipoUsuario tipo; // CONTRATANTE ou PRESTADOR

    @NotBlank(message = "Por favor, informe um CPF")
    private String cpf;

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String nome, String email, String senha, String confirmarSenha,
                              Boolean termosAceitos, TipoUsuario tipo, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.termosAceitos = termosAceitos;
        this.tipo = tipo;
        this.cpf = cpf;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public Boolean getTermosAceitos() {
        return termosAceitos;
    }

    public void setTermosAceitos(Boolean termosAceitos) {
        this.termosAceitos = termosAceitos;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    /**
     * Valida se as senhas conferem
     * @return true se as senhas são iguais
     */
    public boolean senhasConferem() {
        return senha != null && senha.equals(confirmarSenha);
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}