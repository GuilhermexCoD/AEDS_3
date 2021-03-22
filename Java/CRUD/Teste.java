public class Teste {
    private String name;

    public Teste(String name) {
        this.name = name;
    }

    public void PrintName(String complemento) {
        System.out.println(String.format("My name is %s %s", name, complemento));
    }

}
