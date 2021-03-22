import java.lang.reflect.Method;
import java.util.ArrayList;

public class EventHandler implements EventListener {
    Object source;
    private ArrayList<Event> events;

    public EventHandler(Object source) {
        this.source = source;
        events = new ArrayList<>();
    }

    @Override
    public void AddListener(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        // TODO pass method as parameter
        createEvent(eventArgsClass, observer, method);
    }

    @Override
    public void RemoveListener(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        // TODO pass method as parameter
        removeEvent(eventArgsClass, observer, method);
    }

    public boolean hasEvent(Class<? extends EventArgs> eventArgsClass, Object observer) {
        boolean result = false;
        for (Event event : events) {
            if (event.getEventArgsClass() == eventArgsClass) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void createEvent(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        boolean found = false;
        for (Event event : events) {
            if (event.getEventArgsClass() == eventArgsClass) {
                event.AddListener(eventArgsClass, observer, method);
                found = true;
                break;
            }
        }

        if (!found) {
            Event event = new Event(eventArgsClass);
            event.AddListener(eventArgsClass, observer, method);
            events.add(event);
        }

    }

    public void removeEvent(Class<? extends EventArgs> eventArgsClass, Object observer, Method method) {
        for (Event event : events) {
            if (event.getEventArgsClass() == eventArgsClass) {
                event.RemoveListener(eventArgsClass, observer, method);
                if (event.size() <= 0) {
                    events.remove(event);
                }
                break;
            }
        }
    }

    public void CallEvent(EventArgs eventArgs) {
        for (Event event : events) {
            if (event.getEventArgsClass() == eventArgs.getClass()) {
                event.update(eventArgs);
            }
        }
    }
}
