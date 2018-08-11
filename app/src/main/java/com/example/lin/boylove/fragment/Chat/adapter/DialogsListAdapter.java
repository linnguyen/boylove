package com.example.lin.boylove.fragment.Chat.adapter;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lin.boylove.R;
import com.example.lin.boylove.custom.commons.ViewHolder;
import com.example.lin.boylove.custom.commons.models.IMessage;
import com.example.lin.boylove.utilities.DateFormatter;
import com.example.lin.boylove.utilities.GlideUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@SuppressWarnings("WeakerAccess")
public class DialogsListAdapter<DIALOG extends IChatRoom>
        extends RecyclerView.Adapter<DialogsListAdapter.BaseDialogViewHolder> {

    private List<DIALOG> items = new ArrayList<>();
    private int itemLayoutId;
    private GlideUtils.ImageLoader imageLoader;
    private Class<? extends BaseDialogViewHolder> holderClass;
    private OnDialogClickListener<DIALOG> onDialogClickListener;
    private OnDialogViewClickListener<DIALOG> onDialogViewClickListener;
    private OnDialogLongClickListener<DIALOG> onLongItemClickListener;
    private OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener;
    private DialogListStyle dialogStyle;
    private DateFormatter.Formatter datesFormatter;

    public DialogsListAdapter(GlideUtils.ImageLoader imageLoader) {
        this(R.layout.item_chat_room, DialogViewHolder.class, imageLoader);
    }


//    public DialogsListAdapter(@LayoutRes int itemLayoutId, Context context) {
//        this(itemLayoutId, DialogViewHolder.class, context);
//    }

    public DialogsListAdapter(@LayoutRes int itemLayoutId,
                              Class<? extends BaseDialogViewHolder> holderClass,
                              GlideUtils.ImageLoader imageLoader) {
        this.itemLayoutId = itemLayoutId;
        this.holderClass = holderClass;
        this.imageLoader = imageLoader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseDialogViewHolder holder, int position) {
        holder.setImageLoader(imageLoader);
        holder.setOnDialogClickListener(onDialogClickListener);
        holder.setOnDialogViewClickListener(onDialogViewClickListener);
        holder.setOnLongItemClickListener(onLongItemClickListener);
        holder.setOnDialogViewLongClickListener(onDialogViewLongClickListener);
        holder.setDatesFormatter(datesFormatter);
        holder.onBind(items.get(position));
    }

    @Override
    public BaseDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        //create view holder by reflation
        try {
            Constructor<? extends BaseDialogViewHolder> constructor = holderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            BaseDialogViewHolder baseDialogViewHolder = constructor.newInstance(v);
            if (baseDialogViewHolder instanceof DialogViewHolder) {
                ((DialogViewHolder) baseDialogViewHolder).setDialogStyle(dialogStyle);
            }
            return baseDialogViewHolder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return size of dialogs list
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * remove item with id
     *
     * @param id dialog i
     */
    public void deleteById(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    /**
     * Returns {@code true} if, and only if, dialogs count in adapter is non-zero.
     *
     * @return {@code true} if size is 0, otherwise {@code false}
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * clear dialogs list
     */
    public void clear() {
        if (items != null) {
            items.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Set dialogs list
     *
     * @param items dialogs list
     */
    public void setItems(List<DIALOG> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    /**
     * Add dialogs items
     *
     * @param newItems new dialogs list
     */
    public void addItems(List<DIALOG> newItems) {
        if (newItems != null) {
            if (items == null) {
                items = new ArrayList<>();
            }
            int curSize = items.size();
            items.addAll(newItems);
            notifyItemRangeInserted(curSize, items.size());
        }
    }

    /**
     * Add dialog to the end of dialogs list
     *
     * @param dialog dialog item
     */
    public void addItem(DIALOG dialog) {
        items.add(dialog);
        notifyItemInserted(0);
    }

    /**
     * Add dialog to dialogs list
     *
     * @param dialog   dialog item
     * @param position position in dialogs list
     */
    public void addItem(int position, DIALOG dialog) {
        items.add(position, dialog);
        notifyItemInserted(position);
    }

    /**
     * Update dialog by position in dialogs list
     *
     * @param position position in dialogs list
     * @param item     new dialog item
     */
    public void updateItem(int position, DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.set(position, item);
        notifyItemChanged(position);
    }

    /**
     * Update dialog by dialog id
     *
     * @param item new dialog item
     */
    public void updateItemById(DIALOG item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                items.set(i, item);
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * Update last message in dialog and swap item to top of list.
     *
     * @param dialogId Dialog ID
     * @param message  New message
     * @return false if dialog doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public boolean updateDialogWithMessage(int dialogId, IMessage message) {
        boolean dialogExist = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == dialogId) {
                items.get(i).setLastMessage(message);
                notifyItemChanged(i);
                if (i != 0) {
                    Collections.swap(items, i, 0);
                    notifyItemMoved(i, 0);
                }
                dialogExist = true;
                break;
            }
        }
        return dialogExist;
    }

    /**
     * Sort dialog by last message date
     */
    public void sortByLastMessageDate() {
        Collections.sort(items, new Comparator<DIALOG>() {
            @Override
            public int compare(DIALOG o1, DIALOG o2) {
                if (o1.getLastMessage().getCreatedAt().after(o2.getLastMessage().getCreatedAt())) {
                    return -1;
                } else if (o1.getLastMessage().getCreatedAt().before(o2.getLastMessage().getCreatedAt())) {
                    return 1;
                } else return 0;
            }
        });
        notifyDataSetChanged();
    }

    /**
     * Sort items with rules of comparator
     *
     * @param comparator Comparator
     */
    public void sort(Comparator<DIALOG> comparator) {
        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }

    /**
     * @return the item click callback.
     */
    public OnDialogClickListener getOnDialogClickListener() {
        return onDialogClickListener;
    }

    /**
     * Register a callback to be invoked when item is clicked.
     *
     * @param onDialogClickListener on click item callback
     */
    public void setOnDialogClickListener(OnDialogClickListener<DIALOG> onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    /**
     * @return the view click callback.
     */
    public OnDialogViewClickListener getOnDialogViewClickListener() {
        return onDialogViewClickListener;
    }

    /**
     * Register a callback to be invoked when dialog view is clicked.
     *
     * @param clickListener on click item callback
     */
    public void setOnDialogViewClickListener(OnDialogViewClickListener<DIALOG> clickListener) {
        this.onDialogViewClickListener = clickListener;
    }

    /**
     * @return on long click item callback
     */
    public OnDialogLongClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    /**
     * Register a callback to be invoked when item is long clicked.
     *
     * @param onLongItemClickListener on long click item callback
     */
    public void setOnDialogLongClickListener(OnDialogLongClickListener<DIALOG> onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    /**
     * @return on view long click callback
     */
    public OnDialogViewLongClickListener<DIALOG> getOnDialogViewLongClickListener() {
        return onDialogViewLongClickListener;
    }

    /**
     * Register a callback to be invoked when item view is long clicked.
     *
     * @param clickListener on long click item callback
     */
    public void setOnDialogViewLongClickListener(OnDialogViewLongClickListener<DIALOG> clickListener) {
        this.onDialogViewLongClickListener = clickListener;
    }

    /**
     * Sets custom {@link DateFormatter.Formatter} for text representation of last message date.
     */
    public void setDatesFormatter(DateFormatter.Formatter datesFormatter) {
        this.datesFormatter = datesFormatter;
    }

    //TODO ability to set style programmatically
    void setStyle(DialogListStyle dialogStyle) {
        this.dialogStyle = dialogStyle;
    }

    /*
    * LISTENERS
    * */
    public interface OnDialogClickListener<DIALOG extends IChatRoom> {
        void onDialogClick(DIALOG dialog);
    }

    public interface OnDialogViewClickListener<DIALOG extends IChatRoom> {
        void onDialogViewClick(View view, DIALOG dialog);
    }

    public interface OnDialogLongClickListener<DIALOG extends IChatRoom> {
        void onDialogLongClick(DIALOG dialog);
    }

    public interface OnDialogViewLongClickListener<DIALOG extends IChatRoom> {
        void onDialogViewLongClick(View view, DIALOG dialog);
    }

    /*
    * HOLDERS
    * */
    public abstract static class BaseDialogViewHolder<DIALOG extends IChatRoom>
            extends ViewHolder<DIALOG> {

        protected OnDialogClickListener<DIALOG> onDialogClickListener;
        protected OnDialogLongClickListener<DIALOG> onLongItemClickListener;
        protected OnDialogViewClickListener<DIALOG> onDialogViewClickListener;
        protected OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener;
        protected DateFormatter.Formatter datesFormatter;
        protected GlideUtils.ImageLoader imageLoader;

        public BaseDialogViewHolder(View itemView) {
            super(itemView);
        }

        protected void setOnDialogClickListener(OnDialogClickListener<DIALOG> onDialogClickListener) {
            this.onDialogClickListener = onDialogClickListener;
        }

        protected void setOnDialogViewClickListener(OnDialogViewClickListener<DIALOG> onDialogViewClickListener) {
            this.onDialogViewClickListener = onDialogViewClickListener;
        }

        protected void setOnLongItemClickListener(OnDialogLongClickListener<DIALOG> onLongItemClickListener) {
            this.onLongItemClickListener = onLongItemClickListener;
        }

        protected void setOnDialogViewLongClickListener(OnDialogViewLongClickListener<DIALOG> onDialogViewLongClickListener) {
            this.onDialogViewLongClickListener = onDialogViewLongClickListener;
        }

        public void setDatesFormatter(DateFormatter.Formatter dateHeadersFormatter) {
            this.datesFormatter = dateHeadersFormatter;
        }

        void setImageLoader(GlideUtils.ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
        }
    }

    public static class DialogViewHolder<DIALOG extends IChatRoom> extends BaseDialogViewHolder<DIALOG> {
        protected GlideUtils.ImageLoader imageLoader;
        protected DialogListStyle dialogStyle;
        protected ViewGroup container;
        protected ViewGroup root;
        protected TextView tvName;
        protected TextView tvDate;
        protected ImageView ivAvatar;
        protected ImageView ivLastMessageUser;
        protected TextView tvLastMessage;
        protected TextView tvBubble;
        protected ViewGroup dividerContainer;
        protected View divider;

        public DialogViewHolder(View itemView) {
            super(itemView);
            root = (ViewGroup) itemView.findViewById(R.id.dialogRootLayout);
            container = (ViewGroup) itemView.findViewById(R.id.dialogContainer);
            tvName = (TextView) itemView.findViewById(R.id.dialogName);
            tvDate = (TextView) itemView.findViewById(R.id.dialogDate);
            tvLastMessage = (TextView) itemView.findViewById(R.id.dialogLastMessage);
            tvBubble = (TextView) itemView.findViewById(R.id.dialogUnreadBubble);
            ivLastMessageUser = (ImageView) itemView.findViewById(R.id.dialogLastMessageUserAvatar);
            ivAvatar = (ImageView) itemView.findViewById(R.id.dialogAvatar);
            dividerContainer = (ViewGroup) itemView.findViewById(R.id.dialogDividerContainer);
            divider = itemView.findViewById(R.id.dialogDivider);

        }

        private void applyStyle() {
            if (dialogStyle != null) {
                //Texts
                if (tvName != null) {
                    tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogTitleTextSize());
                }

                if (tvLastMessage != null) {
                    tvLastMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogMessageTextSize());
                }

                if (tvDate != null) {
                    tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogDateSize());
                }

                //Divider
                if (divider != null)
                    divider.setBackgroundColor(dialogStyle.getDialogDividerColor());
                if (dividerContainer != null)
                    dividerContainer.setPadding(dialogStyle.getDialogDividerLeftPadding(), 0,
                            dialogStyle.getDialogDividerRightPadding(), 0);
                //Avatar
                if (ivAvatar != null) {
                    ivAvatar.getLayoutParams().width = dialogStyle.getDialogAvatarWidth();
                    ivAvatar.getLayoutParams().height = dialogStyle.getDialogAvatarHeight();
                }

                //Last message user avatar
                if (ivLastMessageUser != null) {
                    ivLastMessageUser.getLayoutParams().width = dialogStyle.getDialogMessageAvatarWidth();
                    ivLastMessageUser.getLayoutParams().height = dialogStyle.getDialogMessageAvatarHeight();
                }

                //Unread bubble
                if (tvBubble != null) {
                    GradientDrawable bgShape = (GradientDrawable) tvBubble.getBackground();
                    bgShape.setColor(dialogStyle.getDialogUnreadBubbleBackgroundColor());
                    tvBubble.setVisibility(dialogStyle.isDialogDividerEnabled() ? VISIBLE : GONE);
                    tvBubble.setTextSize(TypedValue.COMPLEX_UNIT_PX, dialogStyle.getDialogUnreadBubbleTextSize());
                    tvBubble.setTextColor(dialogStyle.getDialogUnreadBubbleTextColor());
                    tvBubble.setTypeface(tvBubble.getTypeface(), dialogStyle.getDialogUnreadBubbleTextStyle());
                }
            }
        }


        private void applyDefaultStyle() {
            if (dialogStyle != null) {
                if (root != null) {
                    root.setBackgroundColor(dialogStyle.getDialogItemBackground());
                }

                if (tvName != null) {
                    tvName.setTextColor(dialogStyle.getDialogTitleTextColor());
                    tvName.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogTitleTextStyle());
                }

                if (tvDate != null) {
                    tvDate.setTextColor(dialogStyle.getDialogDateColor());
                    tvDate.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogDateStyle());
                }

                if (tvLastMessage != null) {
                    tvLastMessage.setTextColor(dialogStyle.getDialogMessageTextColor());
                    tvLastMessage.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogMessageTextStyle());
                }
            }
        }

        private void applyUnreadStyle() {
            if (dialogStyle != null) {
                if (root != null) {
                    root.setBackgroundColor(dialogStyle.getDialogUnreadItemBackground());
                }

                if (tvName != null) {
                    tvName.setTextColor(dialogStyle.getDialogUnreadTitleTextColor());
                    tvName.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogUnreadTitleTextStyle());
                }

                if (tvDate != null) {
                    tvDate.setTextColor(dialogStyle.getDialogUnreadDateColor());
                    tvDate.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogUnreadDateStyle());
                }

                if (tvLastMessage != null) {
                    tvLastMessage.setTextColor(dialogStyle.getDialogUnreadMessageTextColor());
                    tvLastMessage.setTypeface(Typeface.DEFAULT, dialogStyle.getDialogUnreadMessageTextStyle());
                }
            }
        }


        @Override
        public void onBind(final DIALOG dialog) {
            if (dialog.getUnreadCount() > 0) {
                applyUnreadStyle();
            } else {
                applyDefaultStyle();
            }

            //Set Name
            tvName.setText(dialog.getDialogName());

            //Set Date
            String formattedDate = null;
            Date lastMessageDate = dialog.getLastMessage().getCreatedAt();
            if (datesFormatter != null) formattedDate = datesFormatter.format(lastMessageDate);
            tvDate.setText(formattedDate == null
                    ? getDateString(lastMessageDate)
                    : formattedDate);


            //Set Dialog avatar
            if (imageLoader != null) {
                imageLoader.loadImage(dialog.getDialogPhoto(), ivAvatar);
            }
//
//            //Set Last message user avatar
//            if (imageLoader != null) {
//                imageLoader.loadImage(ivLastMessageUser, dialog.getLastMessage().getUser().getAvatar());
//            }

//            ivLastMessageUser.setVisibility(dialogStyle.isDialogMessageAvatarEnabled()
//                    && dialog.getUsers().size() > 1 ? VISIBLE : GONE);

            //Set Last message text
            tvLastMessage.setText(dialog.getLastMessage().getText());

            //Set Unread message count bubble
            tvBubble.setText(String.valueOf(dialog.getUnreadCount()));
            tvBubble.setVisibility(dialogStyle.isDialogUnreadBubbleEnabled() &&
                    dialog.getUnreadCount() > 0 ? VISIBLE : GONE);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDialogClickListener != null) {
                        onDialogClickListener.onDialogClick(dialog);
                    }
                    if (onDialogViewClickListener != null) {
                        onDialogViewClickListener.onDialogViewClick(view, dialog);
                    }
                }
            });


            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onLongItemClickListener != null) {
                        onLongItemClickListener.onDialogLongClick(dialog);
                    }
                    if (onDialogViewLongClickListener != null) {
                        onDialogViewLongClickListener.onDialogViewLongClick(view, dialog);
                    }
                    return onLongItemClickListener != null || onDialogViewLongClickListener != null;
                }
            });
        }

        protected String getDateString(Date date) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        }

        protected DialogListStyle getDialogStyle() {
            return dialogStyle;
        }

        protected void setDialogStyle(DialogListStyle dialogStyle) {
            this.dialogStyle = dialogStyle;
            applyStyle();
        }
    }
}