package com.chickensoup.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.struts2.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xiaoshuai on 2017/3/27.
 */

public class OkhttpUtils {
	private static final String TAG = "okHttpUtils";
	private static final OkHttpClient mOkhttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();
	private static List<Call> mRequests;
	private static Call mCall;

	static {
		mRequests = new ArrayList<>();
	}

	public interface ResultCallback {
		void onSuccess(String data);

		void onFail(String failCode, String hint);

		void onError(int errorCode, String errorMsg);
	}

	/**
     * 发送get请求
     *
     * @param url      请求路径
     * @param callback
     */
    public static void sendGetRequst(String url, ResultCallback callback) {

        // step 2： 创建一个请求，不指定请求方法时默认是GET。
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 发送delete请求
     *
     * @param url
     * @param callback
     */
    public static void sendDeleteRequest(String url, Map<String, String> params, ResultCallback callback) {
        //step 2: 创建  FormBody.Builder
        FormBody formBody = mapToFormbody(params);
        //step 3: 创建请求
        Request request = new Request.Builder().url(url)
                .delete(formBody)
                .build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 发送post请求
     *
     * @param url      请求路径
     * @param keys     请求参数的数组
     * @param values   请求参数对应的值
     * @param callback 请求回调
     */
    public static void sendPostRequest(String url, String[] keys, String[] values, ResultCallback callback) {
        //step 2: 创建  FormBody.Builder
        FormBody formBody = arrayToFormbody(keys, values);
        //step 3: 创建请求
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 发送无参数post请求
     *
     * @param url      请求路径
     * @param callback 请求回调
     */
    public static void sendPostRequest(String url, ResultCallback callback) {
        //step 2: 创建  FormBody.Builder
        FormBody formBody = new FormBody.Builder().build();
        //step 3: 创建请求
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 发送post请求
     *
     * @param url      请求路径
     * @param params
     * @param callback 请求回调
     */
    public static void sendPostRequest(String url, Map<String, String> params, ResultCallback callback) {
        //step 2: 创建  FormBody.Builder
        FormBody formBody = mapToFormbody(params);
        //step 3: 创建请求
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 发送put请求
     *
     * @param url
     * @param callback
     */
    public static void sendPutRequest(String url, String[] keys, String[] values, ResultCallback callback) {
        //step 2: 创建  FormBody.Builder
        FormBody formBody = arrayToFormbody(keys, values);
        //step 3: 创建请求
        Request request = new Request.Builder().url(url)
                .put(formBody)
                .build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 发送put请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void sendPutRequest(String url, Map<String, String> params, ResultCallback callback) {
        //step 2: 创建  FormBody.Builder
        FormBody formBody = mapToFormbody(params);
        //step 3: 创建请求
        Request request = new Request.Builder().url(url)
                .put(formBody)
                .build();
        //把请求添加到list中 方便取消请求
        mRequests.add(callRequest(request, callback));
    }

    /**
     * 将map转换为Body
     *
     * @param params
     * @return
     */
    private static FormBody mapToFormbody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            return builder.build();
        } else {
            new Exception("参数不能为空").printStackTrace();
        }
        return null;
    }

    /**
     * 将key和value的数组转化成formbody
     *
     * @param keys
     * @param values
     */
    private static FormBody arrayToFormbody(String[] keys, String[] values) {
        FormBody.Builder builder = new FormBody.Builder();
        if (keys != null && values != null) {
            if (keys.length == values.length) {
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    String value = values[i];
                    builder.add(key, value);
                }
                return builder.build();
            } else {
                new Exception("参数的键值对长度不统一,键数组长度为:" + keys.length + ",值数组长度为:" + values.length).printStackTrace();
            }
        }
        return null;
    }

	private static  Call callRequest(final Request request, final ResultCallback callback) {
		// step 3：创建 Call 对象
		Call call = mOkhttpClient.newCall(request);
		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				mRequests.remove(call);
				final String errMsg = e.toString();
			}

			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				mRequests.remove(call);
				final String bodyJson = response.body().string();
				final int resultCode = response.code();
				final String errorMsg = response.message();
				//网络请求成功后判断返回码
				if (callback != null) {
					callback.onSuccess((String) bodyJson);

				}
			}
		});
		return call;
	}

	/**
     * 取消请求
     */
    public static void cancelRequest() {
        for (int i = 0; i < mRequests.size(); i++) {
            mRequests.get(i).cancel();
            //进行移除
            mRequests.remove(i);
        }
    }

	public interface UpLoadListener {
		void onStart();

		void onLoading(long totalBytes, long remainingBytes, boolean done);

		void onFail(String failMsg);

		void onSuccess();
	}

	/**
     * @param fileUrl      上传文件的路径
     * @param serverUrl    服务器路径
     * @param needProgress 是否需要获取上传进度
     * @param listener     上传监听
     */
    public static void upLoadFile(String fileUrl, String serverUrl, boolean needProgress, final UpLoadListener listener) {
        //step 2:创建 RequestBody 以及所需的参数
        //2.1 获取文件
        File file = new File(fileUrl);
        //2.2 创建 MediaType 设置上传文件类型
        MediaType MEDIATYPE = MediaType.parse("text/plain; charset=utf-8");
        //2.3 获取请求体
        RequestBody requestBody = RequestBody.create(MEDIATYPE, file);
        //step 3：创建请求
        Request request = new Request.Builder().url(serverUrl)
                .post(requestBody)
                .build();
        if (listener != null) {
            listener.onStart();
        }

        //step 4 建立联系
        mOkhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //上传失败
                if (listener != null) {
                    listener.onFail(e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功
                if (listener != null) {
                    listener.onSuccess();
                }
            }
        });
    }

    /**
     * 带参数的上传图片
     *
     * @param url       服务器地址
     * @param imagekey  图片对应的key
     * @param imageName 图片名称
     * @param imagePath 图片路径
     * @param keys      其他键值对
     * @param values
     * @param callback
     */
    public static void uploadImageWithParams(String url, String imagekey, String imageName, String imagePath, String[] keys, String[] values, ResultCallback callback) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (keys != null && values != null) {
            if (keys.length != values.length) {
                throw new RuntimeException("-----键值对长度不一致-----");
            } else {
                for (int i = 0; i < keys.length; i++) {
                    builder.addFormDataPart(keys[i], values[i]);
                }
            }
        }

        RequestBody requestBody = builder
                .setType(MultipartBody.FORM)
                .addFormDataPart(imagekey, imageName,
                        RequestBody.create(MEDIA_TYPE_PNG, new File(imagePath)))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        callRequest(request, callback);

    }


}
