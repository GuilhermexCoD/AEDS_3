public class EventArgsPointerChanged extends EventArgs {
    public long pointer;

    public EventArgsPointerChanged(Object sender, long pointer) {
        super(sender);
        this.pointer = pointer;
    }
}
