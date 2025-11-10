package edu.unifalmg.monolithecommerce.catalog.application.usecase;

import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.GetDownloadLinkModelCommand;
import edu.unifalmg.monolithecommerce.catalog.application.port.in.GetDownloadLinkModelPort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.FileStoragePort;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.ModelRepositoryPort;
import edu.unifalmg.monolithecommerce.catalog.domain.model.Model;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;

@Service
@Log4j2
public class GetDownloadLinkModelUseCase implements GetDownloadLinkModelPort {

    FileStoragePort fileStoragePort;
    ModelRepositoryPort modelRepositoryPort;

    public GetDownloadLinkModelUseCase(
            ModelRepositoryPort modelRepositoryPort,
            @Qualifier("s3FileStorageAdapter") FileStoragePort fileStoragePort
    ) {
        this.modelRepositoryPort = modelRepositoryPort;
        this.fileStoragePort = fileStoragePort;
    }

    @Override
    public URL execute(GetDownloadLinkModelCommand cmd){
        Optional<Model> model = modelRepositoryPort.findById(cmd.modelId());
        if(model.isEmpty()){
            throw new IllegalArgumentException("The model with this id not exists");
        }
        return fileStoragePort.generateUrl(model.get().getZipKey());
    }
}
