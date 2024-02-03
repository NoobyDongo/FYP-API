package com.application;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private List<SearchCriteria> criteriaList;
    private int page = 0;
    private int size = 10;
    private List<SortDto> sort;
}
