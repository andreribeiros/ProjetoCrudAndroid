package br.com.adsddm.cadastromedico.DAO;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.adsddm.cadastromedico.MainActivity;

public class configuracaoWebService {
    private int porta;
    private String ip;


    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public configuracaoWebService(String porta, String ip) throws Exception{
        this.ip = ip;
        try{
            this.porta = Integer.parseInt(porta);
        }catch (Exception e){
            throw  new Exception("A porta deve ser um numero");

        }
    }

    public configuracaoWebService(){

    }


    public void getShaed(){
        Context a = MainActivity.getAppCont();
        SharedPreferences sh = a.getSharedPreferences("config",a.MODE_PRIVATE);
        configuracaoWebService conf = new configuracaoWebService();
        this.setIp(sh.getString("ip",""));
        this.setPorta(sh.getInt("porta",0));
    }
}
