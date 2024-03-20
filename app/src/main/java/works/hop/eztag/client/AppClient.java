package works.hop.eztag.client;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.component.LifeCycle;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AppClient {

    public static Supplier<Void> request(String method, String url, Map<String, String> headers, Request.Content content, Consumer<ContentResponse> responseConsumer) throws Exception {
        // Instantiate HttpClient.
        HttpClient httpClient = new HttpClient();
        // Configure HttpClient, for example:
        httpClient.setFollowRedirects(false);

        // Start HttpClient.
        httpClient.start();

        // Create a request
        Request request = httpClient.newRequest(url)
                .method(method)
                .timeout(10, TimeUnit.SECONDS);

        // Add request body
        if (content != null) {
            request.body(content);
        }

        // Add request headers
        if (!headers.isEmpty()) {
            request.headers(consumer -> {
                for (String key : headers.keySet()) {
                    consumer.add(key, headers.get(key));
                }
            });
        }

        // Send the request
        ContentResponse response = request.send();

        // Allow response to be extracted in the format that it is needed
        responseConsumer.accept(response);

        // Return shutdown handle
        return () -> {
            new Thread(() -> LifeCycle.stop(httpClient)).start();
            return null;
        };
    }
}
