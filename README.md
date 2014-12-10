#Infogram-Java

A library to view, create and update infographics on Infogr.am.

##Usage

To make the API calls, use the `am.infogr.api.InfogramAPI` class. The response is wrapped in an appropriate Response object, containing the metadata and the body of the response.

```java
import am.infogr.api.InfogramAPI;
import am.infogr.api.response.JsonResponse;

// ...

InfogramAPI infogram = new InfogramAPI(YOUR_API_KEY, YOUR_API_SECRET);
JsonResponse response = infogram.getInfographicList();

if (response.success()) {
    String infographics = response.getResponseBody();
}
```
