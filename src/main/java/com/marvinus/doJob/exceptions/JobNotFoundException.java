package com.marvinus.doJob.exceptions;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException() {
        super("Job Not Found");
    }
}
