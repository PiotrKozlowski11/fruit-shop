package org.kozlowski.fruitshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.mapper.CategoryMapper;
import org.kozlowski.fruitshop.api.model.CategoryDTO;
import org.kozlowski.fruitshop.domain.Category;
import org.kozlowski.fruitshop.respositories.CategoryRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {


    public static final Long ID = 2L;
    public static final String NAME = "Andrew";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE,categoryRepository);
    }

    @Test
    void getAllCategories() {
        //given
        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);

        //when
        List<CategoryDTO> categoryDTOS =categoryService.getAllCategories();


        //then
        assertEquals(3, categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        //then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}