package com.doing.newfeature.jellybean.parentactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.doing.newfeature.FeatureActivity;
import com.doing.newfeature.R;

import static android.os.Build.VERSION_CODES.N;

public class TargetActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TargetActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        findViewById(R.id.TargetActivity_btn_open_self).setOnClickListener(this);
        findViewById(R.id.TargetActivity_btn_open_feature).setOnClickListener(this);
        findViewById(R.id.TargetActivity_btn_open_son).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.TargetActivity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TargetActivity_btn_open_self:
                TargetActivity.start(this);
                break;

            case R.id.TargetActivity_btn_open_feature:
                // 如果想切换栈中Activity到栈顶，并且不清除之上任务，请设计多个Task
                Intent intent = new Intent(this, FeatureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                break;

            case R.id.TargetActivity_btn_open_son:
                SonActivity.start(this);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toBackParentActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        toBackParentActivity();
//        super.onBackPressed();
    }

    public void toBackParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
//        upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                    // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
    }


}
