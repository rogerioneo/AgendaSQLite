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

public class DetalheActivity extends AppCompatActivity {

    Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        if (getIntent().hasExtra("contato")) {
            this.contato = (Contato)getIntent().getSerializableExtra("contato");

            EditText nome = findViewById(R.id.editTextNome);
            nome.setText(contato.getNome());
            EditText fone = findViewById(R.id.editTextFone);
            fone.setText(contato.getFone());
            EditText email = findViewById(R.id.editTextEmail);
            email.setText(contato.getFone());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        ContatoDAO dao = new ContatoDAO(this);
        String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        contato.setNome(nome);
        contato.setFone(fone);
        contato.setEmail(email);

        switch (item.getItemId()){
            case R.id.action_salvarContato:
                dao.alterarContato(contato);
                MainActivity.adapter.atualizaContatoAdapter(contato);
                finish();
                break;
            case R.id.action_excluirContato:
                dao.excluirContato(contato);
                MainActivity.adapter.apagaContatoAdapter(contato);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
