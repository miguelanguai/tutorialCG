package com.ccsw.tutorial.category;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.category.model.Category;

/**
 * @author mguaitav
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}