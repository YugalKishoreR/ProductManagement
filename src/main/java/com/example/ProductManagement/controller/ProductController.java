package com.example.ProductManagement.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProductManagement.dto.ProductDTO;
import com.example.ProductManagement.entity.SearchCriteria;
import com.example.ProductManagement.exception.ProductException;
import com.example.ProductManagement.exception.Response;
import com.example.ProductManagement.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@GetMapping
	public Response<List<ProductDTO>> getAllProducts() {
		try {
			log.debug("ProductController >> getAllProducts");
			List<ProductDTO> productDTOs = productService.getAllProducts();
			if (productDTOs.isEmpty()) {
				return new Response<>("No products found");
			}
			return new Response<>("success", "Products retrieved successfully", productDTOs);
		} catch (Exception e) {
			return new Response<>("error", e.getMessage(), null);

		}
	}
	
	

	@PostMapping("/search")
	public Response<Page<ProductDTO>> searchProducts(@RequestBody SearchCriteria searchCriteria,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		
		log.debug("ProductController >> searchProducts");
		try {
			if(searchCriteria==null) {
				throw new ProductException("searchCriteria is null");
			}
			Pageable pageable = PageRequest.of(page, size);
			Page<ProductDTO> products = productService.searchProducts(searchCriteria, pageable);
			return new Response<>("success", "Product search OK", products);
		} catch (Exception e) {
			return new Response<>("error", "Product search OK: " + e.getMessage(), null);
		}
	}

	@PostMapping
	public Response<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
		ProductDTO savedProductDTO= new ProductDTO();
		try {
	        if (productDTO.getProductName() == null || productDTO.getProductName().isEmpty()) {
	            throw new ProductException("Product name is required");
	        }
	        	      
	        if (productDTO.getPrice() <= 0) {
	            throw new ProductException("Price amount cannot be negative");
	        }       
	      
	        savedProductDTO = productService.addProduct(productDTO);
			return new Response<>("success", "Product added successfully", savedProductDTO);
		} catch (ProductException e) {
			return new Response<>("error", "Failed to add product: " + e.getMessage(), null);
		}
	}
	
	@PostMapping("/addMultiProducts")
	public Response<List<ProductDTO>> addProducts(@RequestBody List<ProductDTO> productDTOList) {
	    List<ProductDTO> savedProducts = new ArrayList<>();

	    try {
	        if (productDTOList != null && !productDTOList.isEmpty()) {
	            for (ProductDTO productDTO : productDTOList) {
	                if (productDTO.getProductName() == null || productDTO.getProductName().isEmpty()) {
	                    throw new ProductException("Product name is required for all products");
	                }

	                if (productDTO.getPrice() <= 0) {
	                    throw new ProductException("Price amount cannot be negative for product: " + productDTO.getProductName());
	                }

	                savedProducts.add(productService.addProduct(productDTO));
	            }

	            return new Response<>("success", "Products added successfully", savedProducts);
	        } else {
	            throw new ProductException("No products provided in the request");
	        }
	    } catch (ProductException e) {
	        return new Response<>("error", "Failed to add products: " + e.getMessage(), null);
	    }
	}


	@PutMapping
	public Response<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
		try {
			if(productDTO.getId()==0) {
				 throw new ProductException("Product ID is null");
			}
			ProductDTO savedProductDTO = productService.updateProduct(productDTO);
			return new Response<>("success", "Product updated successfully", savedProductDTO);
		} catch (Exception e) {
			return new Response<>("error", "Failed to update product: " + e.getMessage(), null);
		}
	}

	@DeleteMapping("/{pid}")
	public Response<Void> deleteProduct(@PathVariable int pid) {
		try {
			productService.deleteProduct(pid);
			return new Response<>("success", "Product deleted successfully", null);
		} catch (Exception e) {
			return new Response<>("error", "Failed to update product: " + e.getMessage(), null);
		}
	}

	@GetMapping("/sort")
	public Response<List<ProductDTO>> getSortedProducts(
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		try {
			List<ProductDTO> sortedProductDTO = productService.getSortedProducts(sortBy);
			return new Response<>("success", "successfully sorted", sortedProductDTO);
		} catch (Exception e) {
			return new Response<>("error", "Failed to get sorted product: " + e.getMessage(), null);
		}
	}
	
	@GetMapping("/sortdesc")
	public Response<List<ProductDTO>> getSortedProductsDesc(
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		try {
			List<ProductDTO> sortedProductDTO = productService.getSortedProductsDesc(sortBy);
			return new Response<>("success", "successfully sorted", sortedProductDTO);
		} catch (Exception e) {
			return new Response<>("error", "Failed to get sorted product: " + e.getMessage(), null);
		}
	}

}
