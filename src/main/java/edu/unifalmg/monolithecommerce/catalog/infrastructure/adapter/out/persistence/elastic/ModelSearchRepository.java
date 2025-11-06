package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.elastic;

import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelSearchRepository extends ElasticsearchRepository<ModelSearchDocument, String> {
}
