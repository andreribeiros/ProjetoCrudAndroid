package br.com.adsddm.cadastromedico;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.adsddm.cadastromedico.DAO.Medico;
import br.com.adsddm.cadastromedico.DAO.CadastroMedicoDAO;
import br.com.adsddm.cadastromedico.DAO.Preferencias;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class visaoCadastroMedico extends AppCompatActivity {
    private Medico medico;
    private CadastroMedicoDAO medicoDAO = new CadastroMedicoDAO(this);
    private TextView etCrm;
    private TextView etNome;
    private TextView etUF;
    private TextView etTetelefone;
    private ListView listView;
    private static final String campos[] = {"matricula", "nome", "_id"};
    private CursorAdapter dataSource;
    private AlertDialog.Builder dialog;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10;
    private Long idMedico;
    private Bitmap img;
    private List<Medico> listaMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visao_cadastro_medico);
        etCrm = (TextView) findViewById(R.id.idCRM);
        etNome = (TextView) findViewById(R.id.idNome);
        etNome.requestFocus();
        etTetelefone = (TextView) findViewById(R.id.idTelefone);
        etUF = (TextView) findViewById(R.id.idUF);
        listView = (ListView) findViewById(R.id.listView1);
        findViewById(R.id.btnSalvar).requestFocus();
        Button remover = (Button) findViewById(R.id.btnExcluir);

        /*Metodo para que ao clicar carregar os edits com os dados dos medicos*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitMedico retrofitMedico = RetrofitMedico.retrofit.create(RetrofitMedico.class);
                final Call<Medico> call = retrofitMedico.listaMedicoUnico(listaMedico.get(i).getId());
                call.enqueue(new Callback<Medico>() {
                    @Override
                    public void onResponse(Call<Medico> call, Response<Medico> response) {
                        int code = response.code();
                        if (code == 200) {
                            Medico medico = response.body();
                            etNome.requestFocus();
                            etNome.setText(medico.getNome());
                            etTetelefone.setText(medico.getTelefone());
                            etCrm.setText(medico.getCrm());
                            etUF.setText(medico.getUf());
                            idMedico = medico.getId();

                        } else {
                            Toast.makeText(getBaseContext(), "Falha: " + String.valueOf(code), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Medico> call, Throwable t) {

                    }
                });
            }
        });

        remover.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RetrofitMedico retrofitMedico = RetrofitMedico.retrofit.create(RetrofitMedico.class);
                final Call<Medico> call = retrofitMedico.listaMedicoUnico(listaMedico.get(i).getId());
                call.enqueue(new Callback<Medico>() {
                    @Override
                    public void onResponse(Call<Medico> call, Response<Medico> response) {
                        int code = response.code();
                        if (code == 200) {
                            Medico medico = response.body();
                            etNome.requestFocus();
                            etNome.setText(medico.getNome());
                            etTetelefone.setText(medico.getTelefone());
                            etCrm.setText(medico.getCrm());
                            etUF.setText(medico.getUf());
                            idMedico = medico.getId();

                        } else {
                            Toast.makeText(getBaseContext(), "Falha: " + String.valueOf(code), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Medico> call, Throwable t) {

                    }
                });
            }
        });


    public void ConsultarMedicosWebService(View view) {
        RetrofitMedico medicosREST = RetrofitMedico.retrofit.create(RetrofitMedico.class);
        final ProgressDialog dialog;
        dialog = new ProgressDialog(visaoCadastroMedico.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        final Call<List<Medico>> call = medicosREST.listaMedicos();

        call.enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
                listaMedico = response.body();
                if(dialog.isShowing())
                    dialog.dismiss();
                if(listaMedico != null) {
                    ArrayAdapter<Medico> adapter = new ArrayAdapter<>(visaoCadastroMedico.this, android.R.layout.simple_list_item_1,listaMedico);
                listView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<List<Medico>> call, Throwable t) {
                if(dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "Problema de acesso", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void Salvar(View view) {
        String vCrm = etCrm.getText().toString();
        String vNome = etNome.getText().toString();
        String vTelefone = etTetelefone.getText().toString();
        String vUf = etUF.getText().toString();
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imagemBytes[] = stream.toByteArray();*/

        if (vCrm.isEmpty() || vUf.isEmpty()) {
            Toast.makeText(this, getString(R.string.dadosnaoinformados), Toast.LENGTH_SHORT).show();
            return;
        }

        medico = new Medico();
        medico.setCrm(vCrm);
        medico.setNome(vNome);
        medico.setTelefone(vTelefone);
        medico.setUf(vUf);
       // medico.setFoto(imagemBytes);

        if (medicoDAO.ExisteCrm(vCrm)) {
            medico.setId(idMedico);
            medicoDAO.save(medico);
            Toast.makeText(this, getString(R.string.medicoauterado), Toast.LENGTH_SHORT).show();
        }
        else {
            SalvarMedicoWebService();
            medico.setId(0L);

            if (medicoDAO.save(medico) > 0) {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, getString(R.string.erroaogravardadosdoaluno), Toast.LENGTH_LONG).show();
            }
        }
        limpaCampos();
        atualizaLista();
    }


    public void Consultar(View view) {
        String vCrm = etCrm.getText().toString();

        if (vCrm.isEmpty()) {
            atualizaLista();
            return;
        }

        medico = medicoDAO.findByCrm(vCrm);
        if (medico == null) {
            Toast.makeText(this, getString(R.string.nenhumalunocomestamatricula), Toast.LENGTH_SHORT).show();
        }
        else {
            etCrm.setText(medico.getCrm());
            etUF.setText(medico.getUf());
            etTetelefone.setText(medico.getTelefone());
            etNome.setText(medico.getNome());
            Toast.makeText(this, getString(R.string.alunoencontrado), Toast.LENGTH_SHORT).show();
        }
    }

    public void Deletar2 (View view) {
        String vCrm = etCrm.getText().toString();
        String vNome = etNome.getText().toString();
        String vTelefone = etTetelefone.getText().toString();
        String vUf = etUF.getText().toString();

        medico = new Medico();
        medico.setCrm(vCrm);
        medico.setNome(vNome);
        medico.setTelefone(vTelefone);
        medico.setUf(vUf);
        medico.setId(idMedico);
        DeletarMedicoWebService();
    };

    public void atualizaLista() {
        List<Medico> medico = medicoDAO.findAll();

        if (medico.size() == 0) {
            LimparLista();
            Toast.makeText(this, getString(R.string.nenhumalunocadastrado), Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<Medico> adapter = new ArrayAdapter<Medico>(this,
                android.R.layout.simple_list_item_1, medico);
        listView.setAdapter(adapter);

    }

    public void LimparLista() {
        List<Medico> medico = medicoDAO.findAll();

        medico.clear();
        ArrayAdapter<Medico> adapter = new ArrayAdapter<Medico>(this,
                android.R.layout.simple_list_item_1, medico);
        listView.setAdapter(adapter);

    }
    public void clickSair(View view) {
        onBackPressed();
    }

    private void limpaCampos() {
        etTetelefone.setText("");
        etNome.setText("");
        etUF.setText("");
        etCrm.setText("");
    }

    /*Esse metodo limpa todos os campos*/
    public void Retornar(View view) {
        limpaCampos();
        LimparLista();
        medicoDAO.FecharConexao();
        etNome.requestFocus();
    }

    public void SalvarPreferencias() {
        Preferencias.setBoolean(this, "cbListar", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle("Confirmação");
        alert.setMessage("Sair do aplicativo ?");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        alert.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        alert.show();
    }

    public void TirarFoto(View view) {
        Intent inten = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(inten,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {

        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                img= (Bitmap) bundle.get("data");

                ImageView imgMedico = (ImageView) findViewById(R.id.imgMedico);
                imgMedico.setImageBitmap(img);
            }

        }
    }

    public void SalvarMedicoWebService () {

        final ProgressDialog dialog;
        dialog = new ProgressDialog(visaoCadastroMedico.this);

        dialog.setMessage("Salvando...");
        dialog.setCancelable(false);
        dialog.show();

        RetrofitMedico medicosREST = RetrofitMedico.retrofit.create(RetrofitMedico.class);
        final Call<Void> call = medicosREST.salvarMedico(medico);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "medico inserido com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeletarMedicoWebService(){

        final ProgressDialog dialog;
        dialog = new ProgressDialog(visaoCadastroMedico.this);

        dialog.setMessage("Deletando...");
        dialog.setCancelable(false);
        dialog.show();

        RetrofitMedico medicosREST = RetrofitMedico.retrofit.create(RetrofitMedico.class);
        Call<Void> call = medicosREST.deletarMedico(medico);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "medico inserido com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
