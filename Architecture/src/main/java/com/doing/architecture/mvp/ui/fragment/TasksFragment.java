package com.doing.architecture.mvp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.doing.architecture.R;
import com.doing.architecture.mvp.ui.widget.ScrollChildSwipeRefreshLayout;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-13.
 */

public class TasksFragment extends Fragment{

    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;
    private View mContainer;
    private LinearLayout mTaskLL;
    private ListView mTaskList;
    private LinearLayout mNoTaskLL;

    public static Fragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(View root, Bundle savedInstanceState) {
        mSwipeRefreshLayout = ((ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout));
        mContainer = root.findViewById(R.id.tasksContainer);
        mTaskLL = ((LinearLayout) root.findViewById(R.id.tasksLL));
        mTaskList = ((ListView) root.findViewById(R.id.tasks_list));
        mNoTaskLL = ((LinearLayout) root.findViewById(R.id.noTasks));

        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefreshLayout.setChildScrollView(mTaskList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:

                break;

            case R.id.menu_filter:

                break;

            case R.id.menu_refresh:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
