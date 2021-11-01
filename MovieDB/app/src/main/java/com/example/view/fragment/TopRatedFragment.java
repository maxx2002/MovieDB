package com.example.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.NowPlayingAdapter;
import com.example.adapter.PopularAdapter;
import com.example.adapter.TopRatedAdapter;
import com.example.helper.ItemClickSupport;
import com.example.model.NowPlaying;
import com.example.model.Popular;
import com.example.model.TopRated;
import com.example.moviedb.R;
import com.example.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopRatedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopRatedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopRatedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopRatedFragment newInstance(String param1, String param2) {
        TopRatedFragment fragment = new TopRatedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        dialog.show();
    }

    private RecyclerView rv_top_rated;
    private MovieViewModel view_model;
    private ProgressDialog dialog;

    private TopRatedAdapter adapter;
    private Integer page = 1;
    private Boolean load = false;
    private List<TopRated.Results> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);

        rv_top_rated = view.findViewById(R.id.rv_top_rated);
        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);

        adapter = new TopRatedAdapter(getActivity());

        view_model.getTopRated(page);
        view_model.getResultGetTopRated().observe(getActivity(), showTopRated);

        return view;
    }

    private Observer<TopRated> showTopRated = new Observer<TopRated>() {

        @Override
        public void onChanged(TopRated topRated) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);

            if (page == 1) {
                rv_top_rated.setLayoutManager(layoutManager);
                adapter.setListTopRated(topRated.getResults());
                list.addAll(topRated.getResults());
                rv_top_rated.setAdapter(adapter);
            } else {
                list.add(null);
                adapter.setListTopRated(list);
                adapter.notifyItemInserted(list.size() -1);
                list.remove(list.size() -1);
                list.addAll(topRated.getResults());
                load = false;
            }

            dialog.dismiss();

            rv_top_rated.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv_top_rated.getLayoutManager();

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size() -2) {
                        page++;
                        load = true;

                        if (page > 1) {
                            view_model.getTopRated(page);
                            view_model.getResultGetTopRated().observe(getActivity(), showTopRated);
                        }
                    }
                }

            });

            ItemClickSupport.addTo(rv_top_rated).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    return false;
                }
            });

            ItemClickSupport.addTo(rv_top_rated).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", "" + topRated.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_popularFragment2_to_MovieDetailFragment);
                }
            });
        }
    };
}