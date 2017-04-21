package com.doing.architecture.mvp.ui.fragment;

import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.doing.architecture.R;
import com.doing.architecture.mvp.model.entity.Task;
import com.doing.architecture.mvp.presenter.tasks.TasksContract;
import com.doing.architecture.mvp.presenter.tasks.TasksFilterType;
import com.doing.architecture.mvp.ui.widget.ScrollChildSwipeRefreshLayout;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-13.
 */

public class TasksFragment extends Fragment implements TasksContract.View{

    private static final String TAG = "TasksFragment";

    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mTaskLL;
    private ListView mTaskList;
    private LinearLayout mNoTaskLL;
    private TasksContract.Precenter mPresenter;
    private TasksAdapter mListAdapter;
    private TextView mNoTasksMain;
    private TextView mNoTasksAdd;
    private ImageView mNoTaskIcon;
    private TextView mFilteringLabel;


    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mListAdapter = new TasksAdapter(Collections.<Task>emptyList(), mItemListener);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(View root, Bundle savedInstanceState) {
        mSwipeRefreshLayout = ((ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout));
        mTaskLL = ((LinearLayout) root.findViewById(R.id.tasksLL));
        mTaskList = ((ListView) root.findViewById(R.id.tasks_list));
        mNoTaskLL = ((LinearLayout) root.findViewById(R.id.noTasks));
        mNoTasksMain = ((TextView) root.findViewById(R.id.noTasksMain));
        mNoTasksAdd = ((TextView) root.findViewById(R.id.noTasksAdd));
        mNoTaskIcon = ((ImageView) root.findViewById(R.id.noTasksIcon));
        mFilteringLabel = ((TextView) root.findViewById(R.id.filteringLabel));



        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefreshLayout.setChildScrollView(mTaskList);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadTasks(false);
            }
        });

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewTask();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                mPresenter.clearCompletedTasks();
                break;

            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;

            case R.id.menu_refresh:
                //从网络直接加载
                mPresenter.loadTasks(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.active:
                        mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                        break;
                    case R.id.completed:
                        mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                        break;
                    default:
                        mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                        break;
                }
                mPresenter.loadTasks(false);
                return true;
            }
        });

        popup.show();
    }

    TaskItemListener mItemListener = new TaskItemListener() {
        @Override
        public void onTaskClick(Task clickedTask) {
            mPresenter.openTaskDetails(clickedTask);
        }

        @Override
        public void onCompleteTaskClick(Task completedTask) {
            mPresenter.completeTask(completedTask);
        }

        @Override
        public void onActivateTaskClick(Task activatedTask) {
            mPresenter.activeteTask(activatedTask);
        }
    };

    @Override
    public void setPresenter(TasksContract.Precenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
        Log.d(TAG, "mPresenter：" + mPresenter);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        //由于在onResume()中就开始LoadTask，并调用了该方法，有可能为Null
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        //同样由于onResume()中就开始Load，所以Hander添加队尾确保刷新操作时UI已经测绘完毕
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showTasks(List<Task> task) {
        mListAdapter.replaceData(task);

        mTaskLL.setVisibility(View.VISIBLE);
        mNoTaskLL.setVisibility(View.GONE);
    }

    @Override
    public void showAddTask() {
//        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
//        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showTaskDetailsUi(String taskId) {
//        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
//        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId);
//        startActivity(intent);
    }

    @Override
    public void showTaskMarkedComplete() {
        showMessage("Task marked complete");
    }

    @Override
    public void showTaskMarkedActive() {
        showMessage("Task marked active");
    }

    @Override
    public void showCompletedTasksCleared() {
        showMessage("Completed tasks cleared");
    }

    @Override
    public void showLoadingTasksError() {
        showMessage("Error while loading tasks");
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNoTasks() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    @Override
    public void showNoActiveTasks() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_active),
                R.drawable.ic_check_circle_24dp,
                false
        );
    }

    @Override
    public void showNoCompletedTasks() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_completed),
                R.drawable.ic_verified_user_24dp,
                false
        );

    }

    private void showNoTasksViews(String mainText, int iconRes, boolean showAddView) {
        mTaskLL.setVisibility(View.GONE);
        mNoTaskLL.setVisibility(View.VISIBLE);

        mNoTasksMain.setText(mainText);
        mNoTaskIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoTasksAdd.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showActiveFilterLabel() {
        mFilteringLabel.setText(getResources().getString(R.string.label_active));
    }

    @Override
    public void showCompletedFilterLabel() {
        mFilteringLabel.setText(getResources().getString(R.string.label_completed));
    }

    @Override
    public void showAllFilterLabel() {
        mFilteringLabel.setText(getResources().getString(R.string.label_all));
    }


    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showFilteringPopupMenu() {

    }

    private static class TasksAdapter extends BaseAdapter{

        private List<Task> mTasks;
        private TaskItemListener mItemListener;

        public TasksAdapter(List<Task> tasks, TaskItemListener listener) {
            mTasks = Preconditions.checkNotNull(tasks);
            mItemListener = listener;//可能是传Null也可以的意思，反正只是个能看不能点的列表了
        }

        public void replaceData(List<Task> tasks) {
            mTasks = Preconditions.checkNotNull(tasks);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTasks.size();
        }

        @Override
        public Task getItem(int position) {
            return mTasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task_item, parent, false);
                holder = new ViewHolder();
                holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.complete);
                holder.mTitle = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Task task = getItem(position);

            holder.mTitle.setText(task.getTitleForList());
            holder.mCheckBox.setChecked(task.isCompleted());

            holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (task.isCompleted()) {
                        mItemListener.onActivateTaskClick(task);
                    } else {
                        mItemListener.onCompleteTaskClick(task);
                    }
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onTaskClick(task);
                }
            });


            return convertView;
        }

        private static class ViewHolder{
            public CheckBox mCheckBox;
            public TextView mTitle;
        }
    }

    public interface TaskItemListener {

        void onTaskClick(Task clickedTask);

        void onCompleteTaskClick(Task completedTask);

        void onActivateTaskClick(Task activatedTask);
    }


}
