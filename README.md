#Infogram-Java

A library to view, create and update infographics on Infogr.am. For details on the different calls see https://developers.infogr.am/rest/

##Usage

To make the API calls, use the `am.infogr.api.InfogramAPI` class. The response is wrapped in an appropriate Response object, containing the metadata and the body of the response. The response body can be accessed via a `java.io.InputStream`.

```java
import am.infogr.api.InfogramAPI;
import am.infogr.api.response.Response;
// ...

InfogramAPI infogram = new InfogramAPI(YOUR_API_KEY, YOUR_API_SECRET);
Response response = infogram.sendRequest("GET", "infographics", null);

if (response == null) {
    System.err.println("There was a problem making the request");
}
else if (response.success()) {
    InputStream is = response.getInputStream();

    // ...
}
else {
    String errmsg = String.format("The server returned %d %s", response.getResponseCode(), response.getResponseMessage());
    System.err.println(errmsg);
}
```

The API provides formatted results for JSON and PNG responses.

```java
import am.infogr.api.InfogramAPI;
import am.infogr.api.response.JsonResponse;
import am.infogr.api.ResponseType;
// ...

InfogramAPI infogram = new InfogramAPI(YOUR_API_KEY, YOUR_API_SECRET);
JsonResponse response = (JsonResponse) infogram.sendRequest("GET", "infographics", null, ResponseType.JSON);

if (response == null) {
    System.err.println("There was a problem making the request");
}
else if (response.success()) {
    String json = response.getResponseBody();

    // ...
}
else {
    String errmsg = String.format("The server returned %d %s", response.getResponseCode(), response.getResponseMessage());
    System.err.println(errmsg);
}
```

To make the calls asynchronously, use the `sendAsyncRequest` method.

```java
import am.infogr.api.InfogramAPI;
import am.infogr.api.response.Response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

// ...

InfogramAPI infogram = new InfogramAPI(YOUR_API_KEY, YOUR_API_SECRET);
RunnableFuture responseFuture = infogram.sendAsyncRequest("GET", "infographics", null);

ExecutorService executor = Executors.newCachedThreadPool();
executor.execute(responseFuture);

// ...

Response response = responseFuture.get();
```
