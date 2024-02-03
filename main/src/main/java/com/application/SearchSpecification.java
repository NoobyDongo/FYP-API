package com.application;

import static com.application.SearchOperation.BEGINS_WITH;
import static com.application.SearchOperation.DOES_NOT_BEGIN_WITH;
import static com.application.SearchOperation.ENDS_WITH;
import static com.application.SearchOperation.GREATER_THAN;
import static com.application.SearchOperation.NOT_EQUAL;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification<E> implements Specification<E> {

    private final SearchCriteria searchCriteria;

    public SearchSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();

        Expression key;
        if (searchCriteria.getFilterKey().contains(".")) {
            String[] keys = searchCriteria.getFilterKey().split("\\.");
            key = root.join(keys[0]).<String>get(keys[1]);
        } else {
            key = root.<String>get(searchCriteria.getFilterKey());
        }

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS -> {
                return cb.like(cb.lower(key.as(String.class)), "%" + strToSearch + "%");
            }
            case DOES_NOT_CONTAIN -> {
                return cb.notLike(cb.lower(key.as(String.class)),
                        "%" + strToSearch + "%");
            }
            case EQUAL -> {
                return cb.equal(cb.lower(key.as(String.class)),
                        strToSearch);
            }
            case NOT_EQUAL -> {
                return cb.notEqual(cb.lower(key),
                        strToSearch);
            }
            case BEGINS_WITH -> {
                return cb.like(cb.lower(key.as(String.class)),
                        strToSearch + "%");
            }
            case DOES_NOT_BEGIN_WITH -> {
                return cb.notLike(cb.lower(key.as(String.class)),
                        strToSearch + "%");
            }
            case ENDS_WITH -> {
                return cb.like(cb.lower(key.as(String.class)),
                        "%" + strToSearch);
            }
            case DOES_NOT_END_WITH -> {
                return cb.notLike(cb.lower(key.as(String.class)),
                        "%" + strToSearch);
            }
            case NUL -> {
                return cb.isNull(cb.lower(key.as(String.class)));
            }
            case NOT_NULL -> {
                return cb.isNotNull(cb.lower(key.as(String.class)));
            }
            case GREATER_THAN -> {
                return cb.greaterThan(key, strToSearch);
            }
            case GREATER_THAN_EQUAL -> {
                return cb.greaterThanOrEqualTo(key, strToSearch);
            }
            case LESS_THAN -> {
                return cb.lessThan(key, strToSearch);
            }
            case LESS_THAN_EQUAL -> {
                return cb.lessThanOrEqualTo(key, strToSearch);
            }
            default -> {
                return null;
            }
//.....
            //.....
        }
    }
    /*
    private Expression<String> join(Root<E> root, SearchCriteria searchCriteria) {
        if (key.equals("deptName")) {
            Join<E, Department> j = root.join("department");
            return j.<String>get(key);
        }
    }     private Join<Employee,Department> departmentJoin(Root<Employee>  
                                       root){
            return root.join("department");
     }
     */
}
