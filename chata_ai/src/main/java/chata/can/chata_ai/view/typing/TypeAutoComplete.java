package chata.can.chata_ai.view.typing;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class TypeAutoComplete extends AppCompatAutoCompleteTextView
{
	private CharSequence mText;
	private int mIndex;
	private long mDelay = 150;

	public TypeAutoComplete(Context context)
	{
		super(context);
	}

	public TypeAutoComplete(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	private Handler mHandler = new Handler();

	private Runnable characterAdder = new Runnable()
	{
		@Override
		public void run()
		{
			setText(mText.subSequence(0, mIndex++));
			if (mIndex <= mText.length())
			{
				mHandler.postDelayed(characterAdder, mDelay);
			}
			else
			{

			}
		}
	};

	public void animateText(CharSequence txt)
	{
		mText = txt;
		mIndex = 0;
		setText("");
		mHandler.removeCallbacks(characterAdder);
		mHandler.postDelayed(characterAdder, mDelay);
	}

	public void setCharacterDelay(long m)
	{
		mDelay = m;
	}
}
