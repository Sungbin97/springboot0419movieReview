package com.green.computer.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"})
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    private int grade;
    private String text;
    //Review 클래스는 Movie와 Member 양쪽에서 참조하는 구조이므로 @ManyToOne으로 설계함
    //Fetch 모드 LAZY설정을 이용하고 toString 호출시에 다른 엔티티를 사용하지 않도록 exclude 지정
}
