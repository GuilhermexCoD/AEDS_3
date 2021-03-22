import java.lang.reflect.Constructor;

public class HashExtensivelId<T extends RegistroHashExtensivel<T>> extends HashExtensivel<T> {

    public HashExtensivelId(Constructor<T> construtor, int quantidadeDadosPorCesto, String nomeArquivo)
            throws Exception {

        super(construtor, quantidadeDadosPorCesto, nomeArquivo);
    }

}
