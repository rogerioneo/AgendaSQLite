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
            EditText fone2 = findViewById(R.id.editTextFone2);
            fone2.setText(contato.getFone2());
            EditText email = findViewById(R.id.editTextEmail);
            email.setText(contato.getEmail());
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

        switch (item.getItemId()){
            case R.id.action_salvarContato:
                String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
                String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
                String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
                String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

                contato.setNome(nome);
                contato.setFone(fone);
                contato.setFone2(fone2);
                contato.setEmail(email);

                dao.alterarContato(contato);
                MainActivity.adapter.atualizaContatoAdapter(contato);

                Toast.makeText(getApplicationContext(), getResources().getText(R.string.contato) +" "+
                                contato.getNome()+ " " + getResources().getText(R.string.alterado),
                        Toast.LENGTH_LONG).show();

                finish();
                break;
            case R.id.action_excluirContato:
                dao.excluirContato(contato);
                MainActivity.adapter.apagaContatoAdapter(contato);
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.contato) +" "+
                                contato.getNome()+ " " + getResources().getText(R.string.excluido),
                        Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
