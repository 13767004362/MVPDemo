package com.xingen.mvppractice.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.xingen.mvppractice.R;
import com.xingen.mvppractice.data.entity.Movie;
import com.xingen.mvppractice.data.source.remote.volley.VolleySingle;
import com.xingen.mvppractice.ui.recyclerview.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieListAdapter  extends BaseRecyclerViewAdapter< List<Movie>, MovieListAdapter.ViewHolder> {
    private List<Movie> moviesList;
    private List<Movie> moviesCollecion;
   public MovieListAdapter(){
       this.moviesList=new ArrayList<>();
       this.moviesCollecion=new ArrayList<>();
   }
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(),R.layout.item_movielist,null);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean check=  viewHolder.getCheckBox().isChecked();
                Movie movie=moviesList.get(viewHolder.getLayoutPosition());
                if(check){
                    moviesCollecion.add(movie);
                }else{
                    moviesCollecion.remove(movie);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
            Movie movie=moviesList.get(position);
            boolean isChecked=false;
            if(moviesCollecion.contains(movie)){
                isChecked=true;
            }
            holder.getCheckBox().setChecked(isChecked);
            NetworkImageView networkImageView=holder.getNetworkImageView();
            networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
            networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
            networkImageView.setImageUrl(movie.getImages().getLarge(), VolleySingle.getInstance().getImageLoader());

           holder.getTitle().setText(movie.getTitle());
           holder.getYear().setText(movie.getYear()+"年");
    }

    @Override
    public int getItemCount() {
        return this.moviesList.size();
    }
    @Override
    public void upData(List<Movie> list) {
        this.moviesList.clear();
        this.moviesList.addAll(list);
        this.notifyDataSetChanged();
    }

    public List<Movie> getMoviesCollecion() {
        return moviesCollecion;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private NetworkImageView networkImageView;
        private TextView title,year;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            this.networkImageView=(NetworkImageView) itemView.findViewById(R.id.item_movielist_iv);
            this.title=(TextView)itemView.findViewById(R.id.item_movielist_name_tv);
            this.checkBox=(CheckBox) itemView.findViewById(R.id.item_movielist_cb);
            this.year=(TextView) itemView.findViewById(R.id.item_movielist_year_tv);
        }

        public NetworkImageView getNetworkImageView() {
            return networkImageView;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getYear() {
            return year;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }
}
