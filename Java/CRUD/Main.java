import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import CrudPack.*;
import Model.*;

class Main {

    public static final String DATA_FOLDER = "DataBase/";

    public static void main(String[] args) {

        // Teste teste = new Teste("Guilherme");

        // try {
        // TesteMetodo testeMetodo = new TesteMetodo(teste,
        // teste.getClass().getMethod("PrintName", String.class));
        // testeMetodo.runMethod("eu programo em java");
        // } catch (Exception e) {
        // // TODO: handle exception
        // }

        // Livros de exemplo
        Livro l1 = new Livro("Eu, Robô", "Isaac Asimov", 14.9F);
        Livro l2 = new Livro("Eu Sou A Lenda", "Richard Matheson", 21.99F);
        Livro l3 = new Livro("Número Zero", "Umberto Eco", 34.11F);
        Livro l4 = new Livro("E o Vento Levou", "Margaret Mitchell", 56.36F);
        Livro l5 = new Livro("Orgulho e Preconceito", "Jane Austen", 41.65F);

        List<Livro> livros = new LinkedList<Livro>();

        int id1, id2, id3;

        try {

            // Abre (cria) o arquivo de livros
            File directory = new File(DATA_FOLDER);
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
            id1 = arqLivros.create(l1);
            l1.setID(id1);
            id2 = arqLivros.create(l2);
            l2.setID(id2);
            id3 = arqLivros.create(l3);
            l3.setID(id3);
            arqLivros.create(l4);
            arqLivros.create(l5);

            // Busca por dois livros
            System.out.println("Lendo livros");
            System.out.println(arqLivros.read(id3));
            System.out.println(arqLivros.read(id1));

            // // Altera um livro para um tamanho maior e exibe o resultado
            // System.out.println("Alterando livros");
            // l2.autor = "Richard Burton Matheson";
            // arqLivros.update(l2);
            // System.out.println(arqLivros.read(id2));

            // // Altera um livro para um tamanho menor e exibe o resultado
            // l1.autor = "I. Asimov";
            // arqLivros.update(l1);
            // System.out.println(arqLivros.read(id1));

            // // Excluir um livro e mostra que não existe mais
            // System.out.println("Excluindo livros");
            // arqLivros.delete(id3);
            // Livro l = arqLivros.read(id3);
            // if (l == null)
            // System.out.println("Livro excluído");
            // else
            // System.out.println(l);

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
}