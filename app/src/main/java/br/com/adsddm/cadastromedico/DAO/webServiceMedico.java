package br.com.adsddm.cadastromedico.DAO;

import android.widget.Toast;

import java.io.IOException;

import br.com.adsddm.cadastromedico.DAO.configuracaoWebService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class webServiceMedico {

    private String url;

    private String res = "";
    private String json;
    public String sendRequest() {
        try {
            OkHttpClient client = new OkHttpClient();
            final Request req;
            if (json ==null) {
                req  = new Request.Builder().get().url(url).build();
                client.newCall(req).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            res = response.body().string();

                        }
                    }
                });
            }else{
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                req = new Request.Builder().post(body).url(url).build();

                client.newCall(req).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            res = response.body().string();

                        }
                    }
                });
            }
            Thread.sleep(2000);

            return res;
        }catch (Exception e){

        }
        return res;
    }

    public String delete() {
        try {
            OkHttpClient client = new OkHttpClient();
            final Request req;

            req  = new Request.Builder().delete().url(url).build();
            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        res = response.body().string();

                    }
                }
            });

            Thread.sleep(2000);
            return res;
        }catch(Exception e){
            throw  new RuntimeException();
        }
    }

    public webServiceMedico(configuracaoWebService confi,String operation,String jsons){
        url  = confi.getIp() + ":";
        url+=  String.valueOf(confi.getPorta()) + ""+operation;
        this.json = jsons;

    }

    public webServiceMedico(configuracaoWebService confi,String operation){
        url  = confi.getIp() + ":";
        url+=  String.valueOf(confi.getPorta()) + ""+operation;

    }

}


