import java.lang.reflect.Method;

public class TesteMetodo {

    private Object source;
    private Method method;

    public TesteMetodo(Object source, Method method) {
        this.source = source;
        this.method = method;
    }

    public void runMethod(Object complemento) {
        try {
            method.invoke(source, complemento);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
