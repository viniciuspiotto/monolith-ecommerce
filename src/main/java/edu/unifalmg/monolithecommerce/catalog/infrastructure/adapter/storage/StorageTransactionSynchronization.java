package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.storage;

import lombok.AllArgsConstructor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class StorageTransactionSynchronization implements TransactionSynchronization {

    private final Path fileToDelete;

    public void register() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(this);
        }
    }

    @Override
    public void afterCompletion(int status) {
        if (status == STATUS_ROLLED_BACK) {
            try {
                Files.deleteIfExists(fileToDelete);
            } catch (IOException e) {
                throw new RuntimeException("Failed to cleanup file on rollback: " + fileToDelete);
            }
        }
    }
}
