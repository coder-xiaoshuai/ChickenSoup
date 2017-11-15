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
     * ����get����
     *
     * @param url      ����·��
     * @param callback
     */
    public static void sendGetRequst(String url, ResultCallback callback) {

        // step 2�� ����һ�����󣬲�ָ�����󷽷�ʱĬ����GET��
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * ����delete����
     *
     * @param url
     * @param callback
     */
    public static void sendDeleteRequest(String url, Map<String, String> params, ResultCallback callback) {
        //step 2: ����  FormBody.Builder
        FormBody formBody = mapToFormbody(params);
        //step 3: ��������
        Request request = new Request.Builder().url(url)
                .delete(formBody)
                .build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * ����post����
     *
     * @param url      ����·��
     * @param keys     �������������
     * @param values   ���������Ӧ��ֵ
     * @param callback ����ص�
     */
    public static void sendPostRequest(String url, String[] keys, String[] values, ResultCallback callback) {
        //step 2: ����  FormBody.Builder
        FormBody formBody = arrayToFormbody(keys, values);
        //step 3: ��������
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * �����޲���post����
     *
     * @param url      ����·��
     * @param callback ����ص�
     */
    public static void sendPostRequest(String url, ResultCallback callback) {
        //step 2: ����  FormBody.Builder
        FormBody formBody = new FormBody.Builder().build();
        //step 3: ��������
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * ����post����
     *
     * @param url      ����·��
     * @param params
     * @param callback ����ص�
     */
    public static void sendPostRequest(String url, Map<String, String> params, ResultCallback callback) {
        //step 2: ����  FormBody.Builder
        FormBody formBody = mapToFormbody(params);
        //step 3: ��������
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * ����put����
     *
     * @param url
     * @param callback
     */
    public static void sendPutRequest(String url, String[] keys, String[] values, ResultCallback callback) {
        //step 2: ����  FormBody.Builder
        FormBody formBody = arrayToFormbody(keys, values);
        //step 3: ��������
        Request request = new Request.Builder().url(url)
                .put(formBody)
                .build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * ����put����
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void sendPutRequest(String url, Map<String, String> params, ResultCallback callback) {
        //step 2: ����  FormBody.Builder
        FormBody formBody = mapToFormbody(params);
        //step 3: ��������
        Request request = new Request.Builder().url(url)
                .put(formBody)
                .build();
        //��������ӵ�list�� ����ȡ������
        mRequests.add(callRequest(request, callback));
    }

    /**
     * ��mapת��ΪBody
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
            new Exception("��������Ϊ��").printStackTrace();
        }
        return null;
    }

    /**
     * ��key��value������ת����formbody
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
                new Exception("�����ļ�ֵ�Գ��Ȳ�ͳһ,�����鳤��Ϊ:" + keys.length + ",ֵ���鳤��Ϊ:" + values.length).printStackTrace();
            }
        }
        return null;
    }

	private static  Call callRequest(final Request request, final ResultCallback callback) {
		// step 3������ Call ����
		Call call = mOkhttpClient.newCall(request);
		//step 4: ��ʼ�첽����
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
				//��������ɹ����жϷ�����
				if (callback != null) {
					callback.onSuccess((String) bodyJson);

				}
			}
		});
		return call;
	}

	/**
     * ȡ������
     */
    public static void cancelRequest() {
        for (int i = 0; i < mRequests.size(); i++) {
            mRequests.get(i).cancel();
            //�����Ƴ�
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
     * @param fileUrl      �ϴ��ļ���·��
     * @param serverUrl    ������·��
     * @param needProgress �Ƿ���Ҫ��ȡ�ϴ�����
     * @param listener     �ϴ�����
     */
    public static void upLoadFile(String fileUrl, String serverUrl, boolean needProgress, final UpLoadListener listener) {
        //step 2:���� RequestBody �Լ�����Ĳ���
        //2.1 ��ȡ�ļ�
        File file = new File(fileUrl);
        //2.2 ���� MediaType �����ϴ��ļ�����
        MediaType MEDIATYPE = MediaType.parse("text/plain; charset=utf-8");
        //2.3 ��ȡ������
        RequestBody requestBody = RequestBody.create(MEDIATYPE, file);
        //step 3����������
        Request request = new Request.Builder().url(serverUrl)
                .post(requestBody)
                .build();
        if (listener != null) {
            listener.onStart();
        }

        //step 4 ������ϵ
        mOkhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //�ϴ�ʧ��
                if (listener != null) {
                    listener.onFail(e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //�ϴ��ɹ�
                if (listener != null) {
                    listener.onSuccess();
                }
            }
        });
    }

    /**
     * ���������ϴ�ͼƬ
     *
     * @param url       ��������ַ
     * @param imagekey  ͼƬ��Ӧ��key
     * @param imageName ͼƬ����
     * @param imagePath ͼƬ·��
     * @param keys      ������ֵ��
     * @param values
     * @param callback
     */
    public static void uploadImageWithParams(String url, String imagekey, String imageName, String imagePath, String[] keys, String[] values, ResultCallback callback) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (keys != null && values != null) {
            if (keys.length != values.length) {
                throw new RuntimeException("-----��ֵ�Գ��Ȳ�һ��-----");
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
