# 3. 함수 정의와 호출

## 1. 컬렉션 만들기

* 코틀린은 자바의 컬렉션 라이브러리를 그대로 가져와서 쓴다. 자바와의 호환성을 좋게 하기 위해서다.
* 대신 확장 함수를 추가해서 더 편리하게 바꿨다. 이것이 어떻게 가능하게 되었는지를 3장에서 설명한다.
  ```kotlin
  println(hashSetOf(1, 2, 3).javaClass)
  println(arrayListOf(1,2,3).javaClass)
  println(hashMapOf(1 to "One", 2 to "two").javaClass)
  ```
  ```
  class java.util.HashSet
  class java.util.ArrayList
  class java.util.HashMap
  ```
* 더 편리해진 확장 함수들
  ```kotlin
    println(listOf("1", "2", "3").last())
    println(setOf(1, 21, 2, 88).max())
  ```
## 2. 함수를 호출하기 쉽게 만들기 

* 문제 : 컬렉션 클래스의 기본 toString() 을 바꿔서 출력하려면 어떻게 할까?
* 함수 구현 
  ```kotlin
  fun <T> 문자열_연결하기 (
          컬렉션: Collection<T>,
          구분자: String,
          접두사: String,
          접미사: String
  ): String {
      val 결과값 = StringBuilder(접두사);
      for((순번, 값) in 컬렉션.withIndex()){
          if(순번>0) 결과값.append(구분자)
          결과값.append(값)
      }
      결과값.append(접미사)
      return 결과값.toString()
  }

  println(문자열_연결하기(arrayListOf(1,2,3), ";", "(", ")"))
  ```
* 이 함수를 더 간단하게 바꿔보자...

#### 이름 붙인 인자 
* 파라미터가 여러개라 햇갈릴 수 있다.
* 코틀린 방식 
  ```kotlin
  println(문자열_연결하기(arrayListOf(1,2,3),
            구분자=";",
            접두사="(",
            접미사=")"))
  ```

#### 디플트 파라미터 값
* 자바에서 같은 이름의 함수를 여러개 만들어야 하는 폐해(method overloading)가 있었다.
* 코틀린은 이름붙인 인자에 디폴트 값을 주어 메서드를 여러개 만드는 수고를 줄였다.

  ``` kotlin
  fun <T> 문자열_연결하기 (
          컬렉션: Collection<T>,
          구분자: String = ",",
          접두사: String = "[",
          접미사: String = "]"
  ): String {
      val 결과값 = StringBuilder(접두사);
      for((순번, 값) in 컬렉션.withIndex()){
          if(순번>0) 결과값.append(구분자)
          결과값.append(값)
      }
      결과값.append(접미사)
      return 결과값.toString()
  }
  // 호출
  println(문자열_연결하기(arrayListOf(1,2,3), "~~"))
  println(문자열_연결하기(arrayListOf(1,2,3), 구분자="~~"))
  println(문자열_연결하기(arrayListOf(1,2,3), 접두사="(", 접미사=")"))
  ```

#### 정적인 유틸리티 클래스 없애기: 최상위 함수와 Property

* 함수를 최상위 함수로 선언할 수 있다. 그냥 클래스와 동등한 수준으로 선언하고 import 해서 쓰면 된다.
* 만약 Java에서 이 함수를 호출해야 한다면 파일이름Kt.메서드() 로 호출한다.
  ```kotlin
  package strings;

  public class JoinKt {
    public static String 문자열_연결하기(Collection<T> 컬렉션){}
    public static String 문자열_연결하기(Collection<T> 컬렉션, String 구분자){}
    public static String 문자열_연결하기(Collection<T> 컬렉션, String 구분자, String 접두사){}
    public static String 문자열_연결하기(Collection<T> 컬렉션, String 구분자, String 접두사, String 접미사){}
    ...
  }
  ```
* 함수 뿐만 아니라 property 도 최상위에 지정해 둘 수 있다. 이런 값은 static 영역에 저장된다.
  

## 3. 메소드를 다른 메소드에 추가하기 : 확장 함수와 확장 property

* 자바와 호환성을 유지하려면 기존 클래스를 그대로 사용하면서 해당 클래스에 확장 함수와 확장 Property를 추가해서 사용할 수 있다면 좋을 것 같다.
* 확장 함수 : 어떤 클래스의 멤버 메서드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수

```kotlin
  package strings

  fun String.lastChar():Char = this.get(this.length -1)
  fun String.lastChar():Char = get(length -1)
```
* 함수 앞에 클래스 이름을 붙여서 선언하면 된다.
* this는 수신 객체(receiver object) 를 가리킨다.
* this는 생략이 가능하다.
* 그렇지만, 비공개 메서드나 멤버는 접근할 수 없다.
* 호출하는 쪽에서는 확장함수인지 여부를 구분할 수 없다. 그리고 그게 중요하지도 않다.

#### import와 확장 함수

* 확장함수를 허용하면 확장함수의 충돌이 발생할 수 있다.
* 확장함수를 사용하는 곳에서는 반드시 그 함수를 import 하고 써야 한다.
* as 키워드를 이용해 alias 이름으로 부를 수 있다. 이름이 같은 확장함수의 충돌을 회피하기 위함이다.

#### 자바에서 확장함수 호출하기

* 내부적으로 확장 함수는 수신 객체를 첫번째 인자로 갖는 static 메서드이다.
* java 에서 확장함수를 사용하려면 static 메서드를 찾아서 호출하되, 첫번째 인자에 대상이 되는 수신 객체를 넘기기만 하면 된다.


#### 확장 함수로 유틸리티 함수 정의

* 컬렉션에 문자열 연결하기 메서드를 붙이기
  ```kotlin
  fun <T> Collection<T>.문자열_연결하기 (
          구분자: String = ",",
          접두사: String = "[",
          접미사: String = "]"
  ): String {
      val 결과값 = StringBuilder(접두사);
      for((순번, 값) in withIndex()){
          if(순번>0) 결과값.append(구분자)
          결과값.append(값)
      }
      결과값.append(접미사)
      return 결과값.toString()
  }
  ```
  * 첫번째 Collection<T> 인자는 this 로 바인딩 되었고, 생략 가능하다.
  * 확장 함수는 static 함수 호출을 쉽고 편하게 해주는 편의 기능일 뿐이다.
  * 특별히 String 에 대해서는 문자열_합치기() 메서드를 조인() 으로 호출하고 싶다면 
    ```kotlin
    fun Collection<String>.조인(
            구분자: String=",",
            접두사: String = "",
            접미사: String = ""
    ) = 문자열_연결하기(구분자, 접두사, 접미사)
    ```
  * String 타입의 리스트에서만 호출할 수 있다.
    ```kotlin
      println(listOf("1", "2", "3").조인()); // 성공
      println(listOf(1, 2, 3).조인()); // 컴파일 오류 
    ```

#### 확장 함수는 override 할 수 없다.

* 코틀린은 호출될 확장 함수를 정적으로 결정한다.
* 따라서 변수 선언시 결정된 객체가 상위 객체라면 상위객체의 정적 메서드가 호출이 된다.
   (선언시 구지 타입을 명시하지 않으면 동적으로 결정이 되기 때문에 ... 의도한 대로 호출 할 수 있지 않을까?...)


#### 확장 property

* 확장 property 도 클래스객체에 선언하면 된다.
  ```kotlin
  val String.lastChar: Char 
    get() = get(length-1)
  ```
  * 이 경우 get 은 반드시 정의를 해야 한다.
  ```kotlin
  var StringBuilder.lastChar: Char
    get() = get(length-1)
    set(value:Char) {
      this.setCharAt(length-1, value)
    }

  // 사용법
  val sb = StringBuilder("코틀린?")
  sb.lastChar='!'
  print(sb) // 코틀린!
  ```
  

## 4. 컬렉션 처리 : 가변 길이 인자, 중위 함수 호출, 라이브러리 지원

#### 자바 Collection API 확장

#### 가변인자 함수 

#### 값의 쌍 다루기: 중위 호출과 구조 분해 선언


## 5. 문자열과 정규식 다루기

#### 문자열 다루기

#### 정규식과 3중 따옴표 문자열

#### 멀티라인 3중 따옴표 문자열 


## 6. 코드 다듬기 : 로컬 함수와 확장

