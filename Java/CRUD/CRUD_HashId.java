import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Predicate;

public class CRUD_HashId<TCrud extends Registro, THashId extends RegistroHashExtensivel<THashId>> {
    private CRUD<TCrud> crud;

    private HashExtensivelId<THashId> heId;

    public CRUD_HashId(Constructor<TCrud> construtorTCrud, String fileName, Constructor<THashId> construtorHashId,
            int quantidadeDadosPorCesto) throws Exception {

        this.crud = new CRUD<>(construtorTCrud, fileName + ".db");
        this.heId = new HashExtensivelId<>(construtorHashId, quantidadeDadosPorCesto, fileName + ".hash");
        this.Setup();

    }

    public CRUD_HashId(Constructor<TCrud> construtorTCrud, Constructor<THashId> construtorHashId,
            int quantidadeDadosPorCesto) throws Exception {

        this(construtorTCrud, construtorTCrud.getName(), construtorHashId, quantidadeDadosPorCesto);

    }

    public void Setup() {
        try {
            crud.AddListener(EventArgsPointerChanged.class, this,
                    this.getClass().getMethod("EventOnPointerChanged", EventArgsPointerChanged.class));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void EventOnPointerChanged(EventArgsPointerChanged args) {
        System.out.println("received " + args.pointer);
    }

    public int create(TCrud objeto) throws Exception {
        return crud.create(objeto);
    }

    public TCrud read(int id) throws Exception {
        return crud.read(id);
    }

    public List<TCrud> read(Predicate<TCrud> condition) throws Exception {
        return crud.read(condition);
    }

    public boolean update(TCrud novoObjeto) throws Exception {
        return crud.update(novoObjeto);
    }

    public boolean delete(int id) throws Exception {
        return crud.delete(id);
    }
}
