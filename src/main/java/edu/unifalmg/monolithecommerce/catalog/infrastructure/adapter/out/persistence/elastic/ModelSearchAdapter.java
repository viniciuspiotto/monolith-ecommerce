package edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.elastic;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import edu.unifalmg.monolithecommerce.catalog.application.dto.commands.ModelSearchCommand;
import edu.unifalmg.monolithecommerce.catalog.application.dto.ModelSearchDTO;
import edu.unifalmg.monolithecommerce.catalog.application.port.out.SearchModelsGateway;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.entities.ModelSearchDocument;
import edu.unifalmg.monolithecommerce.catalog.infrastructure.adapter.out.persistence.mapper.ModelSearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelSearchAdapter implements SearchModelsGateway {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ModelSearchMapper modelSearchMapper;

    @Override
    public Page<ModelSearchDTO> search(ModelSearchCommand cmd, Pageable pageable) {

        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        addTextQuery(boolQueryBuilder, cmd.stringText());

        addTermFilter(boolQueryBuilder, cmd.categoryId());
        addRateFilter(boolQueryBuilder, cmd.minRate());
        addPriceRangeFilter(boolQueryBuilder, cmd.minPrice(), cmd.maxPrice());

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(new Query(boolQueryBuilder.build()))
                .withPageable(pageable)
                .build();

        SearchHits<ModelSearchDocument> searchHits = elasticsearchOperations.search(
                nativeQuery,
                ModelSearchDocument.class
        );

        Page<ModelSearchDocument> pageOfDocuments = SearchHitSupport
                .searchPageFor(searchHits, pageable)
                .map(SearchHit::getContent);

        return pageOfDocuments.map(modelSearchMapper::toDto);
    }

    private void addTextQuery(BoolQuery.Builder bq, String textQuery) {
        if (textQuery != null && !textQuery.isBlank()) {
            bq.must(q -> q
                    .multiMatch(m -> m
                            .query(textQuery)
                            .fields("title", "description")
                    )
            );
        } else {
            bq.must(q -> q.matchAll(m -> m));
        }
    }

    private void addTermFilter(BoolQuery.Builder bq, Object value) {
        if (value != null) {
            bq.filter(q -> q
                    .term(t -> t
                            .field("category_id")
                            .value(FieldValue.of(JsonData.of(value)))
                    )
            );
        }
    }

    private void addRateFilter(BoolQuery.Builder bq, Double minRate) {
        if (minRate != null) {
            bq.filter(q -> q
                    .range(r -> r
                            .number(n -> n.field("average_rate")
                                    .gte(minRate)
                            )
                    )
            );
        }
    }

    private void addPriceRangeFilter(BoolQuery.Builder bq, Double minPrice, Double maxPrice) {
        if (minPrice != null || maxPrice != null) {
            bq.filter(q -> q
                    .range(r -> r.number(n -> {
                        n.field("price");
                        if (minPrice != null) {
                            n.gte(minPrice);
                        }
                        if (maxPrice != null) {
                            n.lte(maxPrice);
                        }
                        return n;
                    }))
            );
        }
    }
}

