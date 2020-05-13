package ru.vsu.amm.inshaker.exceptions.notfound;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Long id) {
        super("Could not find item " + id);
    }

}
