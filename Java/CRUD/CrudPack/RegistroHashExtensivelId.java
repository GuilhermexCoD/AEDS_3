package CrudPack;

public interface RegistroHashExtensivelId<T> extends RegistroHashExtensivel<T> {
    public void setId(int id);

    public int getId();

    public void setPos(long pos);

    public long getPos();
}
