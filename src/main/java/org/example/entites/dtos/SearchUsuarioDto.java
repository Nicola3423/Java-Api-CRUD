package org.example.entites.dtos;


import org.example.entites.Usuario;


import java.util.List;


public record SearchUsuarioDto(String NOME_COMPLETO, String CARGO, String EMAIL_CORPORATIVO,int CONTATO, String EMPRESA, String PAIS,String orderBy,
                            String direction, int limit, int offset, int totalItems, List<Usuario> usuarios){


}