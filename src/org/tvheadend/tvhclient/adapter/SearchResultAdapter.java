package org.tvheadend.tvhclient.adapter;

import java.util.Comparator;
import java.util.List;

import org.tvheadend.tvhclient.R;
import org.tvheadend.tvhclient.Utils;
import org.tvheadend.tvhclient.model.Program;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<Program> {

    private final static String TAG = SearchResultAdapter.class.getSimpleName();
    Activity context;
    List<Program> list;

    public SearchResultAdapter(Activity context, List<Program> list) {
        super(context, R.layout.search_result_widget, list);
        this.context = context;
        this.list = list;
    }

    public void sort() {
        sort(new Comparator<Program>() {
            public int compare(Program x, Program y) {
                return (x.start.compareTo(y.start));
            }
        });
    }

    static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView channel;
        public TextView time;
        public TextView date;
        public TextView duration;
        public TextView description;
        public TextView seriesInfo;
        public TextView contentType;
        public ImageView state;
        public TextView genre;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null) {
            view = context.getLayoutInflater().inflate(R.layout.search_result_widget, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.channel = (TextView) view.findViewById(R.id.channel);
            holder.state = (ImageView) view.findViewById(R.id.state);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.duration = (TextView) view.findViewById(R.id.duration);
            holder.seriesInfo = (TextView) view.findViewById(R.id.series_info);
            holder.contentType = (TextView) view.findViewById(R.id.content_type);
            holder.description = (TextView) view.findViewById(R.id.description);
            holder.genre = (TextView) view.findViewById(R.id.genre);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the program and assign all the values
        Program p = getItem(position);
        if (p != null) {
            holder.title.setText(p.title);
            if (holder.channel != null && p.channel != null) {
                holder.channel.setText(p.channel.name);
            }
            Utils.setChannelIcon(holder.icon, null, p.channel);
            Utils.setState(context, holder.state, p);
            Utils.setDate(holder.date, p.start);
            Utils.setTime(holder.time, p.start, p.stop);
            Utils.setDuration(holder.duration, p.start, p.stop);
            Utils.setDescription(null, holder.description, p.description);
            Utils.setContentType(null, holder.contentType, p.contentType);
            Utils.setSeriesInfo(null, holder.seriesInfo, p.seriesInfo);
            Utils.setGenreColor(context, holder.genre, p, TAG);
        }
        return view;
    }

    public void update(Program p) {
        int length = list.size();

        // Go through the list of programs and find the
        // one with the same id. If its been found, replace it.
        for (int i = 0; i < length; ++i) {
            if (list.get(i).id == p.id) {
                list.set(i, p);
                break;
            }
        }
    }

    public List<Program> getList() {
        return list;
    }
}
