package com.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecificationBuilder<E> {

    private final List<SearchCriteria> params;

    public SearchSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final SearchSpecificationBuilder with(SearchCriteria searchCriteria) {
        System.out.println("Params added searchCriteria");
        params.add(searchCriteria);
        return this;
    }

    public Specification<E> build() {
        if (params.isEmpty()) {
            System.out.println("Params empty");
            return null;
        }

        Specification<E> result = new SearchSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new SearchSpecification(criteria))
                    : Specification.where(result).or(new SearchSpecification(criteria));
        }
        return result;
    }
}
