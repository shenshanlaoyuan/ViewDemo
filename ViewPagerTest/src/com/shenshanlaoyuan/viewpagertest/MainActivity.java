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

	//一般从网络获取数据，这里模拟本地获取数据,要在drawable目录添加五张图片
	String[] titles = { "第一个页面", "第二个页面", "第三个页面", "第四个页面", "第五个页面" };
	int[] imgs = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
			R.drawable.e };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPointContainer = (LinearLayout) findViewById(R.id.point_container);
		mTitle = (TextView) findViewById(R.id.tv_title);
		
		// 初始化数据
		mListDatas = new ArrayList<ImageView>();
		for (int i = 0; i < imgs.length; i++) {
			// 给集合添加ImageView
			ImageView iv = new ImageView(this);
			iv.setImageResource(imgs[i]);
			//图片拉伸
			iv.setScaleType(ScaleType.FIT_XY);
			mListDatas.add(iv);

			// 添加圆点
			View point = new View(this);
			point.setBackgroundResource(R.drawable.point_normal);
			LayoutParams params = new LayoutParams(10, 10);
			if (i != 0) {
				params.leftMargin = 10;
			} else {
				point.setBackgroundResource(R.drawable.point_selected);
				mTitle.setText(titles[i]);
			}
			//向容器LinearLayout中添加圆点
			mPointContainer.addView(point, params);

		}

		// 设置适配器
		mPager.setAdapter(new MyAdapter());
		// 设置监听器
		mPager.addOnPageChangeListener(this);

		// 设置默认选中中间的item，实现循环轮播的效果
		int middle = Integer.MAX_VALUE / 2;
		int extra = middle % mListDatas.size();
		int item = middle - extra;
		mPager.setCurrentItem(item);
		
		}

	class MyAdapter extends PagerAdapter {

		// 页面的数量
		@Override
		public int getCount() {

			if (mListDatas != null) {
				return Integer.MAX_VALUE;
			}
			return 0;
		}

		// 标记方法，用来判断缓存标记
		// view:显示的view
		// object: 标记
		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}

		// 初始化item
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % mListDatas.size();
			// position： 要加载的位置
			ImageView iv = mListDatas.get(position);

			// 用来添加要显示的View的
			mPager.addView(iv);

			// 返回记录缓存标记
			return iv;
		}

		// 销毁item条目
		// object:缓存标记
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			position = position % mListDatas.size();
			ImageView iv = mListDatas.get(position);
			mPager.removeView(iv);

		}

	}

	/************************************* ViewPager监听回调方法 *******************************************/
	// 回调方法,当viewpager的滑动状态改变时的回调
	// * @see ViewPager#SCROLL_STATE_IDLE : 闲置状态
	// * @see ViewPager#SCROLL_STATE_DRAGGING :拖动状态
	// * @see ViewPager#SCROLL_STATE_SETTLING: 固定状态
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	// 回调方法,当viewpager滚动时的回调
	// position: 当前选中的位置
	// positionOffset: 滑动的百分比
	// positionOffsetPixels: 偏移的距离,滑动的像素
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	// 回调方法,当viewpager的某个页面选中时的回调
	@Override
	public void onPageSelected(int position) {

		position = position % mListDatas.size();

		// 设置选中的点的样式
		int count = mPointContainer.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = mPointContainer.getChildAt(i);

			view.setBackgroundResource(position == i ? R.drawable.point_selected
					: R.drawable.point_normal);
		}

		// 设置标题
		mTitle.setText(titles[position]);

	}

}
