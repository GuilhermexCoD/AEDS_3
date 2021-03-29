import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import CrudPack.*;
import Model.*;

class Main {

    public static final String DATA_FOLDER = "DataBase/";

    public static void main(String[] args) {

        // Livros de exemplo
        Livro l1 = new Livro("Eu, Robô", "Isaac Asimov", 14.9F);
        Livro l2 = new Livro("Eu Sou A Lenda", "Richard Matheson", 21.99F);
        Livro l3 = new Livro("Número Zero", "Umberto Eco", 34.11F);
        Livro l4 = new Livro("E o Vento Levou", "Margaret Mitchell", 56.36F);
        Livro l5 = new Livro("Orgulho e Preconceito", "Jane Austen", 41.65F);

        List<Livro> livros = new LinkedList<Livro>();

        int id1, id2, id3, id4, id5;

        try {

            // Abre (cria) o arquivo de livros
            File directory = new File(DATA_FOLDER);
            if (!directory.exists()) {
                directory.mkdir();
            }

            if (directory.isDirectory()) {
                System.out.println("Deleting directory");

                File[] listFiles = directory.listFiles();

                for (File file : listFiles) {
                    file.delete();
                }
            }

            CRUD_HashId<Livro, pcvLivroDireto> arqLivros = new CRUD_HashId<>(Livro.class.getConstructor(),
                    DATA_FOLDER + Livro.class.getName(), pcvLivroDireto.class.getConstructor(), 4);

            // Insere os três livros
            System.out.println("_____________________");
            System.out.println("Criando livros");

            id1 = arqLivros.create(l1);
            l1.setID(id1);
            id2 = arqLivros.create(l2);
            l2.setID(id2);
            id3 = arqLivros.create(l3);
            l3.setID(id3);
            id4 = arqLivros.create(l4);
            l4.setID(id4);
            id5 = arqLivros.create(l5);
            l5.setID(id5);

            PrintAmountArq(arqLivros, 5);

            // Altera um livro para um tamanho maior e exibe o resultado
            System.out.println("_____________________");
            System.out.println("Alterando livros");

            System.out.println("Modificando autor Richard Matheson -> Richard Burton Matheson");
            l2.setAutor("Richard Burton Matheson");
            arqLivros.update(l2);
            System.out.println(arqLivros.read(id2));

            // Altera um livro para um tamanho menor e exibe o resultado
            System.out.println("Modificando autor Isaac Asimov -> I. Asimov");
            l1.setAutor("I. Asimov");
            arqLivros.update(l1);
            System.out.println(arqLivros.read(id1));

            // Excluir um livro e mostra que não existe mais
            System.out.println("_____________________");
            System.out.println("Excluindo livros");
            System.out.println("Excluindo id: " + id3);
            arqLivros.delete(id3);
            Livro l = arqLivros.read(id3);
            if (l == null)
                System.out.println("Livro excluído");
            else
                System.out.println(l);

            PrintAmountArq(arqLivros, 5);

            // System.out.println("Livros com id maior que 1");

            // Predicate<Livro> predicate = livro -> (livro.getID() > 1);

            // livros = arqLivros.read(predicate);

            // for (Livro livroAtual : livros) {
            // System.out.println(livroAtual);
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void PrintAmountArq(CRUD_HashId arq, int number) throws Exception {
        System.out.println("_____________________");
        System.out.println("Lendo todos os livros");

        for (int i = 0; i < number; i++) {
            System.out.println(arq.read(i));

        }
    }
}