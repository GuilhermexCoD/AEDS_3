package Model;

import java.text.DecimalFormat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import CrudPack.Registro;

public class Livro implements Registro {

  protected int id;
  protected String titulo;
  protected String autor;
  protected float preco;

  DecimalFormat df = new DecimalFormat("#,##0.00");

  public Livro(int id, String titulo, String autor, float preco) {
    setID(id);
    setTitulo(titulo);
    setAutor(autor);
    setPreco(preco);
  }

  public Livro(String titulo, String autor, float preco) {
    setID(-1);
    setTitulo(titulo);
    setAutor(autor);
    setPreco(preco);
  }

  public Livro() {
    this(-1, "", "", 0.0f);
  }

  @Override
  public int getID() {
    return id;
  }

  @Override
  public void setID(int id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getAutor() {
    return this.autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public float getPreco() {
    return this.preco;
  }

  public void setPreco(float preco) {
    this.preco = preco;
  }

  @Override
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(this.id);
    dos.writeUTF(this.titulo);
    dos.writeUTF(this.autor);
    dos.writeFloat(this.preco);

    return baos.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);

    setID(dis.readInt());
    setTitulo(dis.readUTF());
    setAutor(dis.readUTF());
    setPreco(dis.readFloat());
  }

  public String toString() {
    return "\nID: " + id + "\nTitulo: " + titulo + "\nAutor: " + autor + "\nPreco: R$" + df.format(preco);
  }
}