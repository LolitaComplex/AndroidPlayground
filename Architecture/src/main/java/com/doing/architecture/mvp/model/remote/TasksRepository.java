package com.doing.architecture.mvp.model.remote;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.doing.architecture.mvp.model.entity.Task;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-04-18.
 */

public class TasksRepository implements TasksDataSource {

    private static TasksRepository sTasksRepositorys;

    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksDataSource mTasksLocalDataSource;
    private boolean mCacheIsDirty;
    private LinkedHashMap<String, Task> mCachedTasks;

    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                           @NonNull TasksDataSource tasksLocalDataSource) {
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                              @NonNull TasksDataSource tasksLocalDataSource) {
        if (sTasksRepositorys == null) {
            synchronized (TasksRepository.class) {
                if (sTasksRepositorys == null) {
                    sTasksRepositorys = new TasksRepository(tasksRemoteDataSource,
                            tasksLocalDataSource);
                }
            }
        }
        return sTasksRepositorys;
    }

    public static void detoryInstance() {
        sTasksRepositorys = null;
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        checkNotNull(callback);

        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onTasksLoaded(Lists.newArrayList(mCachedTasks.values()));
            return;
        }

        // 缓存过期了则加载远程数据
        if (mCacheIsDirty) {
            getTasksFromRemoteSource(callback);
        } else {
            mTasksLocalDataSource.getTasks(new LoadTasksCallback() {
                @Override
                public void onTasksLoaded(List<Task> tasks) {
                    refreshCache(tasks);
                    // callback.onTasksLoaded(tasks);
                    // Goole这里传入的是新建List，可能是好的设计方式
                    callback.onTasksLoaded(Lists.newArrayList(mCachedTasks.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    //失败的话直接从网络获取
                    getTasksFromRemoteSource(callback);
                }
            });
        }
    }

    @Override
    public void getTask(@NonNull final String taskId, @NonNull final GetTaskCallback callback) {
        checkNotNull(taskId);
        checkNotNull(callback);

        Task cacheTask = getTaskWithId(taskId);

        if (cacheTask != null) {
            callback.onTaskLoaded(cacheTask);
            return;
        }

        mTasksLocalDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mCachedTasks == null) {
                    mCachedTasks = new LinkedHashMap<>();
                }
                mCachedTasks.put(taskId, task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                mTasksRemoteDataSource.getTask(taskId, new GetTaskCallback() {
                    @Override
                    public void onTaskLoaded(Task task) {
                        if (mCachedTasks == null) {
                            mCachedTasks = new LinkedHashMap<>();
                        }
                        mCachedTasks.put(taskId, task);
                        callback.onTaskLoaded(task);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        //全部失败在走我们的失败回调
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        Preconditions.checkNotNull(task);
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        //哈希表添加就是更新的操作
        mCachedTasks.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        completeTask(getTaskWithId(taskId));
    }

    @Override
    public void activateTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);

        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), false);

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        //哈希表添加就是更新的操作
        mCachedTasks.put(task.getId(), completedTask);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        Preconditions.checkNotNull(taskId);
        activateTask(getTaskWithId(taskId));
    }

    @Override
    public void clearCompletedTasks() {
        mTasksLocalDataSource.clearCompletedTasks();
        mTasksRemoteDataSource.clearCompletedTasks();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Task>> iterator = mCachedTasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next().getValue();
            if (task.isCompleted()) {
                iterator.remove();
            }
        }
    }

    @Override
    public void refreshTasks() {
        // 刷新任务就是定制一个超时机制，不走缓存，本地，全部走网络
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTasks() {
        mTasksLocalDataSource.deleteAllTasks();
        mTasksRemoteDataSource.deleteAllTasks();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();

    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        mTasksRemoteDataSource.deleteTask(taskId);
        mTasksLocalDataSource.deleteTask(taskId);

        mCachedTasks.remove(taskId);
    }

    private void getTasksFromRemoteSource(@NonNull final LoadTasksCallback callback) {
        mTasksRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);//在获取数据之余存储缓存
                refreshLocalDataSource(tasks);
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * 存储缓存
     * @param tasks
     */
    private void refreshCache(List<Task> tasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }

        mCachedTasks.clear();
        for (Task task: tasks) {
            mCachedTasks.put(task.getId(), task);
        }
        mCacheIsDirty = false;
    }

    /**
     * 更新数据库，添加事务效率会更高
     * @param tasks
     */
    private void refreshLocalDataSource(List<Task> tasks) {
        mTasksLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mTasksRemoteDataSource.saveTask(task);
        }

    }

    @Nullable
    private Task getTaskWithId(String taskId) {
        if (mCachedTasks != null && !mCachedTasks.isEmpty()) {
            return mCachedTasks.get(taskId);
        }
        return null;
    }
}
