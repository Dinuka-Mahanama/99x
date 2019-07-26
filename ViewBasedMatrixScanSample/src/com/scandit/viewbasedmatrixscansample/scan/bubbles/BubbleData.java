package com.scandit.viewbasedmatrixscansample.scan.bubbles;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

public class BubbleData {

    private String code;

    private int stock;
    private int online;
    private String deliveryDate;

    public BubbleData(List<String> values) {
        code = values.get(0);
        stock = Integer.parseInt(values.get(1));
        online = Integer.parseInt(values.get(2));
        deliveryDate = values.get(3);
    }

    public String getCode() {
        return code;
    }

    public int getStock() {
        return stock;
    }

    public int getOnline() {
        return online;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }
}
