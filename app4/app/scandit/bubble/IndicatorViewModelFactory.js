let context 

export default{
    IndicatorViewModelFactory(contextt) {
        context = contextt;
    },
    createBubble(indicatorState, barcode) {
        barcodeData =barcode.getData();

        switch (indicatorState) {
            case MINIMISED:
                return new MinimisedBubble(context, barcodeData);
            case MAXIMISED:
                return new MaximisedBubble(context, barcodeData);
            default:
                return new NoBubble(context, barcodeData);
        }
    },
    mockBubbleDataObject(data) {
        list = [];
        list.add(data);

        return BubbleData(list);
    }

}