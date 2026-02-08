package com.studg.service;

import java.util.Arrays;
import java.util.List;

public class SimpleSubjectService implements SubjectService {
    @Override
    public List<String> availableSubjects() {
        return Arrays.asList("Math");
    }
}
