package EventPack;

import java.lang.reflect.Method;

public interface EventListener {
    public void AddListener(Class<? extends EventArgs> classe, Object observer, Method method);

    public void RemoveListener(Class<? extends EventArgs> classe, Object observer, Method method);

}