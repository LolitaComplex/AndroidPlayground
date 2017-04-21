package com.doing.architecture.mvp.presenter.tasks;

import android.support.annotation.NonNull;

import com.doing.architecture.mvp.base.BasePresenter;
import com.doing.architecture.mvp.base.BaseView;
import com.doing.architecture.mvp.model.entity.Task;

import java.util.List;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-14.
 */

public interface TasksContract {

    interface View extends BaseView<Precenter>{

        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> task);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopupMenu();
    }

    interface Precenter extends BasePresenter{

        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(@NonNull Task requestTask);

        void completeTask(@NonNull Task completeTask);

        void activeteTask(@NonNull Task activeTask);

        void clearCompletedTasks();

        void setFiltering(TasksFilterType requestType);

        TasksFilterType getFiltering();
    }
}
