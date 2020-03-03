package com.jayaraj.firebasechatapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jayaraj.firebasechatapplication.R;
import com.jayaraj.firebasechatapplication.data.FriendDB;
import com.jayaraj.firebasechatapplication.data.GroupDB;
import com.jayaraj.firebasechatapplication.data.StaticConfig;
import com.jayaraj.firebasechatapplication.model.Friend;
import com.jayaraj.firebasechatapplication.model.Group;
import com.jayaraj.firebasechatapplication.model.ListFriend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jayaraj.firebasechatapplication.ui.GroupFragment.CONTEXT_MENU_KEY_INTENT_DATA_POS;
import static com.jayaraj.firebasechatapplication.ui.GroupFragment.REQUEST_EDIT_GROUP;
import static com.jayaraj.firebasechatapplication.ui.ListFriendsAdapter.mapMark;

public class MembersList extends AppCompatActivity {

    private RecyclerView recyclerListFriend;
    private ListPeopleAdapter adapter;
    private ListFriend listFriend;
    TextView text;
    private boolean isEditGroup;
    private Group groupEdit;
    TextView id;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);

        text = (TextView)findViewById(R.id.text);
        id = (TextView)findViewById(R.id.id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listFriend = FriendDB.getInstance(this).getListFriend();
        Intent intentData = getIntent();
        if (intentData.getStringExtra("groupId") != null) {
            isEditGroup = true;
            String idGroup = intentData.getStringExtra("groupId");
            groupEdit = GroupDB.getInstance(this).getGroup(idGroup);
            text.setText(groupEdit.groupInfo.get("name"));
        } else {

        }
        ID = getIntent().getStringExtra("member");
        id.setText(ID);

        recyclerListFriend = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerListFriend.setLayoutManager(linearLayoutManager);
        adapter = new ListPeopleAdapter(this, listFriend, isEditGroup, groupEdit);
        recyclerListFriend.setAdapter(adapter);
    }

    class ListPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Group> listGroup;
        private Context context;
        private ListFriend listFriend;
        private boolean isEdit;
        private Group editGroup;

        public ListPeopleAdapter(Context context, ListFriend listFriend, boolean isEdit, Group editGroup) {

            this.context = context;
            this.listFriend = listFriend;
            this.isEdit = isEdit;
            this.editGroup = editGroup;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_add_friend, parent, false);
            return new ItemFriendHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
             //final String name = listFriend.getListFriend().get(position).name;
            ((ItemFriendHolder) holder).txtName.setText(listFriend.getListFriend().get(position).name);
            ((ItemFriendHolder) holder).txtEmail.setText(listFriend.getListFriend().get(position).email);
            //((ItemFriendHolder) holder).txtEmail.setVisibility(View.INVISIBLE);
            String avata = listFriend.getListFriend().get(position).avata;
            final String id = listFriend.getListFriend().get(position).id;
            final String groupName=groupEdit.groupInfo.get("name");
            if (!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
                ((ItemFriendHolder) holder).avata.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            }else{
                ((ItemFriendHolder) holder).avata.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
            }
            if (isEdit && editGroup.member.contains(id)) {
                ((ItemFriendHolder) holder).checkBox.setChecked(true);
                ((ItemFriendHolder) holder).checkBox.isChecked();
            }else if(editGroup != null && !editGroup.member.contains(id)){
                //((ItemFriendHolder) holder).checkBox.setChecked(false);
                ((ItemFriendHolder) holder).checkBox.setVisibility(View.VISIBLE);
                ((ItemFriendHolder) holder).checkBox.setButtonDrawable(R.drawable.ic_near_me_black_24dp);
                /*((ItemFriendHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Intent intent = new Intent(MembersList.this,ChatActivity.class);
                        intent.putExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND, name);
                        ArrayList<CharSequence> idFriend = new ArrayList<>();
                        ChatActivity.bitmapAvataFriend = new HashMap<>();
                        for(String id : listGroup.get(position).member) {
                            idFriend.add(id);
                            String avata = listFriend.getAvataById(id);
                            if(!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
                                ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            }else if(avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                                ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
                            }else {
                                ChatActivity.bitmapAvataFriend.put(id, null);
                            }
                        }
                        intent.putCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID, idFriend);
                        //intent.putExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID, listGroup.get(position).id);
                        startActivity(intent);
                    }
                });*/
                ((ItemFriendHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listFriend == null){
                            listFriend = FriendDB.getInstance(context).getListFriend();
                        }
                        Intent intent = new Intent(context, ChatActivity.class);
                        intent.putExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND, groupName);
                        intent.putExtra("FriendId",listFriend.getListFriend().get(position).email);
                        intent.putExtra("from","memberslist");
                        ArrayList<CharSequence> idFriend = new ArrayList<>();
                        ChatActivity.bitmapAvataFriend = new HashMap<>();
                        for(String id : editGroup.member) {
                            idFriend.add(id);
                            String avata = listFriend.getAvataById(id);
                            if(!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                                byte[] decodedString = Base64.decode(avata, Base64.DEFAULT);
                                ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                            }else if(avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                                ChatActivity.bitmapAvataFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avata));
                            }else {
                                ChatActivity.bitmapAvataFriend.put(id, null);
                            }
                        }
                        intent.putCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID, idFriend);
                        intent.putExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID,editGroup.id);
                        context.startActivity(intent);
                    }
                });
            }
        }
        @Override
        public int getItemCount() {
            return listFriend.getListFriend().size();
        }
    }

    class ItemFriendHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtEmail;
        public CircleImageView avata;
        public CheckBox checkBox;

        public ItemFriendHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            avata = (CircleImageView) itemView.findViewById(R.id.icon_avata);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkAddPeople);
        }
    }
}
