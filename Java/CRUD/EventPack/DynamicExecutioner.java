package EventPack;

import java.lang.reflect.Method;

public class DynamicExecutioner {
    private Object source;
    private Method method;

    public DynamicExecutioner(Object source, Method method) {
        this.setSource(source);
        this.setMethod(method);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public boolean isEqual(Object source, Method method) {
        return (this.source == source && this.method == method);
    }

    public boolean invokeMethod(Object data) {
        boolean called = false;

        try {
            method.invoke(source, data);
            called = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return called;
    }
}
