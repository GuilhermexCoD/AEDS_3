package EventPack;

import CrudPack.Registro;

public class EventArgsPointerRemoved extends EventArgs {
    public long pos;

    public Registro data;

    public EventArgsPointerRemoved(Object sender, Registro data, long pos) {
        super(sender);
        this.pos = pos;
        this.data = data;
    }
}
