package uk.domdudley.mvp_api_test.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import uk.domdudley.mvp_api_test.ItemClickListener;
import uk.domdudley.mvp_api_test.R;
import uk.domdudley.mvp_api_test.models.Movie;

/**
 * Created by Dom on 26/11/2016.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private final RealmResults<Movie> collection;
    private final ItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View view;

        TextView movieTitle;
        TextView releaseDate;
        TextView certificate;
        public ViewHolder(View v) {
            super(v);
            view = v;
            view.setOnClickListener(this);
            movieTitle = (TextView)v.findViewById(R.id.card_movietitle);
            releaseDate = (TextView)v.findViewById(R.id.card_movierelease);
            certificate = (TextView)v.findViewById(R.id.card_certificate);
        }

        @Override
        public void onClick(View v) {
            if(listener != null)
                listener.onItemClick(v, getAdapterPosition());
        }
    }


    public CollectionAdapter(final RealmResults<Movie> collection, ItemClickListener listener) {
        this.collection = collection;
        this.listener = listener;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_card,parent,false);
        CollectionAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionAdapter.ViewHolder holder, int position) {
        holder.movieTitle.setText(collection.get(position).getTitle());
        holder.releaseDate.setText(collection.get(position).getReleased());
        holder.certificate.setText(collection.get(position).getRated());
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }
}
