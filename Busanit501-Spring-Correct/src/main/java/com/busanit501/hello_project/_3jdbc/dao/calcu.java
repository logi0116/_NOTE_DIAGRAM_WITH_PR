package com.busanit501.hello_project._3jdbc.dao;
// 정적인 메인 메서드를 만들고 age가 18 이상이고 hasID가 참인지 확인해 출력해.
public class calcu {
    public static void main(String[] args) {
        int age = 20;
        boolean hasID = true;

        if (age >= 18 && hasID) {
            System.out.println("성인이며 신분증이 있습니다.");
        } else {
            System.out.println("조건을 만족하지 않습니다.");
        }
    }



//변수 a를 참조형으로 하는 메서드 dool은 변수 a의 값을 2배로 만들어 출력하는 메서드이다.

    public static void dool(int a) {
        a = a * 2;
        System.out.println(a);
    }



// 사용자가 티켓이 없다면 경고메시지를 출력한다.
public static void main(String[] args) {
    boolean hasTicket = false;
    if (!hasTicket) {
        System.out.println("티켓이 없습니다.");
    }
}

//hastTicket 이 true일 때만 입장시킨다.
public static void main(String[] args) {
    boolean hasTicket = true;
    if (hasTicket) {
        System.out.println("입장하세요.");
    }
}












}