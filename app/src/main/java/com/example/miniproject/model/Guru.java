package com.example.miniproject.model;


public class Guru {

    public int id_guru;
    public String nama, alamat, jenis_kelamin, no_telp, foto, username, password;

    public Guru(int id_guru, String nama, String alamat, String jenis_kelamin, String no_telp, String foto, String username, String password) {
        this.id_guru = id_guru;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.no_telp = no_telp;
        this.foto = foto;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id_guru;
    }

    public void setId(int id_guru) {
        this.id_guru = id_guru;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public class ViewHolder {
    }
}
