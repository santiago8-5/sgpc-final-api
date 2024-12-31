package com.grupoingenios.sgpc.sgpc_api_final.exception;

public class EntityInUseException extends RuntimeException{
    public EntityInUseException(String message){
        super(message);
    }
}
