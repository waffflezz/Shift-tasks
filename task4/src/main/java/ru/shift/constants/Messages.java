package ru.shift.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {
    public static final String AVAILABLE_FUNCTIONS = "Available functions:";
    public static final String MENU_ITEM = "%d. %s\n";
    public static final String CHOOSE_FUNCTION = "Choose function: ";
    public static final String FUNCTION_NUMBER_MUST_BE_BETWEEN = "Function number must be between 1 and %d\n";
    public static final String FIXED_EXECUTOR = "fixed-executor";
    public static final String FORK_JOIN = "fork-join";
    public static final String AVAILABLE_MULTITHREAD_SOLVERS = "Available multithread solvers for N greater than threshold:";
    public static final String CHOOSE_MULTITHREAD_SOLVER = "Choose multithread solver: ";
    public static final String SOLVER_NUMBER_MUST_BE_BETWEEN = "Solver number must be between 1 and %d\n";
    public static final String ENTER_N = "Enter N: ";
    public static final String USING_SINGLE_THREAD_SOLVER =
            "N = %d is less than or equal to threshold = %d, using single-thread solver\n";
    public static final String USING_MULTITHREAD_SOLVER =
            "N = %d is greater than threshold = %d, using %s solver\n";
    public static final String CALCULATION_RESULT =
            "%s: sum of %d terms for '%s' = %.12f, expected infinite sum = %.12f\n";
}
