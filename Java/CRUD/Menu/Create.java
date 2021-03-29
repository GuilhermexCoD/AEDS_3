package Menu;

import java.lang.reflect.Constructor;
import java.util.Scanner;

public class Create<T> extends Action {

    protected Constructor<T> construtor;

    public Create(Constructor<T> construtor, Scanner console) {
        super("INCLUSÃO ", console);
        this.construtor = construtor;
    }

    public Create(String title, Constructor<T> construtor, Scanner console) {
        super(title, console);
        this.construtor = construtor;
    }

}
