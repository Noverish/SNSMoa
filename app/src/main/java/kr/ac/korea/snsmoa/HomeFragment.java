package kr.ac.korea.snsmoa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.korea.intelligentgallery.R;

/**
 * Created by Noverish on 2017-03-21.
 */

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        CustomRecyclerView recyclerView = (CustomRecyclerView) view.findViewById(R.id.activity_home_recycler_view);

        return view;
    }
}
