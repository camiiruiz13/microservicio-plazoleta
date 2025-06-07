package com.retoplazoleta.ccamilo.com.microservicioplazoleta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestUtil {

    public static <T> T invokePrivateMethod(Object instance, String methodName, Class<T> returnType, Class<?>[] parameterTypes, Object... args) throws Exception {
        Method method = instance.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        try {
            return returnType.cast(method.invoke(instance, args));
        } catch (InvocationTargetException e) {
            throw (Exception) e.getTargetException();
        }
    }
}