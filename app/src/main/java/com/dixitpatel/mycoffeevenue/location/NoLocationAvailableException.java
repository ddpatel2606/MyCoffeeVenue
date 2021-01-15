package com.dixitpatel.mycoffeevenue.location;

/**
 * Custom Exception case.
 */
public class NoLocationAvailableException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No last or current locations are available (try again later)";
    }
}
