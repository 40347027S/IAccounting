package iaccounting.csie.com.iaccounting.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import iaccounting.csie.com.iaccounting.Home.Diary;
import iaccounting.csie.com.iaccounting.R;

import java.util.List;

/**
 * Created by Zixuan Zhao on 2/19/17.
 */

public class CardRecyViewAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private final static int WITHOUT_IMG = 0;
    private final static int WITH_IMG = 1;

    private List<Diary> diaries;
    private Context context;

    private OnItemClickListener itemClickListener;

    public CardRecyViewAdapter(List<Diary> diaries, Context context) {
        this.diaries = diaries;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case WITH_IMG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_adp_cv_noimg, parent, false);
                break;
            default:
            case WITHOUT_IMG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_adp_cv_noimg, parent, false);
                break;
        }

        view.setOnClickListener(this);

        return new WithoutImageHolder(view);
    }

    @Override
    public int getItemCount() {
        return diaries.isEmpty() ? 0 : diaries.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(diaries.get(position).getImgID()==-1){
            return WITHOUT_IMG;
        }
        return WITH_IMG;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof WithoutImageHolder){
            ((WithoutImageHolder)holder).date.setText(diaries.get(position).getCreateDateTime());
            ((WithoutImageHolder)holder).weather.setText(diaries.get(position).getWeather());
            ((WithoutImageHolder)holder).emotion.setText(diaries.get(position).getEmotion());
            ((WithoutImageHolder)holder).ctx.setText(diaries.get(position).getDiaryText());
        }
    }

    @Override
    public void onClick(View v) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(v);
        }
    }

    private static class WithoutImageHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView date;
        TextView weather;
        TextView emotion;
        TextView ctx;

        public WithoutImageHolder(View view){
            super(view);

            this.cardView = (CardView) view.findViewById(R.id.cv_recy_hm);
            this.date = (TextView) view.findViewById(R.id.tv_recy_hm_date);
            this.weather = (TextView) view.findViewById(R.id.tv_recy_hm_weather);
            this.emotion = (TextView) view.findViewById(R.id.tv_recv_hm_emt);
            this.ctx = (TextView) view.findViewById(R.id.tv_recy_hm_ctx);

        }


    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
    }


}
