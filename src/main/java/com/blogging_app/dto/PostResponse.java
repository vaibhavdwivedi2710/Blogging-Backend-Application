package com.blogging_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private List<PostDto> content;

    private Integer pageNumber;

    private Integer pageSize;

    private long totalElements;

    private Integer totalPages;

    private boolean lastPage;

}
