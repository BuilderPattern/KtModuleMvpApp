package kt.module.base_module.http;

import java.io.IOException;

import android.util.Log;
import com.orhanobut.logger.Logger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class LogInterceptor implements Interceptor {

    private static final String REQUEST_NEW_LINE = " %n";
    private static final String REQUEST_URL = " %s";
    private static final String REQUEST_TIME = " in %.1fms";
    private static final String REQUEST_HEADERS = "%s";
    private static final String REQUEST_BODY = "body：%s";

    private static final String RESPONSE_RESPONSE = "KtApp: %d";
    private static final String RESPONSE_DATA = "data: %s";

    private static final String REQUEST_WITHOUT_BODY = REQUEST_URL + REQUEST_TIME + REQUEST_NEW_LINE + REQUEST_HEADERS;
    private static final String REQUEST_WITH_BODY = REQUEST_URL + REQUEST_TIME + REQUEST_NEW_LINE + REQUEST_HEADERS + REQUEST_BODY + REQUEST_NEW_LINE;
    private static final String RESPONSE_WITH_BODY = RESPONSE_RESPONSE + REQUEST_NEW_LINE + REQUEST_HEADERS + RESPONSE_DATA + REQUEST_NEW_LINE;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String bodyString = null;
        if (response.body() != null) {
            bodyString = response.peekBody(1024 * 1024 * 2).string();//此处用peekbody获得body的复制，参数为内存占用限制
        }

        double time = (t2 - t1) / 1e6d;
        Logger.e("-------------------------------------------");
        if (request.method().equals("GET")) {
            Log.e("Log：", String.format("GET " + REQUEST_WITHOUT_BODY + RESPONSE_WITH_BODY, request.url(), time, request.headers(), response.code(), response.headers(), bodyString));
        } else if (request.method().equals("POST")) {
            Log.e("Log：", String.format("POST " + REQUEST_WITH_BODY + RESPONSE_WITH_BODY, request.url(), time, request.headers(), stringifyRequestBody(request), response.code(), response.headers(), bodyString));
        }
        return response;
    }

    private String stringifyRequestBody(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            e.printStackTrace();
            return "did not work";
        }
    }
}