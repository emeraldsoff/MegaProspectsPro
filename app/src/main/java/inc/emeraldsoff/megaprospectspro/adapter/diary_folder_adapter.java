package inc.emeraldsoff.megaprospectspro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.model.diarycard_folder_gen;

//import com.google.android.material.card.MaterialCardView;

public class diary_folder_adapter extends FirestoreRecyclerAdapter<diarycard_folder_gen, diary_folder_adapter.diary_holder> {
    //    private SimpleDateFormat fullFormat_time = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE', 'hh:MM:SS a", Locale.US);
    private onItemClickListener listener;
    private onLongItemClickListener listener2;

    public diary_folder_adapter(@NonNull FirestoreRecyclerOptions<diarycard_folder_gen> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull diary_holder holder, int position, @NonNull final diarycard_folder_gen model) {
        int x = position + 1;
        holder.diary_no.setText(String.valueOf(x));
        holder.diary_date.setText(model.getFolder_doc());
//        holder.diary_note.setText(model.getDiary_page());
    }

    @NonNull
    @Override
    public diary_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_folder_diary, parent, false);
        return new diary_holder(view);
    }

    public void setOnLongItemClickListener(onLongItemClickListener listener2) {
        this.listener2 = listener2;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    //    public void deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
//    }
    public interface onLongItemClickListener {
        void onLongItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    //    public void deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
//    }
    class diary_holder extends RecyclerView.ViewHolder {
        TextView diary_no;
        TextView diary_date;
        //        TextView cli_dob;
        TextView diary_note;

        diary_holder(View itemView) {
            super(itemView);
            diary_no = itemView.findViewById(R.id.diary_no);
            diary_date = itemView.findViewById(R.id.diary_date);
//            cli_dob = itemView.findViewById(R.id.cli_dob);
//            cli_birthday = itemView.findViewById(R.id.cli_birthday);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
