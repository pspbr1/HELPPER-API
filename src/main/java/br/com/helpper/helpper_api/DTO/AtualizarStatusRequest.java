package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.StatusEnum;

public record AtualizarStatusRequest(StatusEnum status) {
}
