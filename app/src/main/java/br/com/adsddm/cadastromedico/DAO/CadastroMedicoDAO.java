package br.com.adsddm.cadastromedico.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class CadastroMedicoDAO extends SQLiteOpenHelper {
    private static final String NOME_BD = "medico.db";
    private static final int VERSAO_BD = 1;

    public CadastroMedicoDAO(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists medico " +
                "(_id integer primary key autoincrement, crm text, nome text, uf text, telefone text, foto BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVesion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists medico");
        onCreate(sqLiteDatabase);
    }

    public void FecharConexao(){
        SQLiteDatabase db = getWritableDatabase();
        db.close();
    }

    public long save(Medico medico) {
        Long id = medico.getId();
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("crm", medico.getCrm());
            values.put("nome", medico.getNome());
            values.put("uf", medico.getUf());
            values.put("telefone", medico.getTelefone());

            if (id != 0) {
                String _id = String.valueOf(medico.getId());
                String[] where = new String[]{_id};
                int contador = db.update("medico", values, "_id=?", where);
                return contador;
            }
            else {
                id = db.insert("medico", "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    public int delete(Medico medico) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            int contador = db.delete("medico", "_id=?",
                    new String[]{String.valueOf(medico.getId())});
            return contador;
        } finally {
            db.close();
        }
    }

    public Medico findById(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        Medico medico = null;

        try {
            Cursor cursor = db.query("medico", new String[]{"_id", "crm", "nome", "uf", "telefone"},
                    "_id=?", new String[]{id.toString()},
                    null, null, null);
            if (cursor.getCount() > 0) {
                medico = new Medico();
                cursor.moveToFirst();
                medico.setId(cursor.getLong(0));
                medico.setCrm(cursor.getString(1));
                medico.setNome(cursor.getString(2));
                medico.setUf(cursor.getString(3));
                medico.setTelefone(cursor.getString(4));
            }
        } finally {
            db.close();
        }

        return medico;
    }

    public boolean ExisteCrm(String crm) {
        SQLiteDatabase db = getWritableDatabase();
        boolean existe = false;

        try {
            Cursor cursor = db.query("medico", new String[]{"_id", "crm", "nome", "uf", "telefone"},
                    "crm=?", new String[]{crm}, null, null, null);
            existe = cursor.getCount() > 0;
        } finally {
            db.close();
        }

        return existe;
    }

    public Medico findByCrm(String crm) {
        SQLiteDatabase db = getWritableDatabase();
        Medico medico = null;

        try {
            Cursor cursor = db.query("medico", new String[]{"_id", "crm", "nome", "uf", "telefone"},
                    "crm=?", new String[]{crm}, null, null, null);
            if (cursor.getCount() > 0) {
                medico = new Medico();
                cursor.moveToFirst();
                medico.setId(cursor.getLong(0));
                medico.setCrm(cursor.getString(1));
                medico.setNome(cursor.getString(2));
                medico.setUf(cursor.getString(3));
                medico.setTelefone(cursor.getString(4));
            }
        } finally {
            db.close();
        }

        return medico;
    }

    public List<Medico> findAll() {
        SQLiteDatabase db = getWritableDatabase();

        try {
            Cursor cursor = db.query("medico", null, null, null, null, null, null);
            return toList(cursor);
        } finally {
            db.close();
        }
    }

    public Cursor findAllCursor() {
        SQLiteDatabase db = getWritableDatabase();
        final String campos[] = {"_id", "crm", "nome"};

        try {
            Cursor cursor = db.query("medico", campos, null, null, null, null, null);
            return cursor;
        } finally {
            db.close();
        }
    }

    private List<Medico> toList(Cursor cursor) {
        List<Medico> medicos = new ArrayList<Medico>();

        if (cursor.moveToFirst()) {
            do {
                Medico medico = new Medico();
                medicos.add(medico);

                medico.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                medico.setCrm(cursor.getString(cursor.getColumnIndex("crm")));
                medico.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                medico.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                medico.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            } while (cursor.moveToNext());
        }
        return medicos;
    }
}
