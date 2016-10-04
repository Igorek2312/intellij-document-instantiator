package com.rybakigor.document.instantiator.util;

import com.google.common.primitives.Primitives;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Random;

/**
 * Created by igorek2312 on 17.09.16.
 */
@SuppressWarnings("unchecked")
public class ObjectInitializerImpl implements ObjectInitializer {
    private Random random = new Random();

    private Object getGenericInitializedInstance(Field field, int genericIndex) throws IllegalAccessException, InstantiationException {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) return new Object();
        ParameterizedType pt = (ParameterizedType) genericType;
        Class<?> genericClass = (Class<?>) pt.getActualTypeArguments()[genericIndex];

        return getDefaultInitializedInstance(genericClass);
    }

    private Object getDefaultInitializedInstance(Class<?> genericClass) throws InstantiationException, IllegalAccessException {
        Object instance;
        if (genericClass.equals(Integer.class)) {
            instance = random.nextInt(1000);
        } else if (genericClass.equals(Long.class)) {
            instance = (long) random.nextInt(1000);
        } else if (genericClass.equals(Byte.class)) {
            instance = (byte) random.nextInt(255);
        } else if (genericClass.equals(Short.class)) {
            instance = (short) random.nextInt(1000);
        } else if (genericClass.equals(Boolean.class)) {
            instance = random.nextBoolean();
        } else if (genericClass.equals(Double.class)) {
            instance = random.nextDouble();
        } else if (genericClass.equals(Float.class)) {
            instance = random.nextFloat();
        } else if (genericClass.equals(String.class)) {
            instance = "string_item" + random.nextInt(1000);
        } else if (genericClass.isEnum()) {
            Object[] enumConstants = genericClass.getEnumConstants();
            if (enumConstants.length>0)
            instance = enumConstants[random.nextInt(enumConstants.length)];
            else{
                String errMsg = "The enum: " + genericClass.getName() + " should contain at least one constant.";
                throw new IllegalArgumentException(errMsg);
            }
        } else {
            instance = genericClass.newInstance();
        }
        initializeObject(instance);
        return instance;
    }

    @Override
    public void initializeObject(Object o) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = o.getClass();
        if (!(Primitives.isWrapperType(clazz)
                || clazz.isPrimitive()
                || clazz.equals(String.class)
                || clazz.isEnum())) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Class t = f.getType();
                Object v = f.get(o);
                if (t.equals(String.class)) {
                    f.set(o, f.getName());
                } else if (t.isArray()) {
                    int length = Array.getLength(v);
                    for (int i = 0; i < length; i++)
                        Array.set(v, i, getDefaultInitializedInstance(t.getComponentType()));
                } else if (Collection.class.isInstance(v)) {
                    Object ins1 = getGenericInitializedInstance(f, 0);
                    Object ins2 = getGenericInitializedInstance(f, 0);
                    Collection collection = (Collection) v;
                    collection.add(ins1);
                    collection.add(ins2);
                } else {
                    f.set(o, getDefaultInitializedInstance(t));
                }
            }
        }
    }


}
