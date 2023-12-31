# kotlin-study
목차
- [1. 프로그램을 더 안전하게 만들기](#1-----------------)
    * [1.1. 프로그래밍의 함정](#11----------)
        + [1.1.1. 안전하게 부수효과 처리하기](#111---------------)
        + [1.1.2. 참조 투명성으로 프로그램을 더 안전하게 만들기](#112--------------------------)
    * [1.2. 안전한 프로그래밍의 이점](#12--------------)
        + [1.2.1. 프로그램을 추론하는 데 치환 모델 사용하기](#121------------------------)
        + [1.2.3. 끝까지 추상화하기](#123----------)

# 1. 프로그램을 더 안전하게 만들기
> - 프로그래머에게 가장 필요한 자질은 자기 자신의 한계를 깨닫는 것이다.
> - 불변성, 참조 투명성, 치환 모델 등의 기법을 사용하여 **버그가 없음이 명확히 보이는 단순한 프로그램**을 작성하자.

## 1.1. 프로그래밍의 함정

- 상태 변화나 루프 같은 위험한 기능을 사용하지 말라.
    - 가변 참조나 루프가 필요한 경우가 생긴다면 그들을 추상화하라.
- 제어 구조를 피하라.
- 효과를 프로그램의 일부 영역 안에서만 일어나도록 제한하라.
    - 효과: 외부 세계(I/O, 컴포넌트 범위 밖의 원소를 변경하는 행위 등)와의 상호 작용
- 예외를 던지지 말라.

### 1.1.1. 안전하게 부수효과 처리하기

**부수효과(side effect)란?**
- 값을 반환하는 메서드나 함수가 외부 상태를 변경하는 경우

**안전한 프로그램을 만드는 방법**
- 부수 효과가 발생하지 않도록 하기
- 인자를 받아서 값을 반환하는 여러 함수를 합성해서 만들기

### 1.1.2. 참조 투명성으로 프로그램을 더 안전하게 만들기

**참조 투명성이란?**
- 외부 세계의 상태를 변경하지도 않고 외부 상태에 의존하지도 않는 코드
- 프로그램을 충분히 안전하고 결정적으로 만들려면 외부 세계로부터 영향을 받지 않도록 해야 함
    - 프로그램의 출력은 오직 그 인자에 의해서만 영향을 받아야 함

**참조 투명한 코드의 특징**
- 자기 완결적임
    - 어떤 문맥에서도 해당 코드를 사용 가능함
- 결정적임
    - 같은 인자에 대해 같은 결과를 보장함
- 예외를 던지지 않음
- 예기치 않게 다른 코드가 실패하는 상황을 만들지 않음
    - 인자를 변경하거나 다른 외부 데이터를 변경하지 않음
- 외부 장치에 의존하지 않음

## 1.2. 안전한 프로그래밍의 이점

### 1.2.1. 프로그램을 추론하는 데 치환 모델 사용하기
- 참조 투명한 함수 호출이나 식은 항상 그 결괏값으로 치환할 수 있음
- 결괏값으로 치환해 프로그램을 계산하는 **치환 모델**을 함수에 적용해 프로그램의 결과를 추론할 수 있음

### 1.2.3. 끝까지 추상화하기
- 부수 효과가 없는 순수 함수를 합성하면 테스트하기 쉬운 안전한 프로그램을 작성할 수 있음

**순수 함수란?**
- 주어진 입력 값에 대해 항상 동일한 출력 값을 반환하며, 부수 효과가 없는 함수
- fun 키워드를 사용하거나 **함수 값**으로 정의할 수 있음

**함수 값이란?**
- 함수를 변수에 할당하거나 다른 함수에 인자로 전달하거나 함수로부터 반환하는 데 사용되는 **값**
- 일급 함수의 하위 개념이라고 할 수 있음
    - 일급 함수: 함수를 값으로 다룰 수 있는 언어의 기능을 나타내는 개념
    - 함수 값: 이러한 기능을 활용하여 함수를 변수에 할당하거나 다른 함수와 상호 작용하는 데 사용되는 구체적인 값

추상화
- 함수를 더 높은 단계로 추상화해서 재사용할 수 있다.
- 함수를 끝까지 추상화하면 추상화된 부분을 단 한 번만 작성해서 프로그램을 더 안전하게 만들 수 있다.
    - 추상화된 부분을 완전히 테스트하기만 하면 되기 때문

# 2. 코틀린 프로그래밍의 개요

## 2.2. 클래스와 인터페이스

**class 정의하기**
- 코틀린 클래스는 기본적으로는 public이고, private, protected, internal 을 지정할 수 있다.
- 코틀린 클래스는 기본적으로 상속불가(final)이다. (자바는 그렇지 않음)
    - 상속 가능하게 열어주려면 클래스 앞에 open을 추가해야 한다.
    - 클래스 앞에 data를 붙이면 자동으로 hashCode, equals, toString, copy 함수가 생성된다. (dto에서 활용하기 좋음)
- 정적 메서드를 구현하고 싶다면, companion object에 추가하자.
- 싱글턴으로 메서드를 구현하고 싶다면 class 대신 object를 쓰자
- 유틸리티 클래스는 어떤 클래스에도 포함되지 않는 함수를 만들어서 쓰면 된다.

**지연계산**
- by lazy 또는 lateinit을 사용해서 정의할 수 있음
- 최초로 사용하는 시점에 값을 가져오는 방식(9장에서 더 자세히 다룸)


## 2.7. 함수
### 클로저
- 외부 변수에 접근할 수 있는 함수
```kotlin

// isPrime은 외부변수인 seq에 접근하고 있다
fun sumOfPrimes(limit: Int): Long {
    val seq: Sequence<Long> = sequenceOf(2L) + generateSequence(3L, { it + 2 }).takeWhile {
        it < limit
    }

    fun isPrime(n: Long): Boolean = seq.takeWhile { it * it <= n }.all { n % it != 0L }

    return seq.filter(::isPrime).sum()

}

// 책 외에 다른 예시
fun specialFunc() {
    val sum = 0
    val intRange = (0..10).toList()

    // 아래 forEach 함수도 클로저로 볼 수 있음
    intRange.forEach { 
        sum += it
    }
}
```
클로저로 선언된 함수는 해당 변수를 인자로 받는 함수로 변경할 수 있다.
```kotlin
// 위의 예시로는
fun isPrime(seq: Sequence<Long>, n: Long) = seq.takeWhile { it * it <= n }.all { n % it != 0L }
```
단 위와같이 변경하는 경우 함수참조는 사용할 수 없다. 내부에서만 사용한다면 클로저를 사용해서 함수참조를 사용하도록 하는게 더 낫다.


## 2.16. 변성, 공변성, 반공변성, 무공변성

> 변성이란?
> - **파라미터화한 타입**이 서로 어떤 하위 타입 관계에 있는지 결정하는 방식

공변성(covariance)
- 서브 타입에서 슈퍼 타입으로 형 변환할 때 타입 호환성을 허용함
- Red 가 Color의 하위 타입일 때 Matcher<Red> 가 Matcher<Color>의 하위 타입이라면 Matcher<T>는 타입파라미터 T에 대해 공변성

반공변성(contravariance)
- 서브 타입에서 슈퍼 타입으로 형 변환할 때, 파라미터 타입에서 타입 호환성을 허용함
- Red가 Color의 하위 타입일 때 Matcher<Color>가 Matcher<Red>의 하위 타입이라면 Matcher<T>는 타입파라미터 T에 대해 반공변성

무공변성(invariance)
- 서브 타입과 슈퍼 타입 사이의 관계에서 어떤 형태의 변화도 허용하지 않음
- 서브 타입 객체를 슈퍼 타입 객체로 캐스팅할 때 형 변환 오류가 발생
- 예를 들어 `List<Object>`타입 변수에 `List<String>`타입 객체를 할당할 수 없음

코틀린에서의 변성 지정
- in, out 키워드를 사용하여 변성을 지정할 수 있음
- 타입을 오직 출력에 쓰면 out을, 오직 입력에 쓰면 in을 사용하여 선언 지점 변성을 한다.
- 타입을 출력과 입력에 모두 사용하여 out 또는 in을 지정할 수 없는 경우, 사용 지점 변성을 하면 된다.
```kotlin
// 선언 지점 변성
interface Bag<out T> { // 공변성
    fun get(): T
}
 
interface Bag<in T> { // 반공변성
    fun use(t: T): Boolean
}

// 타입을 출력과 입력에 모두 사용하는 케이스
interface Bag<T> {
    fun get(): T
    fun use(t: T): Boolean
}
 
// in 사용 지점 변성
fun useBag(bag: Bag<in MyClass>): Boolean {
    // bag로 작업 수행
    return true
}
 
class BagImpl : Bag<MyClassParent> {
    override fun get(): MyClassParent = MyClassParent()
    override fun use(t: MyClassParent): Boolean = true
}
 
val bag3 = useBag(BagImpl())
 
 
// out 사용 지점 변성
fun createBag(): Bag<out MyClassParent> = BagImpl2()
 
class BagImpl2 : Bag<MyClass> {
    override fun use(t: MyClass): Boolean = true
    override fun get(): MyClass = MyClass()
}
```



# 3. 함수로 프로그래밍 하기
> - 안전한 프로그램을 작성하기 위해서는 계산 시 순수 함수만 사용하고, 계산 결과를 외부에서 사용하기 위해 순수 효과를 사용해야 한다.
> - 효과에 계산이 포함되어 있는 *순수하지 않은 효과*에서 계산을 따로 순수 함수로 떼어내야 한다.

## 3.1. 함수란 무엇인가

함수의 조건
- 대응 관계가 다음 둘을 만족시켜야 함
    - 정의역의 모든 원소에 대해 함수가 정의되어야 함
    - 정의역의 모든 원소는 반드시 공역의 한 값에만 대응해야 함

부분 함수(partial function)란?
- 정의역의 모든 원소에 대해 정의되어 있지는 않지만 나머지 요구 조건을 만족하는 대응 관계
    - 예) f(x) = 1/x은 N에서 Q로 가는 부분 함수
- 부분 함수가 아닌 함수를 전함수(total function)이라 부름
- 공역에 원소(오류)를 추가하면 부분함수를 전함수로 바꿀 수 있음

### **커리한 함수(curried function)**
커리한 함수의 정의
- 인자(argument)를 여러 개 받는 함수를 인자를 하나씩 받는 함수들의 연속으로 분해한 것
    - f:(X * Y) -> Z인 함수를 X -> (Y -> Z) 로 변환하는 것
    - f(x,y) = x + y^2 의 커리 함수 h는 R^R로 정의되며 h(3) = 3 + y^2으로 표현됨
- f(x, y)를 f(x)(y)로 변환하는 과정을 커링(currying)이라 함

커리한 함수의 예시
- `val add: (Int) → (Int) → Int = {a → { b → { a + b } }`가 있을 때
- `add(3)(5)`는 다음과 같이 계산됨
1. add(3)이 먼저 호출되어 다음 "함수"를 반환
-   `(b: Int) => { 3 + b }`
2. 위 함수의 인자로 5를 받아서 `3 + 5 = 8`을 반환

## 3.2. 코틀린 함수

fun 함수
- 인자를 넘기고 그 인자에 따른 반환 값을 얻는 일만 하는 경우에 사용
- fun 키워드를 사용해 정의한 함수는 순수 함수임을 보장할 수 없음
    - 예) `fun div(a: Int, b:Int): Int = a/b`

함수 타입의 식
- 함수를 데이터처럼 취급하는 경우에 사용
    - 함수에서 함수를 돌려줘야 하는 경우
    - 변수, map 등의 자료구조에 함수를 저장해야 하는 경우
- 함수와 일치하는 타입의 참조에 대입할 수 있음
    - `fun double(x: Int): Int = x * 2` 는 다음과 같이 정의 가능
    - `val double = (Int) -> Int: {x -> x * 2}`

함수식 vs fun 함수
fun 함수가 더 효율적이므로 fun 함수를 사용하는 편이 낫다.
한편 함수를 데이터 구조에 저장하거나 함수를 파라미터로 쓰는 등 데이터 처럼 취급해야 하는 경우라면 함수 식을 사용해라.

익명 함수
익명 함수를 사용할지 결정할 때는 코드의 명확성과 유지 보수성만을 고려해 판단하면 된다.
코틀린은 함수가 호출될 때마다 항상 객체를 생성하지는 않으며, 익명 함수 객체를 생성하는 비용도 저렴하기 때문이다.

## 3.3. 고급 함수 기능

부분적용(Partial Application)
- 함수의 인자 중 일부만을 고정하고 나머지 인자를 나중에 제공하는 것
- 함수를 호출할 때 모든 인자를 한 번에 전달하지 않고 여러 번에 걸쳐 전달할 수 있도록 함
- 커리한 함수를 이용해서 부분적용 가능함

부분적용의 장점
- 모듈화(Modularity)
    - 함수의 로직을 더 작은 조각으로 나누고, 각 조각을 독립적으로 다룰 수 있음
    - 이렇게 분리된 함수는 각각의 역할을 명확하게 하고 함수의 코드를 더 이해하기 쉽게 만들어 주고 유지 보수가 용이해짐
- 재사용성(Reusability)
    - 부분적으로 적용된 함수는 원래 함수의 일부분이므로 다른 곳에서도 동일한 로직을 재사용할 수 있음
    - 중복 코드를 줄이고 다른 함수에도 동일한 방식으로 부분 적용을 적용할 수 있음
- 유연성(Flexibility)
    - 함수를 부분적용하면 다양한 상황에 맞게 함수를 조합하여 새로운 함수를 생성할 수 있음
    - 함수 조합(함수를 합성하거나 연결하는 것)과 함께 사용될 때 더 강력한 표현력을 제공함
- 코드 가독성(Readability)
    - 함수를 부분적용하면 함수 호출 코드가 간결해지고 가독성이 향상됨
    - 부분적으로 적용된 함수는 일반 함수보다 인자를 적게 받으므로 함수 호출 시 필요한 정보만 명시적으로 전달함



데이터 클래스의 생성자를 비공개하더라도, copy() 를 이용하여 필드에 원하는 대로 값을 설정할 수 있다.
검증 규칙을 꼭 지켜야 한다면, 일반 클래스를 사용하는 방법 밖에 없다.

### 값 타입 정의
> 프로그램을 더 안전하게 만들려면 강력한 타입을 사용해 컴파일러가 프로그램을 검사하게 만들어야 한다.
이름은 단지 의도를 보여줄 뿐이다.
이름보다는 타입을 신뢰해야 한다.

```kotlin
data class Product(val price: Double, val weight: Double)
```
- 위 예시에서 Product와 Double(price), Double(weight)이 각각 있는 것이 아니고,
- Product와 Double간의 관계차수가 1:2 ..? (둘은 관계가 없다)
- 각각 값 타입을 사용하는 것이 바람직
- 값 타입을 정의할 때 **연산자를 오버라이딩** 할 수 있다.
    - https://kotlinlang.org/docs/operator-overloading.html#property-delegation-operators
- 동등연산자(==)는 내부적으로 a?.equals(b) ?: (b === null)과 같이 수행된다.

**연산자 오버로딩**
- 코틀린에서는 연산자에 대한 오버로딩을 제공한다. (자바는 제공하지 않음)
- 객체 간 연산을 할 때, 연산자 오버로딩을 설정하고 상황에 맞게 활용할 수 있다.
- 동일한 타입을 활용하는 상황을 방지하고자 할 때, 각각을 객체로 만들어 연산자 오버로딩을 통해 코딩에서 발생하는 오류를 방지할 수 있다.
    - ex) weight, amount를 계산해야 하는 로직이 있을 때, weight에 amount를 지정하고, amount에 weight를 지정하는 실수가 생겼을 때

```kotlin
data class Weight(
    val value: Int
) {
    operator fun plus(weight: Weight) = this.value + weight.value
}

data class Amount(
    val value: Int
) {
    operator fun plus(amount: Amount) = this.value + amount.value
}
```
실행 결과
```
println(Weight(1) + Weight(3)) // 4
println(Amount(10) + Amount(3)) // 13
println(Weight(3) + Amount(4)) // Type MisMatch
```

대표적인 operator (더 많은 내용은 https://kotlinlang.org/docs/operator-overloading.html 참조)

| Expression | Translated to       |
|------------|---------------------|
| a + b      | a.plus(b)           |
| a - b      | a.minus(b)          |
| a * b	     | a.times(b)          |
| a / b      | 	a.div(b)           |
| a % b      | 	a.rem(b)           |
| a .. b     | 	a.rangeTo(b)       |
| a ..< b    | 	a.rangeUntil(b)    |
| a in b	    | b.contains(a)       |
| a()	       | a.invoke()          |
| a(i)	      | a.invoke(i)         |
| a > b      | 	a.compareTo(b) > 0 |

>- invoke를 통해 처음 생성시 값에 대한 검증을 할 수도 있다.
>- data class의 비공개 생성자는 자동으로 생긴 copy 함수에 의해 외부로 노출된다.
   >  - 검증 규칙을 꼭 지켜야 하는 경우 일반 클래스를 활용하자
