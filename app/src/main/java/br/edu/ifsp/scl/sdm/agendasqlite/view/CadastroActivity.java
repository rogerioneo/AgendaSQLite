package br.edu.ifsp.scl.sdm.agendasqlite.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifsp.scl.sdm.agendasqlite.R;
import br.edu.ifsp.scl.sdm.agendasqlite.data.ContatoDAO;
import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_salvarContato) {
            ContatoDAO dao = new ContatoDAO(this);
            String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
            String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

            Contato contato = new Contato(nome, fone, email);
            dao.incluirContato(contato);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
