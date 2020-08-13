package Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.UserAdapter;

public class Codereuse {
//    databaseReference = FirebaseDatabase.getInstance().getReference("chats");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            list.clear();
//            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                Chat chat = snapshot.getValue(Chat.class);
//                assert chat != null;
//                if (chat.getSender().equals(firebaseUser.getUid())) {
//                    list.add(chat.getReceiver());
//
//                }
//                if (chat.getReceiver().equals(firebaseUser.getUid())) {
//                    list.add(chat.getSender());
//
//                }
//
//            }
//            readChats();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    });
//    private void readChats() {
//        muser = new ArrayList<>();
//        databaseReference = FirebaseDatabase.getInstance().getReference("users");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                muser.clear();
//                try {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        User user = snapshot.getValue(User.class);
//                        for (String id : list) {
//
//                            if (user.getId().equals(id)) {
//                                if (muser.size() != 0) {
//                                    for (User user1 : muser)
//                                    {
//                                        if(!user.getId().equals(user1.getId()))
//                                        {
//                                            muser.add(user);
//                                        }
//                                    }
//
//                                }else
//                                {
//                                    muser.add(user);
//                                }
//                            }
//
//                        }
//
//                    }
//                }catch (Exception e){
//
//                }
//
//                userAdapter=new UserAdapter(getContext(),muser,true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
}
