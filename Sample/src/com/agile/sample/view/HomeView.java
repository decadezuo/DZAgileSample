package com.agile.sample.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;

import com.agile.sample.R;
import com.decade.agile.DZAgileView;
import com.decade.agile.adapter.DZPickDialogAdapter;
import com.decade.agile.components.DZDialog;
import com.decade.agile.components.DZDialog.Position;
import com.decade.agile.components.DZDialog.PromptBtnCallback;
import com.decade.agile.components.DZDialogBuilder;
import com.decade.agile.components.DZPickDialogView;
import com.decade.agile.components.DZPickDialogView.DZPickItem;
import com.decade.agile.kit.DZDialogHelper;
import com.decade.agile.kit.DZDialogHelper.DialogTheme;
import com.decade.framework.DZApplication;
import com.decade.framework.DZBaseViewActivity;

public class HomeView extends DZAgileView implements PromptBtnCallback {
	private Context context;
	private int index = 0;

	public HomeView(ViewGroup root, DZBaseViewActivity parent) {
		super(root, parent);
		context = getContext();
	}

	@Override
	protected void close() {
		// TODO Auto-generated method stub

	}

	@Override
	protected View create() {
		View view = inflateView(R.layout.home_view, null);
		initViews(view);
		return view;
	}

	public void initViews(View view) {
		view.findViewById(R.id.home_dialog_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showUpdateDialog();
					}
				});
		view.findViewById(R.id.home_pick_dialog_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						createMerchantDialog();
					}
				});
		view.findViewById(R.id.home_strip_dialog_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DZDialogHelper.openPrompt(getParent(),
								DialogTheme.STRIP);
						closePrompt();
					}
				});
		view.findViewById(R.id.home_rect_dialog_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DZDialogHelper
								.openPrompt(getParent(), DialogTheme.RECT);
						closePrompt();
					}
				});
	}

	private void closePrompt() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				getParent().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						DZDialogHelper.closePrompt();
					}
				});
			}
		}).start();
	}

	public void showUpdateDialog() {
		String content = "版本号：1.0.2" + "\t\t\t\t大小：" + "5.0M" + "\n" + "这是测试信息";
		DZDialogBuilder builder = new DZDialogBuilder(getParent(), "应用更新", content, "以后再说",
				"立即更新");
		
		setDialog(
				builder).show();
	}

	public DZDialogBuilder setDialog(DZDialogBuilder builder) {
		int height = DZApplication.getApp().getWorkSpaceHeight() / 3 * 1;
		return builder.setRightBgColor(getColorById(R.color.dialog_green_btn))
				.setLeftBgColor(getColorById(R.color.dialog_gray_btn))
				.setPromptBtnCallback(this).setHeight(height)
				.setLineColor(getColorById(R.color.dialog_line_color))
				.setTitleColor(getColorById(R.color.custom_blue));
	}

	@Override
	protected void start(Object arg0) {
		// TODO Auto-generated method stub
		addTopTitle("主页", Color.WHITE, getDimensionById(R.dimen.title_size));
	}

	@Override
	protected boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(DZDialog dialog, View view, Position position,
			int eventCode) {
		// TODO Auto-generated method stub

	}

	private void createMerchantDialog() {
		int height = DZApplication.getApp().getWorkSpaceHeight() / 3 * 1;
		DZDialogBuilder builder = new DZDialogBuilder(getParent(), "选择商户类型"){
			@Override
			public DZDialog create() {
				DZPickDialogView dialog = new DZPickDialogView(getParent(), getParams()) {
					@Override
					public void onResume() {
						super.onResume();
						getListView().setDivider(getDrawableById(R.drawable.line));
						getListView().setDividerHeight(2);
						getTitleTextView().setPadding(10, 10, 0, 10);
						DZPickDialogAdapter adapter = new DZPickDialogAdapter(
								getContext(), getItems());
						setAdapter(adapter);
						setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								CheckBox checkBox = (CheckBox) view
										.findViewById(R.id.pick_dialog_item_flag_ckb);
								checkBox.setChecked(true);
								dismiss();
								index = position;
							}
						});
					}
				};
				return dialog;
			}
		};
		builder.setTitleColor(getColorById(R.color.custom_blue)).setLineColor(
				getColorById(R.color.dialog_line_color)).setHeight(height).show();

	}

	private List<DZPickItem> getItems() {
		List<DZPickItem> items = new ArrayList<DZPickItem>();
		DZPickItem yl = new DZPickItem("银商登录");
		DZPickItem qb = new DZPickItem("钱宝登录");
		items.add(yl);
		items.add(qb);
		setSelected(items);
		return items;
	}

	private void setSelected(List<DZPickItem> items) {
		for (int i = 0; i < items.size(); i++) {
			if (index == i) {
				items.get(i).setSelected(true);
			}
		}
	}

}
