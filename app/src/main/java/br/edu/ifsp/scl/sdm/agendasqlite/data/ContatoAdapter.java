package br.edu.ifsp.scl.sdm.agendasqlite.data;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifsp.scl.sdm.agendasqlite.R;
import br.edu.ifsp.scl.sdm.agendasqlite.model.Contato;

public class ContatoAdapter
        extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>
        implements Filterable {

    static String TAG = "ContatoAdatapter";

    static List<Contato> contatos;
    List<Contato> contactListFiltered;

    private static ItemClickListener clickListener;

    private void sort() {
        Collections.sort(contatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato c1, Contato c2) {
                return c1.getNome().compareTo(c2.getNome());
            }
        });
    }

    public void adicionaContatoAdapter(Contato contato){
        contatos.add(0,contato);
        this.sort();
        notifyDataSetChanged();
    }

    public void atualizaContatoAdapter(Contato contato) {
        contatos.set(contatos.indexOf(contato), contato);
        this.sort();
        notifyItemChanged(contatos.indexOf(contato));
    }

    public void apagaContatoAdapter(Contato contato) {
        int pos = contatos.indexOf(contato);
        contatos.remove(pos);
        notifyItemRemoved(pos);
    }

    public void favoritarContatoAdapter(Contato contato) {
        int pos = contatos.indexOf(contato);
        contatos.get(pos).setFavorito(contato.getFavorito());
        notifyItemChanged(pos);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        clickListener = itemClickListener;
    }

    public ContatoAdapter(List<Contato> contatos) {

        this.contatos = contatos;
        this.contactListFiltered = contatos;
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
        holder.nome.setText(contactListFiltered.get(position).getNome());
        if (contactListFiltered.get(position).getFavorito() == 1)
            holder.favorito.setImageResource(android.R.drawable.btn_star_big_on);
        else
            holder.favorito.setImageResource(android.R.drawable.btn_star_big_off);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contatos;
                } else {
                    List<Contato> filteredList = new ArrayList<>();
                    for (Contato row : contatos) {
                        if (row.getNome().toLowerCase().contains(charString.toLowerCase()) ||
                            row.getEmail().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Contato>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<Contato> getContactListFiltered(){
        return contactListFiltered;
    }

    public class ContatoViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final TextView nome;
        final ImageButton favorito;
        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            favorito = itemView.findViewById(R.id.favorite);
            itemView.setOnClickListener(this);
            favorito.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Log.i(TAG, "setClickListener: " + view.getClass().getSimpleName());
            Evento evento = Evento.Editar;
            if (clickListener != null)
                if (view.getId() == R.id.favorite) evento = Evento.Favoritar;
                clickListener.onItemClick(evento, getAdapterPosition());
        }
    }

    public enum Evento {
        Editar, Favoritar;
    }

    public interface ItemClickListener {
        void onItemClick(Evento evento, int position);
    }

}
