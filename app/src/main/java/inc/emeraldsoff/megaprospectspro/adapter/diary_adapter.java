package inc.emeraldsoff.megaprospectspro.adapter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.model.diarycard_page_gen;

//import com.google.android.material.card.MaterialCardView;

public class diary_adapter extends FirestoreRecyclerAdapter<diarycard_page_gen, diary_adapter.diary_holder> {
    //    private SimpleDateFormat fullFormat_time = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE', 'hh:MM:SS a", Locale.US);
    private onItemClickListener listener;
    private onLongItemClickListener listener2;
//    private List list;
//    private Context mcontext;

    public diary_adapter(@NonNull FirestoreRecyclerOptions<diarycard_page_gen> options) {
        super(options);
//        this.list = list;
//        this.mcontext = mcontext;
    }

    @Override
    protected void onBindViewHolder(@NonNull final diary_holder holder, int position, @NonNull final diarycard_page_gen model) {
//        List mylist = (List) list.get(position);
        int x = position + 1;
        holder.diary_no.setText(String.valueOf(x));
        holder.diary_date.setText(model.getTime());
        holder.diary_note.setText(model.getdata());

//        holder.diary_no.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                return false;
//            }
//        });
//        holder.cli_birthday.setText(model.getBirthday());
    }


//    @Override
//    public int getItemCount() {
//        return list.size();
//    }

    @NonNull
    @Override
    public diary_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_diary, parent, false);
        return new diary_holder(view);
    }

    public void setOnLongItemClickListener(onLongItemClickListener listener2) {
        this.listener2 = listener2;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onLongItemClickListener {
        void onLongItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class diary_holder extends RecyclerView.ViewHolder {
        TextView diary_no;
        TextView diary_date;
        //        TextView cli_dob;
        TextView diary_note;

        diary_holder(View itemView) {
            super(itemView);
            diary_no = itemView.findViewById(R.id.diary_no);
            diary_date = itemView.findViewById(R.id.diary_date);
            diary_note = itemView.findViewById(R.id.diary_note);
//            cli_dob = itemView.findViewById(R.id.cli_dob);
//            cli_birthday = itemView.findViewById(R.id.cli_birthday);


//            diary_note.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && listener != null) {
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
//                    }
//                }
//            });


            diary_no.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View vi) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener2 != null) {
                        listener2.onLongItemClick(getSnapshots().getSnapshot(position), position);



                    }
                    return true;
                }
            });

        }
    }
}
