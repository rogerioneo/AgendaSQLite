package br.edu.ifsp.scl.sdm.agendasqlite.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.scl.sdm.agendasqlite.R;
import br.edu.ifsp.scl.sdm.agendasqlite.data.ContatoAdapter;
import br.edu.ifsp.scl.sdm.agendasqlite.data.ContatoDAO;
import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato;

public class MainActivity extends AppCompatActivity {

    List<Contato> contatos = new ArrayList<>();
    ContatoDAO dao;
    static ContatoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new ContatoDAO(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        contatos = dao.listaContatos();
        adapter = new ContatoAdapter(contatos);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new ContatoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final Contato contato = adapter.getContactListFiltered().get(position);
                Intent i = new Intent(getApplicationContext(), DetalheActivity.class);
                i.putExtra("contato", contato);
                startActivityForResult(i,2);
            }
        });

        //Efeito deslizando o item para os lados, o resultado disso é apagar o contato
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                0,                  //Para mover para cima ou para baixo        (ItemTouchHelper.UP | ItemTouchHelper.DOWN)
                ItemTouchHelper.RIGHT) {    //Para mover para a esquerda ou direita     (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contato c = adapter.getContactListFiltered().get(viewHolder.getAdapterPosition());
                dao.excluirContato(c);
                adapter.apagaContatoAdapter(c);
                Toast.makeText(getApplicationContext(), "Contato "+c.getNome()+" excluído",
                        Toast.LENGTH_LONG).show();
            }

            //Exibe uma imagem e muda a cor de fundo quando esta deslizando
            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                Paint p = new Paint();

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                p.setColor(ContextCompat.getColor(getBaseContext(), android.R.color.holo_orange_light));
                RectF background = new RectF((float) itemView.getLeft(),
                        (float) itemView.getTop(), dX, (float) itemView.getBottom());
                c.drawRect(background, p);
                icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width,
                        (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, null);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CadastroActivity.class);
                startActivityForResult(i,1);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView;
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
