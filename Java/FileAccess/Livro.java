import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.text.DecimalFormat;

public class Livro{
    protected int idLivro;
    protected String titulo;
    protected String autor;
    protected float preco;
    
    DecimalFormat df = new DecimalFormat("#,##0.00");

    public Livro(byte[] ba)throws IOException{
        fromByteArray(ba);
    }

    public Livro(){
        this.idLivro = -1;
        this.titulo = "";
        this.autor = "";
        this.preco = 0.0f;
    }

    public Livro(int idLivro,String titulo,String autor,float preco){
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
    }

    public String toString(){
        return  "\nID: "+idLivro+
                "\nTitulo: "+titulo+
                "\nAutor: "+autor+
                "\nPreco: R$"+df.format(preco); 
    }

    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(idLivro);
        dos.writeUTF(titulo);
        dos.writeUTF(autor);
        dos.writeFloat(preco);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba)throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        idLivro = dis.readInt();
        titulo = dis.readUTF();
        autor = dis.readUTF();
        preco = dis.readFloat();
    }
}