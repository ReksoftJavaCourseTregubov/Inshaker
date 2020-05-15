package ru.vsu.amm.inshaker.exceptions.notfound;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> cls, Long id) {
        super("Could not find " + cls.getSimpleName() + " with id " + id);
    }

}
