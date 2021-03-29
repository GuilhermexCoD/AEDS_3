package Menu;

import java.util.Scanner;

public class Action {
    protected String title;
    protected Scanner console;

    public Action(String title, Scanner console) {
        this.title = title;
        this.console = console;
    }

    public void Show() throws Exception {
        System.out.println("\n\n" + title);
    }
}
