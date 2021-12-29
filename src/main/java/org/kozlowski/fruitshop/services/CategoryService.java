package org.kozlowski.fruitshop.services;

import org.kozlowski.fruitshop.api.model.CategoryDTO;

import java.util.List;


public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
