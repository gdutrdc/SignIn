package com.rdc.signin.ui.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.signin.R;
import com.rdc.signin.utils.UIUtils;

/**
 * Created by seasonyuu on 15/9/20.
 */
public abstract class AbsMainActivity extends ToolbarActivity {
	protected RecyclerView recyclerView;
	protected SwipeRefreshLayout swipeRefreshLayout;
	protected DrawerLayout drawerLayout;

	/**
	 * 当列表被刷新时回调该方法
	 */
	protected abstract void onRefresh();

	protected abstract void initRecyclerView(RecyclerView recyclerView);

	protected abstract View createNavigationView();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		recyclerView = UIUtils.findView(this, R.id.main_recycler_view);
		swipeRefreshLayout = UIUtils.findView(this, R.id.main_refresh);
		drawerLayout = UIUtils.findView(this, R.id.main_container);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		initRecyclerView(recyclerView);

		swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				AbsMainActivity.this.onRefresh();
			}
		});

		View navigationView = createNavigationView();
		if (navigationView != null) {
			DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			params.gravity = Gravity.START;
			drawerLayout.addView(navigationView, params);

			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
					this, drawerLayout, (Toolbar) findViewById(R.id.toolbar), R.string.open_drawer, R.string.close_drawer);
			toggle.syncState();
			drawerLayout.setDrawerListener(toggle);
			drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
			drawerLayout.getChildAt(drawerLayout.getChildCount() - 1).setClickable(false);
		}
	}
}
