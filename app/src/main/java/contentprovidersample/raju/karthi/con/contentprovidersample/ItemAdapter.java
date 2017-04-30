package contentprovidersample.raju.karthi.con.contentprovidersample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karthikeyan on 30/4/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final ArrayList<Item> mItemLst;

    public ItemAdapter(ArrayList<Item> itemLst) {
        mItemLst = itemLst;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        ItemViewHolder mHolder = new ItemViewHolder(itemView);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = mItemLst.get(position);
        holder.tvId.setText(item.getId() + "");
        holder.tvName.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return mItemLst.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }


}
