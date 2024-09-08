
# 트러블 슈팅 - 제네릭 타입 소거

- 제네릭 타입 소거는 컴파일 시점에만 제네릭 정보를 사용하고 런타임 시점에는 제네릭 타입정보가 제거되는 메커니즘입니다.
- 이로 인해 제네릭 타입 정보는 컴파일 후에 존재하지 않습니다.
- 이는 자바의 하위 호환성을 유지하기 위한 설계입니다.

<br><br><br>

## 제네릭 타입 제거

```java
public class Box<T>{
	private T value; 
	public T getValue(){
			return value; 
	}
	public void setValue(T value){
			this.value = value; 
	}
```

- 이 코드에서 T는 제네릭 타입입니다.

<br><br>

### 컴파일 후

```java
public class Box {
	private Object value; 
	public Object getValue(){
			return value; 
	}
	public void setValue(Object value){
			this.value = value; 
	}
```

- T는 컴파일 후 Object 로 변환됩니다.
- 제네릭 타입은 실제로 런타임에 존재하지 않습니다.

<br><br><br>

## 제약된 타입의 처리

```java
public class Box<T extends Number>{
	private T value; 
	public T getValue(){
			return value; 
	}
	public void setValue(T value){
			this.value = value; 
	}
```

- 제네릭 타입이 특정 타입으로 제한된 경우입니다.

<br><br>

### 컴파일 후

```java
public class Box {
	private Number value; 
	public Number getValue() {
			return value; 
	}
	public void setValue(Number value){
			this.value = value; 
	}
```

- 제네릭 타입은 Number로 컴파일 후 처리됩니다.

<br><br><br>

## 제네릭 타입 정보 가져오기

---

- 제네릭 타입 저보는 런타임 시점에 제거되므로 제네릭 클래스로부터 T와 같은 타입 매개변수의 구체적인 타입 정보를 직접적으로 가져올 수 없습니다.
- 이를 해결하기 위해 Class<T>, 리플렉션, TypeToken 패턴을 사용하여 제네릭 타입정보를 얻을 수 있습니다.
- 해당 프로젝트에서는 Class<T>를 통해 제네릭 타입을 가져왔습니다.

<br><br>

### Class<T> 사용

```java
public class Box<T> {
    private Class<T> type;

    public Box(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<>(Integer.class);
        Box<String> stringBox = new Box<>(String.class);

        System.out.println("Integer Box Type: " + integerBox.getType().getName());
        System.out.println("String Box Type: " + stringBox.getType().getName());
    }
}
```

- 컴파일 시점에 전달된 타입의 정보를 유지하고 사용할 수 있습니다.
- 생성자에서 Class<T>를 전달받아 저장하는 방식입니다.

<br><br><br>

> 📖 톺아보기

- 타입 소거는 자바에서 제네릭 타입 정보를 컴파일 시점에만 유지하고, 런타임에는 제거하는 메커니즘입니다.
- 제네릭으로 컴파일 타임에 타입 안전성을 제공하지만, 런타임에는 제네릭 타입 정보가 존재하지 않으므로, 리플렉션이나 배열 생성에서 제약이 발생할 수 있습니다.
- 타입 소거 덕분에 자바는 제네릭이 도입되기 이전의 코드와 하위 호환성을 유지할 수 있습니다.
