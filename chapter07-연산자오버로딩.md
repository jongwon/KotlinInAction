# 7. 연산자 오버로딩과 기타 관례 

* 자바에
  * for ... in 루프 연산은 Iterator 를 구현한 객체만 적용할 수 있다.
  * try with resource 구문은 AutoCloseable 을 구현한 객체에서만 사용할 수 있다.
  * 자바는 클래스 타입과 문법을 연결시켰다.
* 코틀린은
  * plus 함수를 구현해 + 연산자로 쓸 수 있다.
  * 정해진 이름의 함수와 언어의 특정 기능을 연결해 주는 것을 관례(convention)라 부른다.
  * 코틀린은 관례와 문법을 연결시켰다.

## 1. 산술 연산자

* 관례로 사용할 수 있는 가장 단순한 예는 산술 연산자이다.

#### 이항 산술 연산 오버로딩

* 두 점을 더하거나 빼는 연산자 구현
  ```kotlin
  data class 점(val x:Int, val y:Int) {
      operator fun plus(이동:점) = 점(x+이동.x, y+이동.y)
  }

  fun main(){
      val 가 = 점(10, 20)
      val 나 = 점(30, 40)
      println(가 + 나)
  }
  ```
  ```
  점(x=40, y=60)
  ```
* 연산자를 오버로딩하는 함수 앞에는 반드시 operator 라는 키워드를 붙여주어야 한다. (안붙이면 + 연산자를 정의하지 않았기 때문에 + 에서 문법 오류가 발생한다.)
* 관례라는 말에서 알 수 있듯, 연산자에 대해 오버로딩할 연산자 이름이 다음과 같이 미리 정해져 있다.

  | 식  | 함수 이름            |
  | --- | -------------------- |
  | a*b | times                |
  | a/b | div                  |
  | a%b | mod (1.1 부터는 rem) |
  | a+b | plus                 |
  | a-b | minus                |

* 연산자 우선 순위는 표준 숫자 타입에 대한 연산자 우선 순위와 같다.
* *, /, % 연산자가 우선순위가 높고, + - 는 우선순위가 낮다.
* 연산자 함수의 반환 타입이 꼭 두 연산자 중 하나와 일치해야 하는 것도 아니다.
  ```kotlin
  operator fun Char.times(count: Int): String {
      return toString().repeat(count)
  }
  ```


#### 복합 대입 연산자 오버로딩

* plus를 정의하면 + 뿐만 아니라 += 도 자동으로 지원한다.
* += 이나 -= 을 복합대입 연산자라고 부른다.

#### 단항 연산자 오버로딩



## 2. 비교 연산자 

#### 동등성 연산자: equals


#### 순서 연산자: compareTo



## 3. 컬렉션과 범위에 대해 쓸 수 있는 관례

#### 인덱스로 원소에 접근: get과 set
#### in 관례 
#### rangeTo 관례 
#### for 루프를 위한 iterator 관례 


## 4. 구조 분해 선언과 component 함수

#### 구조 분해 선언과 루프 


## 5. 프로퍼티 접근자 로직 재활용 : 위임 프로퍼티 

#### 위임 프로퍼티 소개
#### 위임 프로퍼티 사용: by lazy()를 사용한 프로퍼티 초기화 지연
#### 위임 프로퍼티 구현
#### 위임 프로퍼티 컴파일 규칙
#### 프로퍼티 값을 맵에 저장
#### 프레임워크에서 위임 프로퍼티 활용

