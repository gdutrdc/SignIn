package com.rdc.signin.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.signin.R;
import com.rdc.signin.constant.Message;
import com.rdc.signin.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by seasonyuu on 15/11/3.
 */
public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MessageViewHolder> {
	private Context context;
	private ArrayList<Message> list;

	public MsgListAdapter(Context context) {
		this.context = context;
		list = new ArrayList<>();
	}

	@Override
	public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MessageViewHolder holder = new MessageViewHolder(
				LayoutInflater.from(context).inflate(R.layout.item_message, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(MessageViewHolder holder, int position) {
		holder.setContent(list.get(position));
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public void setMsgList(ArrayList<Message> msgList) {
		this.list = msgList;
	}

	public class MessageViewHolder extends RecyclerView.ViewHolder {
		private TextView sender;
		private TextView content;
		private TextView senderFirstName;
		private TextView time;
		private ImageView mark;

		public MessageViewHolder(View itemView) {
			super(itemView);
			sender = UIUtils.findView(itemView, R.id.tv_sender_message);
			content = UIUtils.findView(itemView, R.id.tv_short_message);
			senderFirstName = UIUtils.findView(itemView, R.id.tv_first_name_message);
			time = UIUtils.findView(itemView, R.id.tv_time_message);
			mark = UIUtils.findView(itemView, R.id.iv_handle_message);
		}

		public void setContent(Message message) {
			senderFirstName.setText(message.getSenderName()
					.substring(0, 1));
			sender.setText(message.getSenderName());
			content.setText(message.getType() == Message.TYPE_ASK_FOR_LEAVE ? "请假" : "系统信息"
					+ " - "
					+ shortCut(message.getDetail()));
			time.setText(message.getFormatTime());
			mark.setVisibility(message.getHandle() == 1 ? View.VISIBLE : View.GONE);
		}

		private String shortCut(String detail) {
			if (detail.length() > 20)
				return detail.substring(0, 20) + "...";
			return detail;
		}
	}
}
