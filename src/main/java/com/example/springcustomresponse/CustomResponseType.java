package com.example.springcustomresponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.ResponseEntity;

@JsonSerialize(using = CustomResponseSerializer.class)
public class CustomResponseType<T> {

    private final boolean isError;
    private final ErrorCode errorCode;
    private final T obj;

    private CustomResponseType(boolean isError, T obj, ErrorCode errorCode) {
        this.isError = isError;
        this.errorCode = errorCode;
        this.obj = obj;
    }

    public static <T> CustomResponseType<T> success(T obj) {
        return new CustomResponseType<>(false, obj, null);
    }

    public static <T> CustomResponseType<T> error(ErrorCode errorCode) {
        return new CustomResponseType<>(true, null, errorCode);
    }

    public boolean isError() {
        return this.isError;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public T get() {
        return this.obj;
    }

    public ResponseEntity<CustomResponseType<T>> toResponseEntity() {
        if (this.isError) {
            return ResponseEntity.badRequest().body(this);
        } else {
            return ResponseEntity.ok(this);
        }
    }

}
