package com.rybakigor.document.instantiator.util;

import com.rybakigor.document.instantiator.exceptions.CompilationException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Created by igorek2312 on 17.09.16.
 */
public interface ClasspathLoader extends Closeable {
    Object compileAndInstantiate() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, CompilationException;

    Set<String> getDependedFilePaths();

    @Override
    default void close() throws IOException {
        getDependedFilePaths().stream()
                .map(File::new)
                .forEach(File::delete);
    }
}
