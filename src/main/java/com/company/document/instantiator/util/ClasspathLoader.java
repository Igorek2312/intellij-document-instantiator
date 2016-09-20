package com.company.document.instantiator.util;

import com.company.document.instantiator.exceptions.CompilationException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * Created by igorek2312 on 17.09.16.
 */
public interface ClasspathLoader extends Closeable {
    Object compileAndInstantiate() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, CompilationException;

    String getWorkingPackagePath();

    @Override
    default void close() throws IOException{
        File path = new File(getWorkingPackagePath());

        Arrays.stream(path.listFiles(pathname -> pathname.getName().endsWith(".class")))
                .forEach(file -> file.delete());
    }
}
