import java.lang.reflect.Constructor;

public class Arquivo<T extends Entidade> {

    Constructor<T> construtor;

    public Arquivo(Constructor<T> construtor) {
        this.construtor = construtor;
    }

    public T newObject() throws Exception {
        T objeto = this.construtor.newInstance();

        return objeto;
    }

}
