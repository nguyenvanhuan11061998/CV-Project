package com.example.newminiproject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newminiproject.Model.News;
import com.example.newminiproject.R;

import java.util.List;


//Adapter : đối tượng dùng để đẩy dữ liệu lên màn hình
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private LayoutInflater inflater;                                                                                            //đối tượng truyền vào vị trí, ngữ cảnh sử dụng adapter
    private List<News> data;                                                                                                    //List chứa dữ liệu cần để phân tích đẩy lên màn hình
    private NewsItemListener itemListener;                                                                                      //đối tượng thao tác với sự kiện click vào từng item

    public void setItemListener(NewsItemListener itemListener) {                                                                //dùng để gọi sự kiện click vào từng item
        this.itemListener = itemListener;
    }

    public NewsAdapter(Context context) {                                                                                       //truyền ngữ cảnh cần để adapter hoạt động tại đó
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<News> data) {                                                                                      //truyền dữ liệu từ bên ngoài vào list data để xử lý
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {                                                 //xử lý, tạo các đối tượng View để phân tích
        View v = inflater.inflate(R.layout.item_news,viewGroup,false);                                              //tạo từng đối tượng View tương ứng với một layout item_news
        return new NewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, final int i) {                                                 //xử lý dữ liệu trong đối tượng
        News item = data.get(i);                                                                                                //tạo từng đối tượng tương ứng với mỗi phàn tử trong List
        newsHolder.binData(item);                                                                                               //gợi hàm xử lý thông tin của từng đối tượng tin tức

        newsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                                          //set xử lý sự kiện cho mỗi đối tượng
                if (itemListener != null)
                    itemListener.onclickItem(i);                                                                                //gọi hàm onClick tại vị trí của đối tượng i
            }
        });

        newsHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemListener != null){
                    itemListener.onLongClickItem(v,i);                                                                          //gọi hàm onLongClick của đối tượng i và truyền vào đối tượng
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {                                                                                                 //đưa ra số lượng phần tử trong List phần tử
        return data == null ? 0 : data.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{                                                                    //các đối tượng riêng và xử lý thông tin
        private ImageView imgNews;                                                                                              //các thuộc tính hiển thị lên layout
        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvpubDate;


        public NewsHolder(@NonNull View itemView) {                                                                             //set các thuộc tính sang bên layout
            super(itemView);
            imgNews = itemView.findViewById(R.id.im_News);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvDesc = itemView.findViewById(R.id.tv_Desc);
            tvpubDate = itemView.findViewById(R.id.tv_pubDate);
        }

        public void binData(News item) {                                                                                        //truyền dữ liệu vào thuộc tính bên layout
            tvTitle.setText(item.getTitle());
            tvDesc.setText(item.getDesc());
            tvpubDate.setText(item.getPubDate());

            Glide.with(imgNews).load(item.getImage())                                                                           //phương thức chuyển ảnh từ kiểu String sang dạng ảnh
                    .placeholder(R.mipmap.loading)
                    .error(R.mipmap.image_error)
                    .into(imgNews);
        }
    }

    public interface NewsItemListener{                                                                                          //Giao diện của các phương thức xử lý sự kiện của các item
        void onclickItem(int i);
        void onLongClickItem(View v,int i);
    }
}
