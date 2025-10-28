package edu.unifalmg.monolithecommerce.catalog.domain.model;

public record File (String name,
                    String url,
                    MediaType mediaType,
                    long sizeInBytes) {}
