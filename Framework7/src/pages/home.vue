<template>
  <f7-page name="home">

    <!-- Right panel with reveal effect-->
    <f7-panel right reveal theme-dark>
    <f7-view>
      <f7-page>
        <f7-navbar title="Current Score"></f7-navbar>
        <f7-block>
          <div id="TimerDiv">
            Timer:
            <h1 class="seconds">{{ seconds }}s</h1>
          </div>
        </f7-block>
        <f7-block>
          <div id="RedDiv">
            Red Scans:
            <span id="RCount">5</span>
          </div>
        </f7-block>
        <f7-block>
          <div id="GreenDiv">
            Green Scans:
            <span id="GCount">05</span>
          </div>
        </f7-block>
      </f7-page>
    </f7-view>
  </f7-panel>

    <!-- Top Navbar -->
    <f7-navbar :sliding="false">
      
      <f7-nav-title style="">Scan To Win</f7-nav-title>
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    
    
    <f7-block strong>
          <f7-button fill raised id="ScaButton" @click="startTimer()">Start</f7-button>
    </f7-block>
    <f7-block strong>
          <f7-button fill raised id="ScanButton" @click="resetTimer()">Stop</f7-button>
    </f7-block>

    

  </f7-page>
</template>

<script>
  export default{
      data(){
        return{
          RCount: 0,
          GCount: 0,
          TimeFinal: 0,
          elapsed: 0,
          timer: false,
          running: false,
          start: Date.now(),
        };
      },
      GreenScanned(){
        GCount++
      },
      redScanned(){
        RCount++
      },
      //Timer
      methods: {
        tick() {
          this.elapsed = new Date() - this.start;
        },
        startTimer() {
          let vm = this;
          clearInterval(this.timer);
          this.start = Date.now();
          this.timer = setInterval(vm.tick, 50);
          this.running = true;
        },
        resetTimer() {
          this.TimeFinal=(this.elapsed / 1000).toFixed(1);
          this.elapsed = 0;
          clearInterval(this.timer);
          this.running = false;
        }
      },
      
      computed: {
        seconds() {
          return (this.elapsed / 1000).toFixed(1);
        }
      }
  }
</script>
