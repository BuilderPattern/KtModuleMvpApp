package kt.module.base_module.http;

import java.io.IOException;

import com.orhanobut.logger.Logger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class LogInterceptor implements Interceptor {

    private static final String F_BREAK = " %n";
    private static final String F_URL = " %s";
    private static final String F_TIME = " in %.1fms";
    private static final String F_HEADERS = "%s";
    private static final String F_RESPONSE = F_BREAK + "KtApp: %d";
    private static final String F_BODY = "body: %s";

    private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
    private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
    private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
    private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String bodyString = null;
        if (response.body() != null) {
            bodyString = response.peekBody(1024*1024).string();//此处用peekbody获得body的复制，参数为内存占用限制
        }
        double time = (t2 - t1) / 1e6d;
        if (request.method().equals("GET")) {
            Logger.e(String.format("GET " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), response.code(), response.headers(), bodyString));
        } else if (request.method().equals("POST")) {
            Logger.e(String.format("POST " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY, request.url()
                    , time, request.headers(), stringifyRequestBody(request), response.code(), response.headers(), bodyString));
        }
        return response;
    }

    private String stringifyRequestBody(Request request1) {
        try {
            final Request request = request1.newBuilder().build();
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return "did not work";
        }
    }
}