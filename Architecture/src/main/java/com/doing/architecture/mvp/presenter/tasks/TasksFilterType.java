package com.doing.architecture.mvp.presenter.tasks;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-18.
 */

public enum TasksFilterType {

    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}
