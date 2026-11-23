package com.example.campusrideapp.domains;

public enum Status {
    AVAILABLE,
    FULL,
    COMPLETED,
    CANCELLED,
    IN_PROCESS;

    @Override
    public String toString(){
        return switch (this.ordinal()){
            case 0 -> "Available";
            case 1 -> "Full";
            case 2 -> "Completed";
            case 3 -> "Cancelled";
            case 4 -> "In Process";
            default -> "N/A";
        };
    }
}
