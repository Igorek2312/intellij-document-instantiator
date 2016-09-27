package com.rybakigor.document.instantiator.exceptions;

/**
 * Created by igorek2312 on 20.09.16.
 */
public class CompilationException extends Exception {
    public CompilationException(String errors) {
        super(errors);
    }
}
