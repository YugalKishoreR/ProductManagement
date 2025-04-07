package com.example.ProductManagement.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.ProductManagement.dto.ProductDTO;
import com.example.ProductManagement.dto.ProductRatingDTO;
import com.example.ProductManagement.entity.Criteria;
import com.example.ProductManagement.entity.Product;
import com.example.ProductManagement.entity.ProductRating;
import com.example.ProductManagement.entity.SearchCriteria;
import com.example.ProductManagement.exception.ProductException;
import com.example.ProductManagement.repository.ProductRepository;
import com.example.ProductManagement.repository.ReviewRepository;

import jakarta.persistence.criteria.Predicate;

@Repository
public class ProductDao {

	private static final Logger log = LoggerFactory.getLogger(ProductDao.class);
	
	@Autowired
	ProductRepository productRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ReviewRepository reviewRepository;

	public List<ProductDTO> viewAllProducts() {
		log.debug("ProductDao-viewAllProducts");
		List<Product> products = productRepo.findAll();
		List<ProductDTO> productDTOs = new ArrayList<>();
		for (Product product : products) {
			List<ProductRating> ratings = product.getRatings();
			double averageRating = calculateAverageRating(product);
			ProductDTO productDTO = mapToProductDTO(product);
			List<ProductRatingDTO> ratingDTOs = mapToRatingDTOs(ratings);
			productDTO.setAverageRating(averageRating);
			productDTO.setRatings(ratingDTOs);
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	public Page<ProductDTO> searchProducts(SearchCriteria searchCriteria, Pageable pageable) {
		try {
			Page<Product> products = productRepo.findAll((root, query, criteriaBuilder) -> {
				List<Predicate> predicates = new ArrayList<>();
				for (Criteria criteria : searchCriteria.getSearchCriteriaList()) {
					if ("=".equals(criteria.getOperation())) {
						predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
					} else if ("<".equals(criteria.getOperation())) {
						predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()),
								(Comparable) criteria.getValue()));
					}
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}, pageable);
			return products.map(this::mapToProductDTO);
		} catch (

		Exception e) {
			throw new RuntimeException("Error searching products: " + e.getMessage(), e);
		}
	}

	public ProductDTO addProduct(ProductDTO productDTO) throws ProductException {

		log.debug("ProductDao-addProduct");
		Product product = mapToProduct(productDTO);
		Product savedProduct = productRepo.save(product);
		ProductDTO savedProductDTO = mapToProductDTO(savedProduct);
		return savedProductDTO;

	}

	public ProductDTO updateProduct(ProductDTO productDTO) {
		ProductDTO updatedProductDTO = new ProductDTO();
		Optional<Product> optionalProduct = productRepo.findById(productDTO.getId());
		if (optionalProduct.isPresent()) {
			Product existingProduct = optionalProduct.get();
			existingProduct.setProductName(productDTO.getProductName());
			existingProduct.setBrand(productDTO.getBrand());
			existingProduct.setCategory(productDTO.getCategory());
			existingProduct.setPrice(productDTO.getPrice());
			// existingProduct.setRating(productDTO.getRating());
			Product updatedProduct = productRepo.save(existingProduct);
			updatedProductDTO = mapToProductDTO(updatedProduct);
		}
		return updatedProductDTO;
	}

	public void deleteProduct(int pid) {
		log.debug("ProductDao-addProduct");
		if (productRepo.existsById(pid)) {
			productRepo.deleteById(pid);
		}

	}

	public List<ProductDTO> getSortedProducts(String sortBy) {
		log.debug("ProductDao-getSortedProducts");
		List<Product> sortedProducts;
		switch (sortBy.toLowerCase()) {
		case "id":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "pid"));
			break;
		case "name":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "productName"));
			break;
		case "brand":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "brand"));
			break;
		case "category":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "category"));
			break;
		case "price":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "price"));
			break;
		case "rating":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "averageRating"));
			break;
		default:
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.ASC, "pid"));
		}
		return sortedProducts.stream().map(this::mapToProductDTO).collect(Collectors.toList());
	}
	
	public List<ProductDTO> getSortedProductsDesc(String sortBy) {
		log.debug("ProductDao-getSortedProducts");
		List<Product> sortedProducts;
		switch (sortBy.toLowerCase()) {
		case "id":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "pid"));
			break;
		case "name":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "productName"));
			break;
		case "brand":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "brand"));
			break;
		case "category":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "category"));
			break;
		case "price":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "price"));
			break;
		case "rating":
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "averageRating"));
			break;
		default:
			sortedProducts = productRepo.findAll(Sort.by(Sort.Direction.DESC, "pid"));
		}
		return sortedProducts.stream().map(this::mapToProductDTO).collect(Collectors.toList());
	}

	private Product mapToProduct(ProductDTO productDTO) {
		return modelMapper.map(productDTO, Product.class);
	}

	private ProductDTO mapToProductDTO(Product product) {

		ProductDTO dto = new ProductDTO();
		dto.setId(product.getPid());
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setBrand(product.getBrand());
		dto.setCategory(product.getCategory());
		dto.setPrice(product.getPrice());
		dto.setAverageRating(product.getAverageRating()); // Set average rating
		return dto;
	}

	private double calculateAverageRating(Product product) {
		List<ProductRating> ratings = product.getRatings();
		if (ratings.isEmpty()) {
			return 0.0;
		}
		double totalRating = 0.0;
		for (ProductRating rating : ratings) {
			totalRating += rating.getRating();
		}
		return totalRating / ratings.size();
	}
	
	private List<ProductRatingDTO> mapToRatingDTOs(List<ProductRating> ratings) {
        List<ProductRatingDTO> ratingDTOs = new ArrayList<>();
        for (ProductRating rating : ratings) {
            ProductRatingDTO ratingDTO = new ProductRatingDTO();
            ratingDTO.setId(rating.getId());
            ratingDTO.setPid(rating.getProduct().getPid());
            ratingDTO.setRating(rating.getRating());
            ratingDTOs.add(ratingDTO);
        }
        return ratingDTOs;
    }
}
