package com.test0427;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GreenImpl implements Green {
//3) Green Interface를 구현한 클래스 생성
    @Override
    public int sum(int a, int b) {
        int result = a+b;
        return result;
    }

    @Override
    public int min(int a, int b) {
        int result = a-b;
        return result;
    }

    @Override
    public int mul(int a, int b) {
        return a*b;
    }

    @Override
    public String gugu(int a) {
        String result = "";
        for (int i = 1; i <= 9; i++) {
            result = a+" X "+ i +"= "+ a*i;
            System.out.println(result);
        }
        return result;
    }
    //    Map<Integer, Car> 타입의 멤버변수 선언
    Map<Integer, Car> qq;
    Car car;
//    3-5)
//    Map<Integer,Car> map을 파라미터로 받아서 논리형(true/false)의 배열 형태로 반환하는 메서드 생성
//    기능은
//    map의 keySet 메서드를 이용하여 파라미터(map)을 key값을 순회하면서 Car 클래스 기반의 객체의 운전자이름(driver)의 문자열의 길이가 5보다 크면
//    boolean 타입의 배열에 true를 저장하고 그렇지 못하면 false를 저장하고 그렇게 저장된 배열을 반환하는 메서드
    @Override
    public boolean[] arrMap(Map<Integer, Car> map) {
        boolean bool[] = new boolean[6];
        for (int i = 0; i < map.size(); i++) {
           if(map.get(i).getDriver().length()>5){
               bool[i] = true;
           }else bool[i] = false;
        }
        return bool;
    }
// 3-6) 정수형 파리미터를 받아서 Car 클래스 형태를 반환하는 메서드 생성
//    멤버변수를 이용하여 파리미터로 받은 key값에 해당하는 value(Car객체)를 반환함
    @Override
    public Car icar(int a) {
        Car nc = new Car();
        return nc;
    }

    public static void main(String[] args) {
//        4)main 함수를 포함하는 클래스에서 아래객체 생성
//        Car[] cars = new Car[6];
//        String[] drivers = {"홍길동3" ,"홍말자다","홍말자입니다.","그린","그린컴퓨터","그린컴퓨터분당"};
//        Map<Integer, Car> map = new HashMap<>();
        Car[] cars = new Car[6];
        String[] drivers = {"hongildong3" ,"hongmalza","hongmalza is","green","green computer ","green computer bundang"};
        Map<Integer, Car> map = new HashMap<>();

//        5)cars의 Map에 데이터를 저장(key:정수,value:Car객체) 함
        for (int i = 0; i < drivers.length; i++) {
            Car car = new Car();
            car.setDriver(drivers[i]);
            cars[i] = car;
            map.put(i, cars[i]);
        }
        for(Car i : cars) System.out.println("5: " + i);
        System.out.println("-------------------------------");
        for(int key: map.keySet()) System.out.println(key);

//        6) Green을 구현한 객체 생성하고 Green객체의 메서드 호출함
       GreenImpl green = new GreenImpl();
//        6-1) sum, minus, mul메서드,구구단 메서드 호출하고 그 결과를 출력함
//        sum 메서드 호출시 drivers의 0번째  문자열의 길이를 첫 번째 파라미터로 , 1번째  문자열의 길이를 두 번째 파라미터로 전달
        System.out.println("sum: "+green.sum(drivers[0].length(), drivers[1].length()));
//        minus 메서드 호출시 drivers의 2번째  문자열의 길이를 첫 번째 파라미터로 , 3번째  문자열의 길이를 두 번째 파라미터로 전달
        System.out.println("min: "+green.min(drivers[2].length(), drivers[3].length()));
//        mul 메서드 호출시 drivers의 4번째  문자열의 길이를 첫 번째 파라미터로 , 5번째  문자열의 길이를 두 번째 파라미터로 전달
        System.out.println("mul: "+green.mul(drivers[4].length(), drivers[5].length()));
//        구구단 메서드 호출시에는 drivers 배열의 문자열의 총 길이를 다 더한 길이를 배열의 길이로 나눈 값을 구구단 메서드의 파라미터로 전달함
        int f = 0;
        for (int i = 0; i < drivers.length; i++) {
            f += drivers[i].length();
            f = f % drivers.length;
        }
        System.out.println("gugu: "+green.gugu(f));
//        7) 위에서 생성한 map(객체)을 Map<Integer,Car> map을 파라미터로 받아서 논리형(true/false)의 배열 형태로 반환하는 메서드에 전달하고
//        반환되는 값(true/false의 배열)을 받아서 true인 값이 몇 개 인지 출력하세요
        int fCnt=0;
        int tCnt=0;
        boolean result[] = new boolean[6];
         result = green.arrMap(map);
        for (int i = 0; i < result.length; i++) {
            if(result[i]==true) tCnt++;
            else fCnt++;
        }
        System.out.println("tCnt: "+tCnt+ " fCnt: " + fCnt);
//        8)정수형 파리미터를 받아서 Car 클래스 형태를 반환하는 메서드에 key값을 전달하여 반복문을 이용하여 Car클래스 객체의 driver를 출력하세요
        for(int key: map.keySet()){
            Car rr = new Car();
            rr.setDriver(String.valueOf(map.get(key)));
            System.out.println(rr.getDriver());
        }
    }
}
