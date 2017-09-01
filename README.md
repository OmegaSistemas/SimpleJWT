# SimpleJWT
Simple way to encode your payloads with Java 6.

**This class only serve to encode a payload (Map) in JSON,  simple and direct, only work with HS256(yet)**

# Example

```java
    Map<String, Object> payload = new HashMap<String, Object>();
    payload.put("pub", "251234");
    payload.put("exp", 1891212121);
    payload.put("foo", "bar");

    String token = JWT.encode(payload, "mysecretkeyisgreat");
		
```
