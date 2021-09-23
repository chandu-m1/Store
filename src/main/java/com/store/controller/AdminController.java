package com.store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.store.dto.ProductDto;
import com.store.model.Category;
import com.store.model.Product;
import com.store.service.CategoryService;
import com.store.service.ProductService;

@Controller
public class AdminController {
		public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
		@Autowired
		CategoryService categoryService;
		@Autowired
		ProductService productService;
		@GetMapping("/admin")
		public String adminHome()
		{
			return "adminHome";
		}
		@GetMapping("/admin/categories")
		public String getCategories(Model model)
		{
			model.addAttribute("categories", categoryService.getAllCategories());
			return "categories";
		}

		@GetMapping("/admin/categories/add")
		public String getCatAdd(Model model)
		{
			model.addAttribute("category", new Category());
			return "categoriesAdd";
		}
		@PostMapping("/admin/categories/add")
		public String postCatAdd(@ModelAttribute("category") Category category)
		{
			categoryService.addCategory(category);
			return "redirect:/admin/categories";
		}
		@GetMapping("/admin/categories/delete/{id}")
		public String deleteCategory(@PathVariable int id)
		{
			categoryService.delCategoryById(id);
			return "redirect:/admin/categories";

		}
		@GetMapping("/admin/categories/update/{id}")
		public String updateCategory(@PathVariable int id, Model model)
		{
			Optional<Category> category = categoryService.updCategoryById(id);
			if(category.isPresent())
			{
				model.addAttribute("category", category.get());
				return "categoriesAdd";
			}
			else
				return "404";

		}
		
		//product section
		@GetMapping("/admin/products")
		public String getProducts(Model model)
		{
			model.addAttribute("products", productService.getAllProducts());
			return "products";
		}
		
		@GetMapping("/admin/products/add")
		public String getAddProduct(Model model)
		{
			model.addAttribute("productDTO", new ProductDto());
			model.addAttribute("categories", categoryService.getAllCategories());
			return "productsAdd";
		}
		@PostMapping("/admin/products/add")
		public String postAddProduct(@ModelAttribute("productDTO") ProductDto productDto,
									 @RequestParam("productImage") MultipartFile file,
									 @RequestParam("imgName") String imgName) throws IOException
		{
			
			Product product = new Product();
			product.setId(productDto.getId());
			product.setName(productDto.getName());
			product.setCategory(categoryService.updCategoryById(productDto.getCategoryId()).get());
			product.setPrice(productDto.getPrice());
			product.setWeight(productDto.getWeight());
			product.setDescription(productDto.getDescription());
			String imageUUID;
			if(!file.isEmpty())
			{
				imageUUID = file.getOriginalFilename();
				Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
				Files.write(fileNameAndPath, file.getBytes());
			}
			else
			{
				imageUUID = imgName;
			}
			product.setImageName(imageUUID);
			productService.addProduct(product);
			return "redirect:/admin/products";
		}
		@GetMapping("/admin/product/delete/{id}")
		public String deleteProduct(@PathVariable int id)
		{
			productService.removeProductById(id);
			return "redirect:/admin/products";

		}
		@GetMapping("/admin/product/update/{id}")
		public String updateProduct(@PathVariable long id,Model model)
		{
			Product product = productService.getProductById(id).get();
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setName(product.getName());
			productDto.setCategoryId(product.getCategory().getId());
			productDto.setPrice(product.getPrice());
			productDto.setWeight(product.getWeight());
			productDto.setDescription(product.getDescription());
			productDto.setImageName(product.getImageName());
			
			model.addAttribute("categories",categoryService.getAllCategories());
			model.addAttribute("productDTO", productDto);
			return "productsAdd";

		}
}
