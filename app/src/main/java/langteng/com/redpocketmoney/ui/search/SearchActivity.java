package langteng.com.redpocketmoney.ui.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import langteng.com.baselib.baseui.LibBaseActivity;
import langteng.com.baselib.utils.Logger;
import langteng.com.baselib.utils.Tools;
import langteng.com.redpocketmoney.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JHS on 2015/8/25.
 */
public class SearchActivity extends LibBaseActivity {


    private ListView searchLv;
    private SearchQuestionAdapter adapter;
    private List<SearchInfoModel.ResponseBean.DocsBean> docsBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        BaseSetContentView(R.layout.search_activity);
        initView();
    }

    private void initView() {
        setTopbarName("搜索");
        final EditText editText = (EditText) findViewById(R.id.search_edt);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    getDateonRun(editText.getText().toString(), editText);
                    return true;
                }
                return false;
            }
        });
        adapter = new SearchQuestionAdapter(this, docsBeanList);
        searchLv = (ListView) findViewById(R.id.search_lv);
        findViewById(R.id.search_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void getDateonRun(final String keyword, View view) {
        Tools.hideSoftInputFromWindow(getApplication(), view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDate(keyword);
            }
        }).start();
    }

    private void getDate(String keyword) {
        String url = "http://10.0.0.207:10013/solr/mynode/select?indent=on&q=" + keyword +
                "&wt=json";
        /*建立HTTP Get对象*/
        HttpGet httpRequest = new HttpGet(url);
        try {
          /*发送请求并等待响应*/
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
          /*若状态码为200 ok*/
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String strResult = EntityUtils.toString(httpResponse.getEntity());
                Gson gson = new Gson();

                SearchInfoModel model = gson.fromJson(
                        strResult, SearchInfoModel.class);
                docsBeanList.clear();
                if (model.response != null && model.response.docs != null) {
                    docsBeanList.addAll(model.response.docs);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                Logger.i("----strResult--", "strResult:" + strResult);
            } else {
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }


    }


    private void postJson(String keyword) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        JSONObject object = new JSONObject();
        try {
            object.put("indent", "on");
            object.put("q", keyword);
            object.put("wt", "json");
            RequestBody requestBody = RequestBody.create(MediaType.parse(object.toString()),
                    object.toString());
            //创建一个请求对象
            Request request = new Request.Builder()
                    .url("http://192.168.0.102:8080/TestProject/JsonServlet")
                    .post(requestBody)
                    .build();
            //发送请求获取响应
            try {
                Response response = okHttpClient.newCall(request).execute();
                //判断请求是否成功
                if (response.isSuccessful()) {
                    //打印服务端返回结果
                    Logger.i("-------response--", "response: " + response.message());
                }
            } catch (IOException e) {
                Logger.i("-------response--", "IOException: " + e.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
