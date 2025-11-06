package edu.unifalmg.monolithecommerce.catalog.domain.model.vo;

public record Rate(double value) {
    public Rate {
        if (value < 0 || value > 5.0) {
            throw new IllegalArgumentException("Rate must be between 0 and 5.0.");
        }
    }

    public static Rate zero() {
        return new Rate(0.0);
    }
}
