package com.doing.architecture.mvp.presenter.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.doing.architecture.mvp.model.entity.Task;
import com.doing.architecture.mvp.model.remote.TasksDataSource;
import com.doing.architecture.mvp.model.remote.TasksRepository;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-18.
 */

public class TasksPresenter implements TasksContract.Precenter {

    private boolean mFirstLoad = true;

    private final TasksContract.View mTasksView;
    private final TasksRepository mTasksRepository;

    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;


    public TasksPresenter(@NonNull TasksContract.View tasksView,
                          @NonNull TasksRepository tasksRepository) {
        mTasksView = Preconditions.checkNotNull(tasksView);
        mTasksRepository = Preconditions.checkNotNull(tasksRepository);

        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
        //我们启动TasksPresenter就为了要显示Tasks列表
        loadTasks(false);
    }


    @Override
    public void loadTasks(boolean forceUpdate) {
        //模拟第一次从网络获取，其后缓存的操作
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUi) {
        if (showLoadingUi) {
            mTasksView.setLoadingIndicator(true);
        }

        //如果进行了刷新，标记为脏数据，从网络获取
        if (forceUpdate) {
            mTasksRepository.refreshTasks();
        }

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                List<Task> tasksToShow = new ArrayList<>();
                //过滤操作
                for (Task task : tasks) {
                    switch (mCurrentFiltering) {
                        case ALL_TASKS:
                            tasksToShow.add(task);
                            break;

                        case ACTIVE_TASKS:
                            if (task.isActive()) {
                                tasksToShow.add(task);
                            }
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted()) {
                                tasksToShow.add(task);
                            }
                            break;
                        default:
                            tasksToShow.add(task);
                            break;
                    }
                }

                if (!mTasksView.isActive()) {
                    return;
                }

                if (showLoadingUi) {
                    mTasksView.setLoadingIndicator(false);
                }

                processTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {
                if (!mTasksView.isActive()) {
                    return;
                }

                mTasksView.showLoadingTasksError();
            }
        });
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks();
        } else {
            // Show the list of tasks
            mTasksView.showTasks(tasks);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {
//        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
//            mTasksView.showSuccessfullySavedMessage();
//        }
    }

    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }

    @Override
    public void openTaskDetails(@NonNull Task requestTask) {
        Preconditions.checkNotNull(requestTask);
        mTasksView.showTaskDetailsUi(requestTask.getId());
    }

    @Override
    public void completeTask(@NonNull Task completeTask) {
        Preconditions.checkNotNull(completeTask);
        mTasksRepository.completeTask(completeTask);
        mTasksView.showTaskMarkedComplete();
        loadTasks(false, false);
    }

    @Override
    public void activeteTask(@NonNull Task activeTask) {
        Preconditions.checkNotNull(activeTask);
        mTasksRepository.activateTask(activeTask);
        mTasksView.showTaskMarkedActive();
        loadTasks(false, false);
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRepository.clearCompletedTasks();
        mTasksView.showCompletedTasksCleared();
        loadTasks(false, false);
    }

    @Override
    public void setFiltering(TasksFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return mCurrentFiltering;
    }

}
