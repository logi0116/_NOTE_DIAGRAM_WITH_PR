# Java 기본 문법과 변수의 이해 - 마인드맵

## 📚 Java 기본 문법과 변수

### 🏗️ 1. Java 프로그램 구조
- **클래스 (Class)**
  - 프로그램의 기본 단위
  - `public class 클래스명 { }`
  - 파일명과 클래스명 일치 필요

- **메서드 (Method)**
  - 기능을 수행하는 코드 블록
  - `public static void main(String[] args) { }`
  - 프로그램의 시작점

- **패키지 (Package)**
  - 관련 클래스들을 그룹화
  - `package com.example;`

### 🔤 2. 기본 문법 요소

#### 📝 주석 (Comments)
- **한 줄 주석**: `// 주석 내용`
- **여러 줄 주석**: `/* 주석 내용 */`
- **문서화 주석**: `/** 주석 내용 */`

#### 🔢 리터럴 (Literals)
- **정수 리터럴**: `42`, `100L`
- **실수 리터럴**: `3.14`, `2.5f`
- **문자 리터럴**: `'A'`, `'가'`
- **문자열 리터럴**: `"Hello World"`
- **불린 리터럴**: `true`, `false`
- **null 리터럴**: `null`

#### 🔧 연산자 (Operators)
- **산술 연산자**: `+`, `-`, `*`, `/`, `%`
- **비교 연산자**: `==`, `!=`, `>`, `<`, `>=`, `<=`
- **논리 연산자**: `&&`, `||`, `!`
- **대입 연산자**: `=`, `+=`, `-=`, `*=`, `/=`
- **증감 연산자**: `++`, `--`

### 📦 3. 변수 (Variables)

#### 🏷️ 변수 선언과 초기화
```java
// 선언
int number;

// 선언과 동시에 초기화
int number = 10;

// 여러 변수 동시 선언
int a, b, c;

// 여러 변수 동시 초기화
int a = 1, b = 2, c = 3;
```

#### 📊 기본 데이터 타입 (Primitive Types)

##### 🔢 정수형
- **byte**: 1바이트 (-128 ~ 127)
- **short**: 2바이트 (-32,768 ~ 32,767)
- **int**: 4바이트 (-2^31 ~ 2^31-1) ⭐ **기본형**
- **long**: 8바이트 (-2^63 ~ 2^63-1)

##### 🔢 실수형
- **float**: 4바이트 (소수점 6자리)
- **double**: 8바이트 (소수점 15자리) ⭐ **기본형**

##### 🔤 문자형
- **char**: 2바이트 (유니코드 문자)

##### ✅ 불린형
- **boolean**: 1바이트 (true/false)

#### 🏗️ 참조 데이터 타입 (Reference Types)
- **클래스**: `String`, `Scanner`, 사용자 정의 클래스
- **배열**: `int[]`, `String[]`
- **인터페이스**
- **열거형 (enum)**

### 🎯 4. 변수 사용 규칙

#### 📋 명명 규칙 (Naming Convention)
- **클래스명**: PascalCase (`MyClass`)
- **변수명/메서드명**: camelCase (`myVariable`)
- **상수명**: UPPER_SNAKE_CASE (`MAX_VALUE`)
- **패키지명**: 소문자 (`com.example`)

#### ⚠️ 제약사항
- 예약어 사용 불가 (`int`, `class`, `public` 등)
- 숫자로 시작 불가
- 특수문자 사용 제한 (`_`, `$`만 허용)
- 공백 사용 불가

### 🔄 5. 변수 스코프 (Scope)

#### 📍 지역 변수 (Local Variables)
- 메서드 내부에서 선언
- 해당 메서드 내에서만 사용 가능
- 자동 초기화되지 않음

#### 📍 인스턴스 변수 (Instance Variables)
- 클래스 내부, 메서드 외부에서 선언
- 객체 생성 시 메모리 할당
- 자동 초기화됨 (기본값)

#### 📍 클래스 변수 (Class Variables)
- `static` 키워드 사용
- 클래스 로딩 시 메모리 할당
- 모든 객체가 공유

### 💡 6. 타입 변환 (Type Conversion)

#### 🔄 자동 형변환 (Implicit Casting)
```java
int num = 10;
double dNum = num; // int → double 자동 변환
```

#### 🔄 강제 형변환 (Explicit Casting)
```java
double dNum = 10.5;
int num = (int) dNum; // double → int 강제 변환
```

### 🎨 7. 실습 예제

#### 📝 기본 변수 사용
```java
public class VariableExample {
    public static void main(String[] args) {
        // 정수형 변수
        int age = 25;
        long population = 7800000000L;
        
        // 실수형 변수
        double height = 175.5;
        float weight = 70.2f;
        
        // 문자형 변수
        char grade = 'A';
        
        // 불린형 변수
        boolean isStudent = true;
        
        // 문자열 변수 (참조형)
        String name = "홍길동";
        
        // 출력
        System.out.println("이름: " + name);
        System.out.println("나이: " + age);
        System.out.println("키: " + height + "cm");
    }
}
```

### 🎯 8. 핵심 포인트

#### ✅ 기억해야 할 것들
- 변수는 데이터를 저장하는 메모리 공간
- 데이터 타입에 따라 메모리 크기와 표현 범위가 다름
- 변수명은 의미있게 지어야 함
- 스코프에 따라 변수의 생명주기가 결정됨
- 형변환 시 데이터 손실 주의

#### ❌ 주의사항
- 초기화하지 않은 지역 변수 사용 금지
- 데이터 타입 범위 초과 주의
- 참조형 변수는 `null` 가능
- 문자열 비교 시 `==` 대신 `.equals()` 사용

---

*이 마인드맵은 Java의 기본 문법과 변수 개념을 체계적으로 정리한 것입니다.* 