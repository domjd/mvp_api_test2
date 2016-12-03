package io.github.domjd.mvp_api_test.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.github.domjd.mvp_api_test.tools.ItemClickListener;
import io.github.domjd.mvp_api_test.R;
import io.github.domjd.mvp_api_test.models.Movie;
import io.github.domjd.mvp_api_test.tools.ItemTouchHelperAdapter;
import io.github.domjd.mvp_api_test.tools.PosterBitmap;

/**
 * Created by Dom on 26/11/2016.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private final RealmResults<Movie> collection;
    private final ItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View view;

        TextView movieTitle;
        TextView releaseDate;
        TextView certificate;

        ImageView moviePoster;

        public ViewHolder(View v) {
            super(v);
            view = v;
            view.setOnClickListener(this);
            //movieTitle = (TextView)v.findViewById(R.id.card_movietitle);
            releaseDate = (TextView)v.findViewById(R.id.card_movierelease);
            certificate = (TextView)v.findViewById(R.id.card_certificate);

            movieTitle = (TextView)v.findViewById(R.id.movieName_grid);
            moviePoster = (ImageView)v.findViewById(R.id.moviePoster);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_card,parent,false);
        CollectionAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionAdapter.ViewHolder holder, int position) {
/*        holder.movieTitle.setText(collection.get(position).getTitle());
        holder.releaseDate.setText(collection.get(position).getReleased());
        holder.certificate.setText(collection.get(position).getRated());*/

        holder.movieTitle.setText(collection.get(position).getTitle());
        new PosterBitmap(holder.moviePoster).execute(collection.get(position).getPoster());
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemRemoved(int position) {
        Realm realm;
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        collection.deleteFromRealm(position);
        realm.commitTransaction();
        notifyItemRemoved(position);
    }
}