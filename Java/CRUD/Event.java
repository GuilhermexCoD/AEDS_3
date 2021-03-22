import java.lang.reflect.Method;
import java.util.ArrayList;

public class Event implements EventListener {
    private Class<? extends EventArgs> eventArgsClass;
    private ArrayList<DynamicExecutioner> dynamicExecutioners;

    public Event(Class<? extends EventArgs> eventArgsClass) {

        this.setEventArgsClass(eventArgsClass);
        dynamicExecutioners = new ArrayList<>();
    }

    public Class<? extends EventArgs> getEventArgsClass() {
        return eventArgsClass;
    }

    public void setEventArgsClass(Class<? extends EventArgs> eventArgsClass) {
        this.eventArgsClass = eventArgsClass;
    }

    public int size() {
        return dynamicExecutioners.size();
    }

    @Override
    public void AddListener(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        DynamicExecutioner dynamicExecutioner = new DynamicExecutioner(observer, method);
        dynamicExecutioners.add(dynamicExecutioner);
    }

    @Override
    public void RemoveListener(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {

        for (int i = 0; i < dynamicExecutioners.size(); i++) {
            if (dynamicExecutioners.get(i).isEqual(observer, method)) {
                dynamicExecutioners.remove(i);
            }
        }
    }

    public void update(EventArgs event) {
        for (DynamicExecutioner dynamicExecutioner : dynamicExecutioners) {
            dynamicExecutioner.invokeMethod(event);
        }
    }

}
