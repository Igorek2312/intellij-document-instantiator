package com.company.document.instantiator.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Random;

/**
 * Created by igorek2312 on 17.09.16.
 */
@SuppressWarnings("unchecked")
public class ObjectInitializerimpl implements ObjectInitializer {
    private Random random = new Random();

    private Object getGenericClassInstance(Field field, int genericIndex) throws IllegalAccessException, InstantiationException {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) return new Object();
        ParameterizedType pt = (ParameterizedType) genericType;
        Class<?> genericClass = (Class<?>) pt.getActualTypeArguments()[genericIndex];
        return genericClass.newInstance();
    }


    @Override
    public void initializeObject(Object o) throws IllegalAccessException, InstantiationException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            Class t = f.getType();
            Object v = f.get(o);
            if (t.equals(String.class)) {
                f.set(o, f.getName());
            } else if (t.equals(byte.class) || t.equals(Byte.class)) {
                f.set(o, (byte) random.nextInt(255));
            } else if (t.equals(short.class) || t.equals(Short.class)) {
                f.set(o, random.nextInt(1000));
            } else if (t.equals(int.class) || t.equals(Integer.class)) {
                f.set(o, random.nextInt(1000));
            } else if (t.equals(long.class) || t.equals(Long.class)) {
                f.set(o, (long) random.nextInt(1000));
            } else if (t.equals(double.class) || t.equals(Double.class)) {
                f.set(o, random.nextDouble());
            } else if (t.equals(float.class) || t.equals(Float.class)) {
                f.set(o, random.nextFloat());
            } else if (t.equals(boolean.class) || t.equals(Boolean.class)) {
                f.set(o, random.nextBoolean());
            } else if (Collection.class.isInstance(v)) {
                Object ins1 = getGenericClassInstance(f, 0);
                Object ins2 = getGenericClassInstance(f, 0);
                initializeObject(ins1);
                initializeObject(ins2);
                Collection collection = (Collection) v;
                collection.add(ins1);
                collection.add(ins2);
            } else {
                Object instance = t.newInstance();
                initializeObject(instance);
                f.set(o, instance);
            }
        }

    }
}
