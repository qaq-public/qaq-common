package com.qaq.base.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageData<T> {

    int page;
    @JsonProperty("page_size")
    int pageSize;
    long total;
    List<T> data; 
}
