package br.edu.ifsp.scl.sdm.agendasqlite.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.scl.sdm.agendasqlite.R;
import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    List<Contato> contatos;

    public ContatoAdapter(List<Contato> contatos) {
        this.contatos = contatos;
    }

    // onCreateViewHolder é responsável por inflar o layout
    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.contato_celula, parent, false);
        return new ContatoViewHolder(view);
    }

    //Atribuir o nome do contato no layout inflado
    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        holder.nome.setText(contatos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder {
        final TextView nome;
        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
        }
    }

}
