package EventPack;

import CrudPack.Registro;

public class EventArgsPointerUpdated extends EventArgs {
    public long pos;

    public Registro data;

    public EventArgsPointerUpdated(Object sender, Registro data, long pos) {
        super(sender);
        this.pos = pos;
        this.data = data;
    }
}
