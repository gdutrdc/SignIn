package com.rdc.signin.ui.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rdc.signin.R;
import com.rdc.signin.constant.Message;
import com.rdc.signin.net.control.ConnectListener;
import com.rdc.signin.net.student.GetMsgList;
import com.rdc.signin.ui.adapter.MsgListAdapter;
import com.rdc.signin.utils.DialogUtils;
import com.rdc.signin.utils.JSONUtils;
import com.rdc.signin.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/11/3.
 */
public class MessageListActivity extends ToolbarActivity {
	private RecyclerView recyclerView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private MsgListAdapter msgListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_list);

		recyclerView = UIUtils.findView(this, R.id.msg_list);
		swipeRefreshLayout = UIUtils.findView(this, R.id.msg_list_refresh);

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getMsgList();
			}
		});
		swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		msgListAdapter = new MsgListAdapter(this);
		recyclerView.setAdapter(msgListAdapter);

		DialogUtils.showProgressDialog(this, R.string.loading);
		getMsgList();

	}

	private void getMsgList() {
		new GetMsgList(new ConnectListener() {
			@Override
			public void onConnect(boolean isConnect, String reason, String response) {
				swipeRefreshLayout.setRefreshing(false);
				DialogUtils.dismissAllDialog();
				if (isConnect) {
					ArrayList<Message> list = JSONUtils.getMessageList(response);
					msgListAdapter.setMsgList(list);
					msgListAdapter.notifyDataSetChanged();
				} else {
					Toast.makeText(MessageListActivity.this, reason, Toast.LENGTH_SHORT).show();
				}
			}
		}).connect();
	}
}
