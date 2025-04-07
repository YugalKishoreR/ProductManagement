package com.example.ProductManagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ProductManagement.dao.ReviewDao;
import com.example.ProductManagement.dto.ProductRatingDTO;



@Service
public class ReviewService {
	
	@Autowired
	ReviewDao reviewDao;
	
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
	
	public List<ProductRatingDTO> getReviews() {
		log.debug("ReviewService-getAllProducts");
		return reviewDao.getAllReviews();
	}
	
	public ProductRatingDTO addReview(ProductRatingDTO proRatingDTO) {
		log.debug("ReviewService-addReview");
		return reviewDao.addReview(proRatingDTO);
	}
	
	public ProductRatingDTO updateProRating(ProductRatingDTO proRatingDTO) {
        return reviewDao.updateProRating(proRatingDTO);
    }
	
	public void deleteProRatingById(int proRatingId) {
		reviewDao.deleteById(proRatingId);
    }

}
