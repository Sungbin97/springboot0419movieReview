package com.test0427;

import java.util.Map;
//    2) Green Interface 설계
public interface Green {
//    추상 메서드
//2-1)두 개의 정수형 파라미터를 받아서 더한 결과 정수형을 반환하는 함수
int sum(int a, int b);
//2-2)두 개의 정수형 파라미터를 받아서 첫 번째 파라미터에서 두 번째 파라미터를 뺀 결과 정수형을 반환하는 함수
int min(int a, int b);

//2-3)두 개의 정수형 파라미터를 받아서 곱한 결과 정수형을 반환하는 함수
int mul(int a, int b);

//2-4) 하나의 정수형 변수를 받아서 구구단 결과를 문자열로 반환하는 메서드
String gugu(int a);
//2-5) Map<Integer,Car> map을 파라미터로 받아서 논리형(true/false)의 배열 형태로 반환하는 메서드
boolean[] arrMap(Map<Integer, Car> map);

//2-6) 정수형 파리미터를 받아서 Car 클래스 형태를 반환하는 메서드
Car icar(int a);

}
