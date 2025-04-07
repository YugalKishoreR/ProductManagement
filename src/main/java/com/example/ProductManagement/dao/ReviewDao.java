package com.example.ProductManagement.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.ProductManagement.dto.ProductRatingDTO;
import com.example.ProductManagement.entity.Product;
import com.example.ProductManagement.entity.ProductRating;
import com.example.ProductManagement.exception.ProductException;
import com.example.ProductManagement.repository.ProductRepository;
import com.example.ProductManagement.repository.ReviewRepository;

@Repository
public class ReviewDao {

	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	ProductRepository productRepo;

	private static final Logger log = LoggerFactory.getLogger(ReviewDao.class);
	
	public List<ProductRatingDTO> getAllReviews() {
		log.debug("ProRatingDTO-getAllReviews");
		List<ProductRating> review = reviewRepo.findAll();
		List<ProductRatingDTO> productDTOs = review.stream().map(this::mapToProRatingDTO).collect(Collectors.toList());
		return productDTOs;
	}
	
	public ProductRatingDTO addReview(ProductRatingDTO proRatingDTO) throws ProductException {
	    // Convert DTO to entity
	    ProductRating proRating = mapToProRating(proRatingDTO);
	    // Save the entity
	    ProductRating savedProRating = reviewRepo.save(proRating);
	    // Convert saved entity back to DTO and return
	    return mapToProRatingDTO(savedProRating);
	}

	public ProductRatingDTO updateProRating(ProductRatingDTO proRatingDTO) {
		ProductRating proRating = mapToProRating(proRatingDTO);
		ProductRating updatedProRating = reviewRepo.save(proRating);
		ProductRatingDTO updatedProRatingDTO = mapToProRatingDTO(updatedProRating);
		return updatedProRatingDTO;
	}

	public void deleteById(int proRatingId) {
		reviewRepo.deleteById(proRatingId);
	}

	private ProductRatingDTO mapToProRatingDTO(ProductRating review) {
		ProductRatingDTO proRatingDTO = new ProductRatingDTO();
		proRatingDTO.setId(review.getId());
		proRatingDTO.setPid(review.getProduct().getPid());
		proRatingDTO.setRating(review.getRating());
		return proRatingDTO;
	}
	
	private ProductRating mapToProRating(ProductRatingDTO proRatingDTO) {
		log.debug("proRatingDTO----", proRatingDTO);
		ProductRating proRating = new ProductRating();
		proRating.setId(proRatingDTO.getId());
		Product product = new Product();
		product.setPid(proRatingDTO.getPid());
		proRating.setProduct(product);
		proRating.setRating(proRatingDTO.getRating());
		return proRating;
	}
}
