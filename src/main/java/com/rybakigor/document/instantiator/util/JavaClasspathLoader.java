package com.rybakigor.document.instantiator.util;

import com.rybakigor.document.instantiator.exceptions.CompilationException;
import com.rybakigor.document.instantiator.model.CompilationFile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by igorek2312 on 17.09.16.
 */
public class JavaClasspathLoader implements ClasspathLoader {

    private CompilationFile compilationFile;

    public JavaClasspathLoader(CompilationFile compilationFile) {
        this.compilationFile = compilationFile;
    }

    @Override
    public Object compileAndInstantiate() throws ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException, CompilationException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();
        String[] arguments = compilationFile.toCompilerArgs();
        compiler.run(null, null, errorOutputStream, arguments);
        byte[] bytes = errorOutputStream.toByteArray();
        String errors = new String(bytes);
        if (!errors.isEmpty())
            throw new CompilationException(errors);

        URL[] urls = compilationFile.getDependedRootsPaths().stream().map(s -> {
            try {
                return new File(s).toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }).toArray(URL[]::new);

        // Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(urls);
        Class<?> cls = Class.forName(compilationFile.getClassName(), true, classLoader);
        Object instance = cls.newInstance();

        return instance;
    }

    @Override
    public Set<String> getDependedFilePaths() {
        return compilationFile.getDependedFilesPaths().stream()
                .filter(s -> s.endsWith(".java"))
                .map(s -> s.replace(".java", ".class"))
                .collect(Collectors.toSet());
    }

}
