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

            crud.AddListener(EventArgsPointerUpdated.class, this,
                    this.getClass().getMethod("UpdateOnPointerChanged", EventArgsPointerUpdated.class));

            crud.AddListener(EventArgsPointerRemoved.class, this,
                    this.getClass().getMethod("RemoveOnPointerChanged", EventArgsPointerRemoved.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void CreateOnPointerChanged(EventArgsPointerChanged args) throws Exception {

        THashId elem = heId.createInstance();
        elem.setId(args.data.getID());
        elem.setPos(args.pos);
        heId.create(elem);

    }

    public void UpdateOnPointerChanged(EventArgsPointerUpdated args) throws Exception {

        THashId elem = heId.createInstance();
        elem.setId(args.data.getID());
        elem.setPos(args.pos);
        heId.update(elem);

    }

    public void RemoveOnPointerChanged(EventArgsPointerRemoved args) throws Exception {

        THashId elem = heId.createInstance();
        elem.setId(args.data.getID());
        elem.setPos(args.pos);
        heId.delete(elem.getId());

    }

    public int create(TCrud objeto) throws Exception {
        return crud.create(objeto);
    }

    public TCrud read(int id) throws Exception {
        THashId elem = heId.read(id);

        TCrud objeto = null;

        if (elem != null && elem.getPos() != -1) {
            objeto = crud.read(elem.getPos());
        }

        return objeto;
    }

    public List<TCrud> read(Predicate<TCrud> condition) throws Exception {
        return crud.read(condition);
    }

    public boolean update(TCrud novoObjeto) throws Exception {

        boolean updated = false;
        THashId elem = heId.read(novoObjeto.getID());

        if (elem.getPos() != -1) {
            updated = crud.update(novoObjeto, elem.getPos());
        }

        return updated;
    }

    public boolean delete(int id) throws Exception {
        boolean deleted = false;
        THashId elem = heId.read(id);

        if (elem.getPos() != -1) {
            deleted = crud.delete(elem.getPos(), id);
        }

        return deleted;
    }
}
