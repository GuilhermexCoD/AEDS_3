package Menu;

import java.lang.reflect.Method;
import java.util.Scanner;
import EventPack.*;

public class Menu implements EventListener {

    Scanner console;

    EventHandler eventHandler;

    public Menu() {
        console = new Scanner(System.in);
        eventHandler = new EventHandler(this);
    }

    public Menu(boolean display) {
        this();
        if (display) {
            DisplayMenu();
        }
    }

    public void DisplayMenu() {
        int opcao = -1;

        System.out.println("\n\n-------------------------------");
        System.out.println("              MENU");
        System.out.println("-------------------------------");
        System.out.println("1 - Inserir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Imprimir");
        System.out.println("0 - Sair");

        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }
    }

    @Override
    public void AddListener(Class<? extends EventArgs> classe, Object observer, Method method) {
        eventHandler.AddListener(classe, observer, method);
    }

    @Override
    public void RemoveListener(Class<? extends EventArgs> classe, Object observer, Method method) {
        eventHandler.RemoveListener(classe, observer, method);
    }
}
