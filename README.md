#Infogram-Java

A library to view, create and update infographics on Infogr.am. For details on the different calls see https://developers.infogr.am/rest/

##Usage

To make the API calls, use the `net.infogram.api.InfogramAPI` class. The response is wrapped in an appropriate Response object, containing the metadata and the body of the response. The response body can be accessed via a `java.io.InputStream`.

```java
import net.infogram.api.InfogramAPI;
import net.infogram.api.response.Response;
// ...

InfogramAPI infogram = new InfogramAPI(YOUR_API_KEY, YOUR_API_SECRET);

try {
    Response response = infogram.sendRequest("GET", "infographics", null);

    if (response.isSuccessful()) {
        InputStream is = response.getResponseBody();

        // ...
    } else {
        String errmsg = String.format("The server returned %d %s", response.getHttpStatusCode(), response.getHttpStatusMessage());
        System.err.println(errmsg);
    }
} catch (IOException e) {
    System.err.println("There was a problem connecting to the server");
    e.printStackTrace();
}
```

To make the calls asynchronously, use the you can wrap a `Future` around the `sendRequest()` method. An example is given below.

```java
import net.infogram.api.InfogramAPI;
import net.infogram.api.response.Response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

// ...

InfogramAPI infogram = new InfogramAPI(YOUR_API_KEY, YOUR_API_SECRET);

FutureTask responseFuture = new FutureTask<Response>(new Callable<Response>() {
@Override
    public Response call() throws Exception {
        return sendRequest("GET", "infographics", null);
    }
});

ExecutorService executor = Executors.newCachedThreadPool();
executor.execute(responseFuture);

// ...

Response response = responseFuture.get();

// ...

executor.shutdown();
```

More samples can be found in [infogram-java-samples](https://github.com/infogram/infogram-java-samples) project
