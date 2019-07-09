<template>
  <view class="container">
    <text class="text-color-primary">My Vue Native App</text>
  </view>
  
  <block strong>
      <button fill raised id="ScaButton" @click="startTimer()">Start</button>
  </block>
  <block strong>
      <button fill raised id="ScanButton" @click="resetTimer()">Stop</button>
  </block>
</template>
 
<style>
.container {
  background-color: white;
  align-items: center;
  justify-content: center;
  flex: 1;
}
.text-color-primary {
  color: blue;
}
</style>
