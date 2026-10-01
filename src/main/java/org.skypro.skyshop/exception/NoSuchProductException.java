package org.skypro.skyshop.exception;

import java.util.UUID;

public class NoSuchProductException extends RuntimeException {

    public NoSuchProductException(UUID id) {
        super("Товар по ID:" + id + " не найден");
    }
}
