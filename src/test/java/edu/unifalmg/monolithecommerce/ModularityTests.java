package edu.unifalmg.monolithecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTests {

    @Test
    void verifyModularity() {
        ApplicationModules.of(MonolithEcommerceApplication.class).verify();
    }
}
