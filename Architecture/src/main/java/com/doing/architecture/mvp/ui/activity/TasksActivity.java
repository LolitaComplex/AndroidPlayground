package com.doing.architecture.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.doing.architecture.R;
import com.doing.architecture.mvp.model.local.TasksLocalDataSource;
import com.doing.architecture.mvp.model.remote.FakeTasksRemoteDataSource;
import com.doing.architecture.mvp.model.remote.TasksRepository;
import com.doing.architecture.mvp.presenter.tasks.TasksFilterType;
import com.doing.architecture.mvp.presenter.tasks.TasksPresenter;
import com.doing.architecture.mvp.ui.fragment.TasksFragment;
import com.doing.architecture.mvp.util.ActivityUtils;

public class TasksActivity extends AppCompatActivity {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;
    private TasksPresenter mTasksPresenter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TasksActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_tasks_drawer);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        ((NavigationView) findViewById(R.id.activity_nav_tasks))
                .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.NavigationMenu_tasks_list:
                                Toast.makeText(TasksActivity.this, "List", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.NavigationMenu_tasks_statistics:
                                Toast.makeText(TasksActivity.this, "Statistics", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return false;
                    }
                });

        TasksFragment tasksFragment =
                (TasksFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        mTasksPresenter = new TasksPresenter(tasksFragment, TasksRepository.getInstance(
                FakeTasksRemoteDataSource.getInstance(), TasksLocalDataSource.getInstance(this)));

        if (savedInstanceState != null) {
            TasksFilterType currentFiltering =
                    (TasksFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mTasksPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mTasksPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
