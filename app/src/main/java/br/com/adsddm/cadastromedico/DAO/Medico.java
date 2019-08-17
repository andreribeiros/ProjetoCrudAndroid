package br.com.adsddm.cadastromedico.DAO;

public class Medico {

    private Long id;
    private String crm;
    private String telefone;
    private String nome;
    private String uf;
    private byte[]foto;


    public String getUf() {
        return uf;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Medico() {
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getId() + "\n" + getNome() + "\n" + getTelefone() + "\n" + getUf() + " - " + getCrm();
    }
}


