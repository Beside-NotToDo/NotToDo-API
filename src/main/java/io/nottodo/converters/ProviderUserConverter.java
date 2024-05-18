package io.nottodo.converters;

public interface ProviderUserConverter<T,R> {
    R convert(T t);
}
