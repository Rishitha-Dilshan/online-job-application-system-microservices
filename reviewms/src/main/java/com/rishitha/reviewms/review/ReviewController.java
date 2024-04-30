package com.rishitha.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllreviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReviews(@RequestParam Long companyId,
                                             @RequestBody Review review){

        boolean isReviewedSaved = reviewService.addReview(companyId,review);
        if(isReviewedSaved)
            return new ResponseEntity<>("Review Added Successfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Review Not Saved",HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review){

        boolean isreviewUpdated = reviewService.updateReview(reviewId,review);
        if(isreviewUpdated)
            return new ResponseEntity<>("Review Updated successfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Review not Updated",HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId
                                               ){
        boolean isreviewDeleted = reviewService.deleteReview(reviewId);
        if(isreviewDeleted)
            return new ResponseEntity<>("Review Deleted successfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Review not Deleted",HttpStatus.NOT_FOUND);
    }


}
