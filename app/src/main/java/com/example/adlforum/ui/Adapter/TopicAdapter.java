package com.example.adlforum.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adlforum.ui.model.Topic;
import com.example.adlforum.R;

import java.util.List;

public class TopicAdapter extends BaseAdapter {
    private Context context;
    private List<Topic> topics;

    public TopicAdapter(Context context, List<Topic> topics) {
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int position) {
        return topics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_topic, parent, false);
        }

        TextView title = convertView.findViewById(R.id.topic_title);
        TextView content = convertView.findViewById(R.id.topic_content);

        Topic topic = topics.get(position);
        title.setText(topic.getTitle());
        content.setText(topic.getContent());

        return convertView;
    }
}
