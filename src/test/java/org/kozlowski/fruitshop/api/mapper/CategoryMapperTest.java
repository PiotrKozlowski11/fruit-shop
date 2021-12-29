package org.kozlowski.fruitshop.api.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.model.CategoryDTO;
import org.kozlowski.fruitshop.domain.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    public static final String NAME = "Piotr";
    public static final long ID = 1L;

    CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {

        categoryMapper = CategoryMapper.INSTANCE;
    }

    @Test
    void categoryToCategoryDTO() {

        //given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}