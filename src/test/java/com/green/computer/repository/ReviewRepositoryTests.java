package com.green.computer.repository;

import com.green.computer.entity.Member;
import com.green.computer.entity.Movie;
import com.green.computer.entity.MovieImage;
import com.green.computer.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMoviesReview(){
        //200개의 리뷰 등록
        IntStream.rangeClosed(1,200).forEach(i->{
            //영화 번호
            Long mno = (long)(Math.random()*100) +1;
            //리뷰어 번호
            Long mid = ((long)(Math.random()*100)+1);

            Member member = Member.builder()
                    .mid(mid)
                    .build();

            Review review = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("이 영화에 대한 느낌..."+i)
                    .build();
            reviewRepository.save(review);
        });
    }
    @Test
    public void testGetMovieReviews(){
        Movie movie = Movie.builder()
                .mno(92L)
                .build();

        List<Review> result = reviewRepository.findByMovie(movie);
        result.forEach(i->{
            System.out.println(i.getReviewnum());
            System.out.println("\t"+i.getGrade());
            System.out.println("\t"+i.getText());
            System.out.println("\t멤버"+i.getMember()); //.getEmail()
            System.out.println("--------------------------");
        });
    }
    
    @Test
    public void testShare(){
        System.out.println("수정 잘되는가?"); 
    }
}
