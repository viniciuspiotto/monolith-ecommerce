package edu.unifalmg.monolithecommerce.catalog.application.dto.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ModelSearchCommand(String stringText, UUID categoryId, Double minPrice, Double maxPrice, Double minRate) {
    @Override
    public String toString() {
        List<String> parts = new ArrayList<>();

        if (stringText != null) parts.add("stringText='" + stringText + "'");
        if (categoryId != null) parts.add("categoryId=" + categoryId);
        if (minPrice != null) parts.add("minPrice=" + minPrice);
        if (maxPrice != null) parts.add("maxPrice=" + maxPrice);
        if (minRate != null) parts.add("minRate=" + minRate);

        if (parts.isEmpty()) {
            return "ModelSearchCommand[no parameters]";
        }

        return "ModelSearchCommand[" + String.join(", ", parts) + "]";
    }
}
