<template>
  <Page>
    <ActionBar title="BarCode Scan Game" />
    <StackLayout>
      <Button text="Normal Scan" @tap="scan"/>
      <Button text="Matrix Scan" @tap="scanMatrix"/>
    </StackLayout>
  </Page>
</template>

<script >
import scandit2 from "../scandit";

export default {
  data() {
    return {
      msg: "Hello World!"
    };
  },
  methods: {
    scan() {
      console.log("Scandit Called");
      scandit2.init();
    },
    scanMatrix(){
      console.log("Scandit Matrix Called");
      scandit2.mscan();
    }
  }
};
</script>

<style scoped>
ActionBar {
  background-color: #53ba82;
  color: #ffffff;
}

.message {
  vertical-align: center;
  text-align: center;
  font-size: 20;
  color: #333333;
}
</style>
