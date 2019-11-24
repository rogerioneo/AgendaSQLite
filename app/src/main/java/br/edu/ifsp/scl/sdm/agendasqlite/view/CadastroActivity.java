package br.edu.ifsp.scl.sdm.agendasqlite.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
            String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

            Contato contato = new Contato(nome, fone, fone2, email);
            contato.setId((int) dao.incluirContato(contato));

            MainActivity.adapter.adicionaContatoAdapter(contato);

            Toast.makeText(getApplicationContext(), getResources().getText(R.string.contato) +" "+
                            contato.getNome()+ " " + getResources().getText(R.string.incluido),
                    Toast.LENGTH_LONG).show();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
