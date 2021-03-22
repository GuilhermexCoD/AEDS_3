package CrudPack;

import EventPack.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Predicate;

public class CRUD_HashId<TCrud extends Registro, THashId extends RegistroHashExtensivelId<THashId>> {
    private CRUD<TCrud> crud;

    private HashExtensivel<THashId> heId;

    public CRUD_HashId(Constructor<TCrud> construtorTCrud, String fileName, Constructor<THashId> construtorHashId,
            int quantidadeDadosPorCesto) throws Exception {

        this.crud = new CRUD<>(construtorTCrud, fileName + ".db");
        this.heId = new HashExtensivel<>(construtorHashId, quantidadeDadosPorCesto, fileName + ".hash");
        this.Setup();

    }

    public CRUD_HashId(Constructor<TCrud> construtorTCrud, Constructor<THashId> construtorHashId,
            int quantidadeDadosPorCesto) throws Exception {

        this(construtorTCrud, construtorTCrud.getName(), construtorHashId, quantidadeDadosPorCesto);

    }

    public void Setup() {
        try {
            crud.AddListener(EventArgsPointerChanged.class, this,
                    this.getClass().getMethod("CreateOnPointerChanged", EventArgsPointerChanged.class));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void CreateOnPointerChanged(EventArgsPointerChanged args) {
        // System.out.println("received " + args.pos);
        // System.out.println("Data " + args.data.toString());
        // System.out.println("ID " + args.data.getID());

        try {
            THashId elem = heId.createInstance();
            elem.setId(args.data.getID());
            elem.setPos(args.pos);
            heId.create(elem);

        } catch (Exception e) {

        }

    }

    public int create(TCrud objeto) throws Exception {
        return crud.create(objeto);
    }

    public TCrud read(int id) throws Exception {
        THashId elem = heId.read(id);
        System.out.println("Encontrei pos: " + elem.getPos());
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
