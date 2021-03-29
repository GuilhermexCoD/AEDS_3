package Menu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import Model.Livro;

public class CreateLivro extends Create<Livro> {

    public CreateLivro(Scanner console) throws NoSuchMethodException, SecurityException {
        super(Livro.class.getConstructor(), console);
    }

    @Override
    public void Show()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Livro novoLivro = construtor.newInstance();

        System.out.print("Titulo: ");
        String titulo = console.nextLine();
        System.out.print("Autor: ");
        String autor = console.nextLine();
        System.out.print("Preco: ");
        float preco = Float.valueOf(console.nextLine());

        novoLivro.setTitulo(titulo);
        novoLivro.setAutor(autor);
        novoLivro.setPreco(preco);

    }

}
