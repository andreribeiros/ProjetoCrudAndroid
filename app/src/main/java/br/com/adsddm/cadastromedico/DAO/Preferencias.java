package br.com.adsddm.cadastromedico.DAO;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    public static final String ID_PREF = "alunos";

    public static void setString(Context context, String chave, String valor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static String getString(Context context, String chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        String s = sharedPreferences.getString(chave, "");
        return s;
    }

    public static void setBoolean(Context context, String chave, Boolean valor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(chave, valor);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        Boolean b = sharedPreferences.getBoolean(chave, true);
        return b;
    }

    public static void setInteger(Context context, String chave, int valor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(chave, valor);
        editor.commit();
    }

    public static int getInteger(Context context, String chave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ID_PREF, 0);
        int i = sharedPreferences.getInt(chave, 0);
        return i;
    }
}

