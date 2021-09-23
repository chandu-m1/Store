package com.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.model.Category;
import com.store.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories()
	{
		return categoryRepository.findAll();
	}
	
	public void addCategory(Category category)
	{
		categoryRepository.save(category);
	}
	public void delCategoryById(int id)
	{
		categoryRepository.deleteById(id);
	}
	public Optional<Category> updCategoryById(int id)
	{
		return categoryRepository.findById(id);
	}
}
