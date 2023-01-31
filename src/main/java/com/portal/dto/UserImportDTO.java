package com.portal.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserImportDTO {

	private String Wpd;
	private String profissional;
	private String codProfissao;
	private String especialidadePrincipal;
	private String cpf;
	private Date dataNascimento;

}
