package tv.live.bx.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efeizao.bx.R;
import tv.live.bx.common.Constants;
import tv.live.bx.common.Utils;
import tv.live.bx.imageloader.ImageLoaderUtil;
import tv.live.bx.model.RankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行版适配器
 *
 * @since JDK 1.6
 */
public class RankListAdapter extends BaseAdapter {

	protected Context moContext;
	public static int RANK_STAR = 1;
	public static int RANK_WEALTH = 3;
	protected int mRankType;
	private List<RankBean.UserBean> userArray = new ArrayList<>();

	/**
	 * Creates a new instance of RankListAdapter.
	 *
	 * @param poContext layoutId 列表布局文件Id，如：R.layout.item_fm_rank_field
	 */
	public RankListAdapter(Context poContext, int rankType) {
		moContext = poContext;
		this.mRankType = rankType;
	}

	/**
	 * 设置数据
	 *
	 * @param data
	 */
	public void setData(List<RankBean.UserBean> data) {
		userArray.clear();
		userArray.addAll(data);
		notifyDataSetChanged();
	}


	/**
	 * 清除数据
	 */
	public void clearData() {
		userArray.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return userArray.size();
	}

	@Override
	public Object getItem(int position) {
		return userArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({"InflateParams", "ResourceAsColor"})
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder loHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(moContext).inflate(R.layout.a_main_rank_item, null);
			loHolder = new Holder();
			loHolder.moRankNo = (ImageView) convertView.findViewById(R.id.item_rank_no);
			loHolder.moRankNoText = (TextView) convertView.findViewById(R.id.item_rank_no_text);
			loHolder.moIv = (ImageView) convertView.findViewById(R.id.item_fm_rank_field_photo);
			loHolder.moIvPhotoV = (ImageView) convertView.findViewById(R.id.item_fm_rank_field_photo_v);
			loHolder.moTvNickname = (TextView) convertView.findViewById(R.id.item_fm_rank_field_tv_nickname);
			loHolder.moTvMoney = (TextView) convertView.findViewById(R.id.item_fm_rank_field_tv_money);
			loHolder.mUserLevel = (ImageView) convertView.findViewById(R.id.item_user_level);
			convertView.setTag(loHolder);
		} else {
			loHolder = (Holder) convertView.getTag();
		}

		// 如果需要突然显示“前10名”
		if (position < 10) {
			loHolder.moRankNo.setVisibility(View.VISIBLE);
			loHolder.moRankNoText.setVisibility(View.GONE);
			loHolder.moRankNo.setImageResource(Utils.getFiledDrawable(Constants.USER_RANK_PIX, String.valueOf(position)));
		} else {
			loHolder.moRankNo.setVisibility(View.GONE);
			loHolder.moRankNoText.setVisibility(View.VISIBLE);
			loHolder.moRankNoText.setText(String.valueOf(position + 1));
		}

		RankBean.UserBean bean = (RankBean.UserBean) getItem(position);
		if (!TextUtils.isEmpty(bean.headPic))
			ImageLoaderUtil.with().loadImageTransformRoundCircle(moContext, loHolder.moIv, bean.headPic);
		if (!TextUtils.isEmpty(bean.nickname))
			loHolder.moTvNickname.setText(bean.nickname);
		loHolder.moIvPhotoV.setVisibility(bean.verified ? View.VISIBLE : View.GONE);

		if (mRankType == RANK_WEALTH) {
			//财富榜，优先显示用户等级
			ImageLoaderUtil.with().loadImage(moContext, loHolder.mUserLevel, Utils.getLevelImageResourceUri(Constants.USER_LEVEL_PIX, String.valueOf(bean.level)));
		} else {
			//人气榜、明星榜，优先显示主播等级
			ImageLoaderUtil.with().loadImage(moContext, loHolder.mUserLevel, Utils.getLevelImageResourceUri(Constants.USER_ANCHOR_LEVEL_PIX, String.valueOf(bean.moderatorLevel)));
		}
		return convertView;
	}

	protected class Holder {
		protected ImageView moIv, moRankNo, mUserLevel, moIvPhotoV;
		protected TextView moTvNickname, moTvMoney, moRankNoText;
	}
}