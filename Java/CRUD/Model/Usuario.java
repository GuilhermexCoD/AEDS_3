package Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import CrudPack.Registro;

public class Usuario implements Registro {

  protected int id;
  protected String nome;
  protected String email;
  protected String senha;

  public Usuario(int id, String nome, String email, String senha) {
    setID(id);
    setNome(nome);
    setEmail(email);
    setSenha(senha);
  }

  public Usuario(String nome, String email, String senha) {
    setID(-1);
    setNome(nome);
    setEmail(email);
    setSenha(senha);
  }

  public Usuario() {
    this(-1, "", "", "");
  }

  @Override
  public int getID() {
    return id;
  }

  @Override
  public void setID(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return this.senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  @Override
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(this.id);
    dos.writeUTF(this.nome);
    dos.writeUTF(this.email);
    dos.writeUTF(this.senha);

    return baos.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);

    setID(dis.readInt());
    setNome(dis.readUTF());
    setEmail(dis.readUTF());
    setSenha(dis.readUTF());
  }

  public String toString() {
    return "\nID: " + id + "\nNome: " + nome + "\nEmail: " + email + "\nSenha: " + senha;
  }
}