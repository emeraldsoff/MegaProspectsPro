//package inc.emeraldsoff.megaprospectspro.ui_data;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Source;
//
//import java.util.Objects;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import inc.emeraldsoff.megaprospectspro.R;
//
//import static android.content.Context.MODE_PRIVATE;
//
//public class fragment_dummy extends Fragment {
//    private Context mcontext;
//    private Source cache = Source.CACHE;
//    private SharedPreferences mpref;
//    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_dummy,container,false);
//
//        mcontext = getActivity();
//        mpref = Objects.requireNonNull(mcontext)
//                .getSharedPreferences("User", MODE_PRIVATE);
//
//
//
//        return v;
//    }
//}
