let bubbleData
export default{
    MinimisedBubble(context,bubbleDataa) {
        bubbleData = bubbleDataa;
    },
    getWidth() {
        return WIDTH;
    },
    getHeight() {
        return HEIGHT;
    },
    getView(context,inflater) {
        let view = inflater.inflate(R.layout.bubble_minimised, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.findViewById(R.id.header).setBackgroundTintList(ColorStateList.valueOf(highlightColor));
        }
        //((TextView) view.findViewById(R.id.stock)).setText(String.valueOf(bubbleData.getStock()));
        view.setLayoutParams(new FrameLayout.LayoutParams(
                UiUtils.pxFromDp(context, WIDTH), UiUtils.pxFromDp(context, HEIGHT)));
        return view;
    }

}