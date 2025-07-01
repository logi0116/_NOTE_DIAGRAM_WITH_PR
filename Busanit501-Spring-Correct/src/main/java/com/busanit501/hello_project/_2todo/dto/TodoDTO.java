package com.busanit501.hello_project._2todo.dto;

import java.time.LocalDate;

// Todo 항목의 데이터 전달 객체로 정의하라.
// [목적/이유] Todo 항목의 데이터 전달을 위한 객체. Controller-View-Model 계층 간 데이터 이동의 표준화.
// [기능/자질: 계층 분리, 재사용성, 유지보수 용이성]
// [타입 위상/변경 가능성: 클래스, DTO 패턴, 이름/구조/필드 자유롭게 변경 가능]
public class TodoDTO {
    // Todo의 고유 식별자를 저장하라.
    // [목적/이유] Todo의 고유 식별자 저장. DB의 PK와 매핑.
    // [기능/자질: 데이터 일관성, 식별성]
    // [타입 위상/변경 가능성: 필드, Long, 이름/타입 변경 가능]
    private Long tno;
    // Todo의 제목을 저장하라.
    // [목적/이유] Todo의 제목 정보 저장.
    // [기능/자질: 가독성, 사용자 편의]
    // [타입 위상/변경 가능성: 필드, String, 이름/타입/제약 변경 가능]
    private String title;
    // Todo의 마감일을 저장하라.
    // [목적/이유] Todo의 마감일 정보 저장.
    // [기능/자질: 시간 관리, 일정 추적]
    // [타입 위상/변경 가능성: 필드, LocalDate, 이름/타입 변경 가능]
    private LocalDate dueDate;
    // Todo의 완료 여부를 저장하라.
    // [목적/이유] Todo의 완료 여부 저장.
    // [기능/자질: 상태 추적, 논리적 명확성]
    // [타입 위상/변경 가능성: 필드, boolean, 이름/타입 변경 가능]
    private boolean finished;

    // **BEFORE**: 반자동으로 생성.
    // **BEFORE**: 1) 멤법 선택 후, 반자동 생성. 먼저 이것 부터. 작업.
    // **BEFORE**: 2) lombok 라이브러리, 메모리에 자동생성
    // 반복적인 getter/setter, 생성자, toString 코드를 자동 생성하라.
    // [목적/이유] 반복적인 getter/setter, 생성자, toString 코드 자동화로 생산성 및 일관성 향상.
    // [기능/자질: 간결성, 생산성, 오류 감소]
    // [타입 위상/변경 가능성: 메서드, 자동 생성/직접 작성/라이브러리 활용 모두 가능]
    // [연관/종속] Lombok(@Getter/@Setter 등) 또는 IDE 자동 생성 기능과 연계 가능

    // tno 값을 반환하는 getter를 만들어라.
    // [목적/이유] tno 값 반환.
    // [기능/자질: 캡슐화, 데이터 접근]
    // [타입 위상/변경 가능성: 메서드, getter, 이름/반환타입 변경 가능]
    public Long getTno() {
        return tno;
    }

    // tno 값을 설정하는 setter를 만들어라.
    // [목적/이유] tno 값 설정.
    // [기능/자질: 캡슐화, 데이터 수정]
    // [타입 위상/변경 가능성: 메서드, setter, 이름/파라미터 변경 가능]
    public void setTno(Long tno) {
        this.tno = tno;
    }

    // title 값을 반환하는 getter를 만들어라.
    // [목적/이유] title 값 반환.
    // [기능/자질: 캡슐화, 데이터 접근]
    // [타입 위상/변경 가능성: 메서드, getter, 이름/반환타입 변경 가능]
    public String getTitle() {
        return title;
    }

    // title 값을 설정하는 setter를 만들어라.
    // [목적/이유] title 값 설정.
    // [기능/자질: 캡슐화, 데이터 수정]
    // [타입 위상/변경 가능성: 메서드, setter, 이름/파라미터 변경 가능]
    public void setTitle(String title) {
        this.title = title;
    }

    // dueDate 값을 반환하는 getter를 만들어라.
    // [목적/이유] dueDate 값 반환.
    // [기능/자질: 캡슐화, 데이터 접근]
    // [타입 위상/변경 가능성: 메서드, getter, 이름/반환타입 변경 가능]
    public LocalDate getDueDate() {
        return dueDate;
    }

    // dueDate 값을 설정하는 setter를 만들어라.
    // [목적/이유] dueDate 값 설정.
    // [기능/자질: 캡슐화, 데이터 수정]
    // [타입 위상/변경 가능성: 메서드, setter, 이름/파라미터 변경 가능]
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // finished 값을 반환하는 getter를 만들어라.
    // [목적/이유] finished 값 반환.
    // [기능/자질: 캡슐화, 데이터 접근]
    // [타입 위상/변경 가능성: 메서드, getter, 이름/반환타입 변경 가능]
    public boolean isFinished() {
        return finished;
    }

    // finished 값을 설정하는 setter를 만들어라.
    // [목적/이유] finished 값 설정.
    // [기능/자질: 캡슐화, 데이터 수정]
    // [타입 위상/변경 가능성: 메서드, setter, 이름/파라미터 변경 가능]
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    // 객체의 모든 필드값을 문자열로 표현하는 toString 메서드를 오버라이드하라.
    // [목적/이유] 객체의 모든 필드값을 문자열로 표현.
    // [기능/자질: 디버깅, 로깅, 가독성]
    // [타입 위상/변경 가능성: 메서드, 오버라이드, 내용/형식/출력방식 변경 가능]
    @Override
    public String toString() {
        return "TodoDTO{" +
                "tno=" + tno +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}';
    }

    // 모든 필드를 초기화하는 생성자를 만들어라.
    // [목적/이유] 모든 필드를 초기화하는 생성자.
    // [기능/자질: 객체 일관성, 편의성]
    // [타입 위상/변경 가능성: 생성자, 파라미터/로직/접근제어자 변경 가능]
    public TodoDTO(Long tno, String title, LocalDate dueDate, boolean finished) {
        this.tno = tno;
        this.title = title;
        this.dueDate = dueDate;
        this.finished = finished;
    }

    // 기본 생성자를 만들어라.
    // [목적/이유] 기본 생성자.
    // [기능/자질: 프레임워크/라이브러리 호환성, 직렬화 지원]
    // [타입 위상/변경 가능성: 생성자, 내용/접근제어자 변경 가능]
    public TodoDTO() {}
}
