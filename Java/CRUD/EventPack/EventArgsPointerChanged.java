package EventPack;

import CrudPack.Registro;

public class EventArgsPointerChanged extends EventArgs {
    public long pos;

    public Registro data;

    public EventArgsPointerChanged(Object sender, Registro data, long pos) {
        super(sender);
        this.pos = pos;
        this.data = data;
    }
}
