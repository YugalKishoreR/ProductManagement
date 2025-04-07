package com.example.ProductManagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ProductManagement.dao.ProductDao;
import com.example.ProductManagement.dto.ProductDTO;
import com.example.ProductManagement.entity.SearchCriteria;
import com.example.ProductManagement.exception.ProductException;

@Service
public class ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	public ProductDao productDao;

	public List<ProductDTO> getAllProducts() {
		log.debug("ProductService-getAllProducts");
		return productDao.viewAllProducts();
	}
	
	

	public Page<ProductDTO> searchProducts(SearchCriteria searchCriteria, Pageable pageable) {
		return productDao.searchProducts(searchCriteria, pageable);
	}

	public ProductDTO addProduct(ProductDTO productDTO) throws ProductException {
		log.debug("ProductService-addProduct");
		return productDao.addProduct(productDTO);
	}

	public ProductDTO updateProduct(ProductDTO productDTO) {
		log.debug("ProductService-updateProduct");
		return productDao.updateProduct(productDTO);
	}

	public void deleteProduct(int pid) {
		log.debug("ProductService-deleteProduct");
		productDao.deleteProduct(pid);
	}

	public List<ProductDTO> getSortedProducts(String sortBy) {
		log.debug("ProductService-getSortedProducts");
		return productDao.getSortedProducts(sortBy);

	}
	
	public List<ProductDTO> getSortedProductsDesc(String sortBy) {
		log.debug("ProductService-getSortedProductsDesc");
		return productDao.getSortedProductsDesc(sortBy);

	}

}
