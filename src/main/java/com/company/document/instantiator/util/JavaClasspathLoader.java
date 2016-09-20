package com.company.document.instantiator.util;

import com.company.document.instantiator.exceptions.CompilationException;
import com.company.document.instantiator.model.CompilationFile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

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
        // Save source in .java file.
        File root = new File(compilationFile.getSourceRootPath());
        File sourceFile = new File(compilationFile.getProjectFilePath());

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //String compilePackage = sourceFile.getParent() + "/*.java";
        //compiler.run(null, null, null, sourceFile.getPath());

        File[] children = sourceFile.getParentFile().listFiles(
                pathname -> pathname.getName().endsWith(".java")
        );

        String[] arguments = Arrays.stream(children).map(file -> file.getPath())
                .toArray(size -> new String[size]);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compiler.run(null, null, outputStream, arguments);
        byte[] bytes = outputStream.toByteArray();
        String errors=new String(bytes);
        if (!errors.isEmpty())
            throw new CompilationException(errors);

        // Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        Class<?> cls = Class.forName(compilationFile.getClassName(), true, classLoader);
        Object instance = cls.newInstance();

        return instance;
    }

    @Override
    public String getWorkingPackagePath() {
        return compilationFile.getWorkingPackagePath();
    }
}
