package com.shenshanlaoyuan.viewpagertest;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity implements OnPageChangeListener {

	private ViewPager mPager;
	private LinearLayout mPointContainer;
	private List<ImageView> mListDatas;
	private TextView mTitle;

	//һ��������ȡ���ݣ�����ģ�Ȿ�ػ�ȡ����,Ҫ��drawableĿ¼�������ͼƬ
	String[] titles = { "��һ��ҳ��", "�ڶ���ҳ��", "������ҳ��", "���ĸ�ҳ��", "�����ҳ��" };
	int[] imgs = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
			R.drawable.e };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPointContainer = (LinearLayout) findViewById(R.id.point_container);
		mTitle = (TextView) findViewById(R.id.tv_title);
		
		// ��ʼ������
		mListDatas = new ArrayList<ImageView>();
		for (int i = 0; i < imgs.length; i++) {
			// ���������ImageView
			ImageView iv = new ImageView(this);
			iv.setImageResource(imgs[i]);
			//ͼƬ����
			iv.setScaleType(ScaleType.FIT_XY);
			mListDatas.add(iv);

			// ���Բ��
			View point = new View(this);
			point.setBackgroundResource(R.drawable.point_normal);
			LayoutParams params = new LayoutParams(10, 10);
			if (i != 0) {
				params.leftMargin = 10;
			} else {
				point.setBackgroundResource(R.drawable.point_selected);
				mTitle.setText(titles[i]);
			}
			//������LinearLayout�����Բ��
			mPointContainer.addView(point, params);

		}

		// ����������
		mPager.setAdapter(new MyAdapter());
		// ���ü�����
		mPager.addOnPageChangeListener(this);

		// ����Ĭ��ѡ���м��item��ʵ��ѭ���ֲ���Ч��
		int middle = Integer.MAX_VALUE / 2;
		int extra = middle % mListDatas.size();
		int item = middle - extra;
		mPager.setCurrentItem(item);
		
		}

	class MyAdapter extends PagerAdapter {

		// ҳ�������
		@Override
		public int getCount() {

			if (mListDatas != null) {
				return Integer.MAX_VALUE;
			}
			return 0;
		}

		// ��Ƿ����������жϻ�����
		// view:��ʾ��view
		// object: ���
		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}

		// ��ʼ��item
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % mListDatas.size();
			// position�� Ҫ���ص�λ��
			ImageView iv = mListDatas.get(position);

			// �������Ҫ��ʾ��View��
			mPager.addView(iv);

			// ���ؼ�¼������
			return iv;
		}

		// ����item��Ŀ
		// object:������
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			position = position % mListDatas.size();
			ImageView iv = mListDatas.get(position);
			mPager.removeView(iv);

		}

	}

	/************************************* ViewPager�����ص����� *******************************************/
	// �ص�����,��viewpager�Ļ���״̬�ı�ʱ�Ļص�
	// * @see ViewPager#SCROLL_STATE_IDLE : ����״̬
	// * @see ViewPager#SCROLL_STATE_DRAGGING :�϶�״̬
	// * @see ViewPager#SCROLL_STATE_SETTLING: �̶�״̬
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	// �ص�����,��viewpager����ʱ�Ļص�
	// position: ��ǰѡ�е�λ��
	// positionOffset: �����İٷֱ�
	// positionOffsetPixels: ƫ�Ƶľ���,����������
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	// �ص�����,��viewpager��ĳ��ҳ��ѡ��ʱ�Ļص�
	@Override
	public void onPageSelected(int position) {

		position = position % mListDatas.size();

		// ����ѡ�еĵ����ʽ
		int count = mPointContainer.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = mPointContainer.getChildAt(i);

			view.setBackgroundResource(position == i ? R.drawable.point_selected
					: R.drawable.point_normal);
		}

		// ���ñ���
		mTitle.setText(titles[position]);

	}

}
