package chata.can.chata_ai_api.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import chata.can.chata_ai_api.R;

public class TestPopupActivity extends Activity
{
	Point p;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btn_show = findViewById(R.id.show_popup);
		btn_show.setOnClickListener(view -> {
			//Open popup window
			if (p != null)
				showPopup(TestPopupActivity.this, p);
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		int[] location = new int[2];
		Button button = findViewById(R.id.show_popup);

		button.getLocationOnScreen(location);
		p = new Point();
		p.x = location[0];
		p.y = location[1];
	}

	private void showPopup(final Activity context, Point p)
	{
		int popupWidth = 200;
		int popupHeight = 150;

		LinearLayout viewGroup = context.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);

		int OFFSET_X = 30;
		int OFFSET_Y = 30;

		popup.setBackgroundDrawable(new BitmapDrawable());

		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

		Button close = (Button) layout.findViewById(R.id.close);
		close.setOnClickListener(view -> popup.dismiss());
	}
}
