package darkyx.com.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.ConnectionState;
import microsoft.aspnet.signalr.client.Constants;
import microsoft.aspnet.signalr.client.FutureHelper;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.StateChangedCallback;
import microsoft.aspnet.signalr.client.http.HttpConnection;
import microsoft.aspnet.signalr.client.http.HttpConnectionFuture;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.http.Response;
import microsoft.aspnet.signalr.client.http.android.AndroidHttpConnection;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.HttpClientTransport;
import microsoft.aspnet.signalr.client.transport.LongPollingTransport;
import microsoft.aspnet.signalr.client.transport.NegotiationException;
import microsoft.aspnet.signalr.client.transport.NegotiationResponse;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;
import microsoft.aspnet.signalr.client.transport.TransportHelper;
import microsoft.aspnet.signalr.client.transport.WebsocketTransport;

public class MainActivity extends AppCompatActivity {
    String TAG = "Signalr";
    HubConnection hubConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         hubConnection = new HubConnection("http://192.168.3.166:8899","",true,new SimpleLog());
//        hubConnection.getHeaders().put("Authorization","Bearer _nkuDKtw-C7SQ9NqPSwE6QNH5pScaU1JlOk52ZQgfBxfIPwhTXtby2vo67_K6r9_n75agiTUnMhbtGXy5wJ_kRJ-zYbozzMWTxPwXfF2pN5TjhW12ZSmuoX9Ws-7hPnVAwEVLUUedqBEOGcsLDo6tNcCR5EeccNJnhzzUcv-If0nTNkacdfGHmythm88AfsctDOVGzibWAfd0IaSU6p-L04NePKkKBo8olazgC07rSf_P4h16xBsYylUnEA0qZSzS0ntasuyH6wN3fWPUQI3ttDpnnkDVr2h57UTO1spM4xoDkv80UQPtLaT3paHT1uxuY9SsZ6d4LeAG3yALySWADhtiWwX3ypcp7VZUhs0mY4teQ5BJcknMWiapWUzKKzooCTImaurhKRpDJZ0Uhc2iEGnkMKfEA9ASr2UaDe1lWZuYcseJH0-NtAH_cO9UzAGC6NUS0L55vzwX0ZowcykvC6bXmG4A7urDf2ARlY0leIFZH8pD59x3YDLKdWBQaHa130h6TQmItJcfYrMliu_VA");

        hubConnection.getHeaders().put("Authorization","Bearer dBxNuQYx-z-FgJ_Y4t3pclW4bnqN7Q_mWh3xrTqZS2cU_DlK80MStLua4OSKllcGQlSK9jSayuy18xSED_pjaZWpVzqd39_cZPkxfwGRGkUnaflMMnp7ghFmW-qeNIb98FagDZMy723b3b-EX12MLtibPRAlUr9xA00o4LuPuax94pj_kXJUYvzPH-cxfzrj5da-69HBzkHlSDdA1VH6fmZQg_khe8UXdZAaLuRW-zSZuJAZY_hgISaYfJ_FHn1FV2Gg7tF47lfvurTr5gS45qdQhkLCnSnhZPBsFmas_LrbpwDwAhBj_j_HkQpUuBhLeunodPaFnFGdI17dAK0GMr9H51btjzyRjrsHV5WEtgUiKJ3I1c17g_tvdr5dL9PdS343KZ1W5JUiY9YxaywSw_L183JWYAUAUi4RR6QQYKenSAooSsDzp0Hh8weMDQy2TqCV1YRBTGO-kZVloAhDDjZkX43f3qi9PdBmwqMeQr4raCMtFy5rO1osMjza91eRe99uPE-CiY3P3IF0Ak4sC7nVsC8JCxMXkWX6Vcu5Q7T1E6imgpXxRVMBPRaMDlDh");
        HubProxy myCommonHub = hubConnection.createHubProxy("myCommonHub");

        hubConnection.reconnected(new Runnable() {
            @Override
            public void run() {
                Logger.log("重连成功",LogLevel.Information);
            }
        });
        hubConnection.connected(new Runnable() {
            @Override
            public void run() {
                Logger.log("自定义链接成功",LogLevel.Information);
                String url = null;
                try {
                    url = hubConnection.getUrl()+"start?"
                            +"transport="+"webSockets"
                            +"&connectionToken="+ URLEncoder.encode(hubConnection.getConnectionToken(),"UTF-8")
                            +"&connectionData="+"%5B%7B%22name%22%3A%22mycommonhub%22%7D%5D";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Request get = new Request(Constants.HTTP_GET);
                get.addHeader("Authorization","Bearer dBxNuQYx-z-FgJ_Y4t3pclW4bnqN7Q_mWh3xrTqZS2cU_DlK80MStLua4OSKllcGQlSK9jSayuy18xSED_pjaZWpVzqd39_cZPkxfwGRGkUnaflMMnp7ghFmW-qeNIb98FagDZMy723b3b-EX12MLtibPRAlUr9xA00o4LuPuax94pj_kXJUYvzPH-cxfzrj5da-69HBzkHlSDdA1VH6fmZQg_khe8UXdZAaLuRW-zSZuJAZY_hgISaYfJ_FHn1FV2Gg7tF47lfvurTr5gS45qdQhkLCnSnhZPBsFmas_LrbpwDwAhBj_j_HkQpUuBhLeunodPaFnFGdI17dAK0GMr9H51btjzyRjrsHV5WEtgUiKJ3I1c17g_tvdr5dL9PdS343KZ1W5JUiY9YxaywSw_L183JWYAUAUi4RR6QQYKenSAooSsDzp0Hh8weMDQy2TqCV1YRBTGO-kZVloAhDDjZkX43f3qi9PdBmwqMeQr4raCMtFy5rO1osMjza91eRe99uPE-CiY3P3IF0Ak4sC7nVsC8JCxMXkWX6Vcu5Q7T1E6imgpXxRVMBPRaMDlDh");
                get.setUrl(url);
                get.setVerb(Constants.HTTP_GET);


                final SignalRFuture<String> negotiationFuture = new SignalRFuture<String>();

                HttpConnection mHttpConnection =new AndroidHttpConnection(Logger);

                mHttpConnection.execute(get, new HttpConnectionFuture.ResponseCallback() {
                    @Override
                    public void onResponse(Response response) throws Exception {
                        Logger.log(response.readToEnd(),LogLevel.Critical);
                    }
                });
            }
        });

        myCommonHub.on("getMessage", new SubscriptionHandler1<Object>() {
            @Override
            public void run(Object p1) {
                Logger.log("收到消息："+p1,LogLevel.Information);
            }
        },Object.class);
        hubConnection.stateChanged(new StateChangedCallback() {
            @Override
            public void stateChanged(ConnectionState oldState, ConnectionState newState) {
                Logger.log("stateChanged,oldState:"+oldState.name()+",newState:"+newState,LogLevel.Information);
            }
        });
        try {
            hubConnection.start(new WebsocketTransport(new SimpleLog()) {
            }).get();

            Void register = myCommonHub.invoke("register").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    SimpleLog Logger = new SimpleLog();
    public class SimpleLog implements Logger {

        @Override
        public void log(String message, LogLevel level) {
            Log.d(TAG,message);
        }
    }

    @Override
    protected void onDestroy() {
        Logger.log("断开连接",LogLevel.Information);
        hubConnection.disconnect();
        super.onDestroy();
    }
}
