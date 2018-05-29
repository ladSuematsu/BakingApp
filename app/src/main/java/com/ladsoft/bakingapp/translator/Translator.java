package com.ladsoft.bakingapp.translator;

public interface Translator<S, T> {
    T translate(S source);
}
