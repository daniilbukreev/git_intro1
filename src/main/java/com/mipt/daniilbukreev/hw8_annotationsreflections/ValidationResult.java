package com.mipt.daniilbukreev.hw8_annotationsreflections;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean isValid;
    private List<String> errors;

    public ValidationResult() {
        this.isValid = true;
        this.errors = new ArrayList<>();
    }

    public boolean isValid() {
        return isValid;
    }
    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        errors.add(error);
        isValid = false;
    }
}

