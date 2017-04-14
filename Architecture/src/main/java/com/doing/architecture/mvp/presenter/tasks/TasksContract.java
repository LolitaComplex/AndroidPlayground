package com.doing.architecture.mvp.presenter.tasks;

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

        void showAddTaks();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedCompelete();

        void showTaskMarkedActive();

        void showCompeltedTasksCleared();

        void showLoadingTasksError();

        void showNoTask();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompeletedTasks();

        void showSuccessfullySaveMessage();

        boolean isActive();

        void showFilteringPopupMenu();
    }

    interface Precenter extends BasePresenter{
    }


}
