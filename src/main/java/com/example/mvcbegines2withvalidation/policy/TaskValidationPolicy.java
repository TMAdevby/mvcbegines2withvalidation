package com.example.mvcbegines2withvalidation.policy;


import com.example.mvcbegines2withvalidation.model.Task;

public interface TaskValidationPolicy {
    void validate(Task task);
}
