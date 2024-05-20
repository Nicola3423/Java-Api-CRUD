package org.example.entites;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private String NOME_COMPLETO;
    private String CARGO;


    private String EMAIL_CORPORATIVO;
    private int CONTATO;
    private String EMPRESA;
    private String PAIS;



    public Usuario(){}

    public Usuario(String NOME_COMPLETO, String CARGO, String EMAIL_CORPORATIVO, int CONTATO, String EMPRESA, String PAIS){
        this.NOME_COMPLETO = NOME_COMPLETO;
        this.CARGO = CARGO;
        this.EMAIL_CORPORATIVO = EMAIL_CORPORATIVO;
        this.CONTATO = CONTATO;
        this.EMPRESA = EMPRESA;
        this.PAIS = PAIS;
    }


    public String getNOME_COMPLETO() {
        return NOME_COMPLETO;
    }

    public void setNOME_COMPLETO(String NOME_COMPLETO) {
        this.NOME_COMPLETO = NOME_COMPLETO;
    }

    public String getCARGO() {
        return CARGO;
    }

    public void setCARGO(String CARGO) {
        this.CARGO = CARGO;
    }

    public String getEMAIL_CORPORATIVO() {
        return EMAIL_CORPORATIVO;
    }

    public void setEMAIL_CORPORATIVO(String EMAIL_CORPORATIVO) {
        this.EMAIL_CORPORATIVO = EMAIL_CORPORATIVO;
    }

    public int getCONTATO() {
        return CONTATO;
    }

    public void setCONTATO(int CONTATO) {
        this.CONTATO = CONTATO;
    }

    public String getEMPRESA() {
        return EMPRESA;
    }

    public void setEMPRESA(String EMPRESA) {
        this.EMPRESA = EMPRESA;
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", nome completo='" + NOME_COMPLETO + '\'' +
                ", cargo=" + CARGO +
                ", email=" + EMAIL_CORPORATIVO +
                ", contato=" + CONTATO +
                ", empresa=" + EMPRESA +
                ", Pais=" + PAIS +
                '}';
    }
    public Map<Boolean, String> validate() {
        var result = new HashMap<Boolean, String>();
        if (NOME_COMPLETO == null || NOME_COMPLETO.isEmpty()) {
            result.put(false, "Nome completo não pode estar vazio");
        }
        if (CARGO == null || CARGO.isEmpty()) {
            result.put(false, "Cargo não pode estar vazio");
        }
        if (EMAIL_CORPORATIVO == null || EMAIL_CORPORATIVO.isEmpty()) {
            result.put(false, "Email corporativo não pode estar vazio");
        }
        if (EMPRESA == null || EMPRESA.isEmpty()) {
            result.put(false, "Empresa não pode estar vazia");
        }
        if (PAIS == null || PAIS.isEmpty()) {
            result.put(false, "País não pode estar vazio");
        }
        if (!result.containsKey(false)) {
            result.put(true, "Validação bem-sucedida");
        }
        return result;
    }
}
