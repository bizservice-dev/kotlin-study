# 1주차 키워드 (ch2, ch3)

---

### 지연 초기화
```kotlin
// 불변 변수
val name: String by lazy { getName() }

// 가변 변수 : 나중에 초기화되는데 초기값을 정의하기 어려울 때 ( null이나 "" 를 사용하지 않는 방법 )
lateinit var name: String
```

### 클래스 접근 제한자
코틀린은 private, protected, internal의 접근 제한자가 존재한다

- internal : 같은 모듈 내에서만 사용 가능
- protected : 자바와 다르게 확장하는 클래스에서만 사용 가능 ( 같은 패키지 내의 다른 클래스는 사용 불가능 )

자바는 내부 클래스의 private 멤버를 외부에서 볼 수 없다
```java
public class OuterJavaVisibleTest {
    
    private String outerPrivateMember = "Outer variable";
    
    public void outerFunc() {
        String member = innerPrivateMember; // error
    }
    
    class InnerJavaVisibleTest {
        private String innerPrivateMember = "Inner variable";
        
        public void innerFunc() {
            String member = outerPrivateMember;
        }
    }
}
```

코틀린은 외부 클래스의 private 멤버를 내부에서 볼 수 없다 ( + 내부 클래스의 private도 외부에서 볼 수 없다)
```kotlin
class OuterKotlinVisibleTest {

    private val outerPrivateMember: String = "Outer variable"

    fun outerFunc() {
        val member = innerPrivateMember // error
    }

    class InnerKotlinVisibleTest {
        private val innerPrivateMember: String = "Inner variable"

        fun innerFunc() {
            val member = outerPrivateMember // error
        }
    }
}
```

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

### 커리한 함수
- 함수의 인자를 단 하나만 받는 형태로 만든 함수
- 전달하고자하는 파라미터에 따라서 함수를 분리할 수 있기 때문에 재사용성이 높아진다.
```kotlin
// def a: A, b: B, c: C, d: D

// 일반적인 함수
func(a, b, c, d)

// 커리한 함수
curryFunc(a)(b)(c)(d)

// 재사용성을 높인 분리한 함수
val defaultCurryFunc = curryFunc(a) // return (B) -> (C) -> D
defaultCurryFunc(b)(c)(d)

```

----
