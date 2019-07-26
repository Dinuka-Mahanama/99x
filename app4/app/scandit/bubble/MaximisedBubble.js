WIDTH = 200;
FULL_INDICATOR_HEIGHT = 104;
INDICATOR_WITHOUT_DELIVERY_HEIGHT = 82;

export default{
    MaximisedBubble(context,bubbleData) {
        this.bubbleData = bubbleData;

        if (!this.bubbleData.getDeliveryDate().isEmpty()) {
            deliveryVisibility = View.VISIBLE;
            backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.bg_transparent_black);
        } else {
            deliveryVisibility = View.GONE;
            backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.bg_rounded_bottom_black);
        }
    },
    getWidth() {
        return WIDTH;
    },
    getHeight() {
        if (!bubbleData.getDeliveryDate().isEmpty()) {
            return FULL_INDICATOR_HEIGHT;
        } else {
            return INDICATOR_WITHOUT_DELIVERY_HEIGHT;
        }
    },
    getView(context,inflater) {
        targetHeight = getHeight();
        view = inflater.inflate(R.layout.bubble_maximised, null);
        view.findViewById(R.id.top).setBackground(backgroundDrawable);

        view.findViewById(R.id.bottom).setVisibility(deliveryVisibility);

       // ((TextView) view.findViewById(R.id.delivery)).setText(bubbleData.getDeliveryDate());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.findViewById(R.id.header).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
            view.findViewById(R.id.bottom).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
            view.findViewById(R.id.triangle).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
        }

        view.setLayoutParams(new FrameLayout.LayoutParams(
                UiUtils.pxFromDp(context, WIDTH), UiUtils.pxFromDp(context, targetHeight)));

        return view;
    }

}