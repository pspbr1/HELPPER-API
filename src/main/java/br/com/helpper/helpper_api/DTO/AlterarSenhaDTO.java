package br.com.helpper.helpper_api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlterarSenhaDTO {

    @NotBlank(message = "Senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter no mínimo 6 caracteres")
    private String novaSenha;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    private String confirmarNovaSenha;

    public AlterarSenhaDTO() {
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmarNovaSenha() {
        return confirmarNovaSenha;
    }

    public void setConfirmarNovaSenha(String confirmarNovaSenha) {
        this.confirmarNovaSenha = confirmarNovaSenha;
    }
}