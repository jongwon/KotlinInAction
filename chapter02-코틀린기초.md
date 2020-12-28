# 2. 코틀린 기초

## 1. 기본 요소 : 함수와 변수

### 함수

- 함수를 선언할 때는 fun 키워드를 쓴다.
- 함수는 최상위 수준에서 정의할 수 있다. 클래스 안에서만 쓰이는 것이 아니다.
- 배열도 일반적인 클래스이다.
- System.out.println 대신 println 을 쓴다. 코틀린 표준 라이브러리는 간결한 시스템 함수들을 기존 시스템 메소드에 대한 wrapper로 제공한다.
- 끝에 ; 을 붙이지 않아도 된다.

### 문장과 식

- 코틀린에서 if 는 문장이 아닌 식이다. 식은 결과를 리턴하지만 문은 리턴하지 않는다. 코틀린에서는 루프문을 제외한 대부분의 제어구조가 식이다.

### 식이 본문인 함수

- 하나의 제어문으로 구성된 함수는 return 을 생략할 수 있다.

```
fun max(a:Int, b:Int): Int = if(a>b) a else b
```

- 본문이 중괄호로 둘러싸인 함수를 블록이 본문인 함수라고 부른다.
- 식이 본문인 함수의 경우 타입추론이 가능하기 때문에 리턴타입을 적어주지 않아도 된다.
- 블록이 본문인 함수는 반드시 리턴타입을 명시해주어야 한다. return 을 한 곳 이상에서 할 수 있기 때문이다.

### 변수

- val (value) : 변경 불가능한 참조
- var (variable) : 변경 가능한 참조

## 2. 클래스와 Property

### 프로퍼티

- 자바의 필드와 접근자를 묶어서 프로퍼티라고 한다.
- class Person(val name:String, var isMarried: Boolean)
  - val : 불변 프로퍼티
  - var : 변경 가능 프로퍼티
  - 기본 접근자는 public 이다. person.isMarried = false
  - getter / setter 기본제공
  -

### 커스텀 접근자

- 값에 따라 동적으로 계산해서 줘야 하는 프로퍼티는 그 뒤에 바로 get() 을 선언한다.
  ```
  class Rectangle(val height:Int, val width:Int){
      val isSquare: Boolean
          get() {
              return height === width
          }
  }
  ```

### 디렉터리와 패키지

- java 에서 처럼, package 와 import 를 그대로 쓴다.
- 패키지 구조와 디렉토리 구조는 전혀 관계가 없다. (관습적으로 일치시켜주는 것 뿐이다.)
- 여러 클래스를 한 파일 안에 두어도 좋다.

## 3. enum과 when : 선택표현 처리

#### enum 클래스 정의

```kotlin
enum class 색(val r:Int, val g:Int, val b:Int){
    빨강색 (255,0,0),
    주황색 (255,165,0),
    노랑색 (255,255,0),
    초록색 (0,255,0),
    파랑색 (0, 0,255),
    남색 (75,0,130),
    보라색 (238,130,238);

    fun rgb() = (r*256+g)*256+b;
}
```

- enum class 가 지정된 예약어이다. class 앞이 아니라면 enum은 변수 명으로도 사용 가능하다. (소프트 키워드)
- 코틀린에서 enum 은 클래스이기 때문에 생성자와 property를 갖는다.
- enum class 의 객체를 먼저 열거하고 뒤에 fun 을 쓸 수 있는데, 이 경우 반드시 ; 으로 열거가 끝났음을 알려야 한다.

#### when 으로 enum 클래스 다루기

```kotlin
fun 영어_색이름 (color: 색) = when(color){
    색.빨강색 -> "red"
    색.주황색 -> "orange"
    색.노랑색 -> "yellow"
    색.초록색 -> "green"
    색.파랑색 -> "blue"
    색.남색 -> "indigo"
    색.보라색 -> "viloet"
}

fun 색의_따뜻한_정도 (색이름 : 색): String = when(색이름){
    색.빨강색, 색.주황색, 색.노랑색 -> "따뜻한 색"
    색.초록색 -> "중립"
    색.파랑색, 색.남색, 색.보라색 -> "차가운색"
}
```

- if와 마찬가지로 when도 식이다. (구문이 아니라 함수)
- 분기 끝에 break를 넣지 않는다.
- 여러 값을 매치하는 경우 , 를 써서 열거한다.
- import ...\* 를 이용해 더 간단하게 쓸 수 있다.

#### when과 임의의 객체를 함께 사용

```kotlin
fun 혼합하기(색1: 색, 색2: 색) = when(setOf(색1, 색2)) {
    setOf(빨강색, 노랑색) -> 주황색
    setOf(노랑색, 파랑색) -> 초록색
    setOf(파랑색, 보라색) -> 남색
    else -> throw Exception("조합을 알 수 없음")
}

println(" 빨강색과 노랑색을 섞으면 ${혼합하기(빨강색, 노랑색)}이 된다.")
println(" 노랑색과 빨강색을 섞으면 ${혼합하기(노랑색, 빨강색)}이 된다.")
```

- when 의 조건절에는 임의의 객체가 올 수 있다.
- setOf 는 Set 을 만들어주는 컬렉션 도우미 함수이다.
- Set 은 안에 있는 요소를 가지고 동등성을 조사한다. 그래서 빨강색과 노랑색의 순서를 바꿔도 같은 결과가 나온다.

#### 인자 없는 when 사용

```kotlin
fun 혼합하기2(색1: 색, 색2: 색) = when {
    (색1 == 빨강색 && 색2 == 노랑색) || (색2 == 빨강색 && 색1 == 노랑색) -> 주황색
    (색1 == 노랑색 && 색2 == 파랑색) || (색2 == 노랑색 && 색1 == 파랑색) -> 초록색
    (색1 == 파랑색 && 색2 == 보라색) || (색2 == 파랑색 && 색1 == 보라색) -> 남색
    else -> throw Exception("조합을 알 수 없음")
}
```

- when 에 인자를 주지 않고 바로 검사해도 된다.
- 같은 결과가 나온다.
- 가독성은 좀 떨어지는 복잡한 코드이지만, 성능은 더 나을 것이다.

#### 스마트 캐스트 : 타입 검사와 cast 를 조합

다음 문제를 코틀린으로 풀어보자.

```
  (1 + 2 ) + 4
```

자바 방식으로 하기

```kotlin
interface 식
data class 숫자(val 값: Int) : 식
data class 더하기(val 왼쪽식:식 , val 오른쪽식:식 ) : 식

fun 계산(식:식) : Int {
    if(식 is 숫자){
        val 식의 = 식 as 숫자 // 구지 캐스팅 할 필요가 없음...
        return 식의.값
    }
    if( 식 is 더하기){
        return 계산(식.왼쪽식) + 계산(식.오른쪽식); // 스마트 캐스트
    }
    throw IllegalArgumentException("알 수 없는 연산")
}


val 결과 = 계산(더하기(더하기(숫자(1),숫자(2)), (숫자(4))));
println(" 계산 결과 ${결과} ")

```

- 타입 캐스팅은 as 를 이용한다.
- is 연산자로 검사를 하고 나면 구지 타입 캐스팅을 하지 않아도 컴파일러가 알아서 타입을 바꿔준다. -> 스마트 캐스트

#### 리팩토링 : if -> when

```kotlin
fun 계산(식:식) : Int =
    if(식 is 숫자){
        식.값
    }else if( 식 is 더하기){
        계산(식.왼쪽식) + 계산(식.오른쪽식);
    }else {
        throw IllegalArgumentException("알 수 없는 연산")
    }
```

- 코틀린의 if 는 문장이 아니라 식이다.
- 식은 값을 반환한다. 위와 같이 fun 을 바꿀 수 있다.
- 그래서 삼항연산자 ( 조건 ? 참 : 거짓) 가 없다. 그냥 if 로 대신한다.

```kotlin
fun 계산(식:식) : Int = when(식){
    is 숫자 -> 식.값
    is 더하기 -> 계산(식.왼쪽식) + 계산(식.오른쪽식);
    else -> throw IllegalArgumentException("알 수 없는 연산")
}
```

- if를 when 으로 바꾸면, Boolean 값을 적용한 식과 return 식으로 바꿀 수 있다.

#### if와 when의 분기에서 블록 사용

- if나 when 모두 분기에 블록문 사용할 수 있다.
- 블럭의 마지막 식이 블럭의 결과이다.
- 함수의 블럭 본문은 예외적으로 return 을 명시해야 한다.

```kotlin
fun 계산(식:식) : Int = when(식){
    is 숫자 -> {
        println("수 : ${식.값}")
        식.값
    }
    is 더하기 -> {
        println("더하기 : ${식.왼쪽식} + ${식.오른쪽식} ")
        계산(식.왼쪽식) + 계산(식.오른쪽식)
    }
    else -> throw IllegalArgumentException("알 수 없는 연산")
}
fun main(){
    val 결과 = 계산(더하기(더하기(숫자(1),숫자(2)), (숫자(4))));
    println("계산 결과 ${결과} ")
}
```

```
더하기 : 더하기(왼쪽식=숫자(값=1), 오른쪽식=숫자(값=2)) + 숫자(값=4)
더하기 : 숫자(값=1) + 숫자(값=2)
수 : 1
수 : 2
수 : 4
계산 결과 7
```

## 4. while 과 for : 루프

#### while 루프

- while과 do-whille 문을 쓴다. 자바와 동일

#### 수에 대한 이터레이션 : 범위와 수열

- 자바의 for(int i=0;i<10; i++) 에 대응하는 코드는 없다.
- 코틀린에는 Range 연산이 있다. 1..10 (python 에서 따온듯...)

```kotlin
fun 피즈버즈(숫자:Int) = when {
    숫자 % 15 == 0 -> "피즈버즈 "
    숫자 % 3 == 0 -> "피즈 "
    숫자 % 5 == 0 -> "버즈 "
    else -> "${숫자} "
}
fun main(){
    for(숫자 in 1..100){
        print(피즈버즈(숫자))
    }
    for(숫자 in 100 downTo 50 step 2){
        print(피즈버즈(숫자))
    }
}
```

- 100 downTo 50 step 2 : 100부터 50까지 2씩 내려가는 범위의 수

#### 맵에 대한 이터레이션

```kotlin
fun main(){
    val 이진표현맵 = TreeMap<Char, String>()

    for(알파벳 in 'A'..'F') {
        val 이진값 = Integer.toBinaryString(알파벳.toInt())
        이진표현맵.put(알파벳, 이진값)
        // 이진표현맵[알파벳]= 이진값 // 같은 표현이다.
    }

    for((알파벳, 이진값) in 이진표현맵 ){
        println("${알파벳} ==> ${이진값}")
    }
}
```

- .. 연산자는 숫자뿐만 아니라 문자에도 쓸 수 있다.
- 맵은 (키, 값)을 쌓으로 갖는 컬렉션이다.

```kotlin
val 이진리스트 = arrayListOf("10", "11", "1001");
for((순번, 값) in 이진리스트.withIndex()){
    println("${순번} ==> ${값}")
}
// =======
0 ==> 10
1 ==> 11
2 ==> 1001
```

- 배열도 구조분해 구문(?)을 이용해 순번을 같이 쓸 수 있다.
- withIndex 구문은 3장에서 배운다.

#### in 으로 컬렉션이나 범위의 원소 검사

```kotlin
fun 알파벳인가(문자:Char) = 문자 in 'a'..'z' || 문자 in 'A'..'Z'
fun 숫자인가(문자: Char) = 문자 in '0'..'9'
fun 한글인가(문자: Char) = 문자 in '가'..'힣'

fun 무슨문자인가(문자:Char) = when{
    알파벳인가(문자) -> "알파벳"
    숫자인가(문자) -> "숫자"
    한글인가(문자) -> "한글"
    else -> "몰라"
}

println(무슨문자인가('a'))
println(무슨문자인가('8'))
println(무슨문자인가('학'))
```

```
알파벳
숫자
한글
```

- in 연산자는 값의 범위에 있는지를 판단하는 연산자이다.
- !in 은 속하지 않은지를 검사한다.
- Comparable 인터페이스를 구현한 클래스는 모두 in 으로 연산이 된다.
- 문자열도 문자열의 비교연산으로 범위 연산을 수행한다.
  ```kotlin
  println("Kotlin" in "Java".."Scala") // true
  println("Botlin" in "Java".."Scala") // false
  println("스칼라" in "가봉".."코틀린")  // true
  println("스칼라" in "자바".."코틀린")  // false 가나..사..자..카..하
  ```
- Set 이나 컬렉션에서도 연산이 가능하다.
  ```kotlin
  println("코틀린" in setOf("자바", "스칼라")) // false
  println("코틀린" in setOf("자바", "스칼라", "코틀린")) // true
  ```

## 5. 예외 처리

- 코틀린에서 예외를 던지는 문법은 자바와 거의 같다.

```kotlin
fun 퍼센트(값:Int){
    if(값 !in 0..100){
        throw IllegalArgumentException("퍼센트 값은 0과 100사이의 값이 와야 합니다.")
    }
}
println(퍼센트(120))
```

- 이 코드는 아래와 같은 오류를 남긴다.

```
Exception in thread "main" java.lang.IllegalArgumentException: 퍼센트 값은 0과 100사이의 값이 와야 합니다.
	at com.sp.demo1.demo.MapIterationKt.퍼센트(MapIteration.kt:46)
	at com.sp.demo1.demo.MapIterationKt.main(MapIteration.kt:51)
	at com.sp.demo1.demo.MapIterationKt.main(MapIteration.kt)
```

- throws 는 식으로 다른 식에 포함될 수 있다.

```kotlin
val 퍼센트값 = if(값 in 0..100) 값
else throw IllegalArgumentException("퍼센트 값은 0과 100사이의 값이 와야 합니다.")
```

#### try, catch, finally 구문

- 리더를 이용해 첫번째 줄의 숫자를 읽어온다. try-catch-finally 구문은 자바와 비슷하다.

```kotlin
fun 첫줄의숫자는 (리더: BufferedReader) : Int? {
    try{
        val 라인 = 리더.readLine()
        return Integer.parseInt(라인)
    }catch(e: NumberFormatException){
        return 0
    }finally {
        리더.close()
    }
}
fun main(){
      println(첫줄의숫자는(BufferedReader(StringReader("""
        123
        사람아 아 사람아
        """.trimIndent()))))
}
```

- throws 를 fun에 명시하지 않는다. IOException 이 발생할 수 있는데...
- 코틀린은 모든 예외를 꼭 잡아내지 않아도 되고 처리하지 않아도 된다.
- 자바에서의 폐헤
  - 개발자들이 불필요한 Exception 을 잡아서 처리하느라 고생한다.
  - Runtime 에러는 디폴트 처리를 해주는게 나은데, 심각한 오류 효과를 낸다.
- 그래서 코틀린은 필요한 경우에만 예외를 잡아서 처리하도록 강제하지 않았다. (좋네..)

#### try 를 식으로 사용

```kotlin
fun 첫줄의숫자는 (리더: BufferedReader) = try{
        Integer.parseInt(리더.readLine())
    }catch(e: NumberFormatException){
        0
    }
```

- try 구문은 반드시 { } 로 둘러싸야 한다.
- try 의 마지막 식이나 catch의 마지막 식이 리턴된다.
- finally 는 의미가 없어 보인다.. (내 생각) 근데 이 경우 close는 안해도 되나?
