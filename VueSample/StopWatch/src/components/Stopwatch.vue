<template>
  <div class="stopwatch-wrapper">
    <h1 class="seconds">{{ seconds }}s</h1>

    <div class="stopwatch-controls">

      <button class="btn btn--start" @click="startTimer()" :class="{'running': running}">Start</button>
      <button class="btn btn--stop" @click="resetTimer()">Reset</button>
      
    </div>
    
  </div>
</template>

<script>
export default {
  name: 'Stopwatch',
  data () {
    return {
      elapsed: 0,
      timer: false,
      running: false,
      start: Date.now(),
      msg: 'Welcome to Your Vue.js App'
    }
  },
  computed: {
    seconds() {
      return (this.elapsed / 1000).toFixed(1);
    }
  },
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
      this.elapsed = 0;
      clearInterval(this.timer);
      this.running = false;
    }
  },
  created() {
    let vm = this;
    document.body.onkeyup = function(e){
      e.preventDefault();
      e.stopPropagation();
      if(e.keyCode == 32){
          if(vm.running){
            vm.resetTimer();
          } else {
            vm.startTimer();
          }
      }
    }
  },
  destroyed() {
    clearInterval(this.timer);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

$primary-color: #62ae85;
$border-color: darken($primary-color, 10%);

* {
  color: white;
}

h1, h2 {
  font-weight: normal;
  margin-top: 0;
}

.seconds {
  font-size: 4rem;
  font-weight: bold;
  padding: 2rem 5rem;
  display: inline-block;
  border: 1px solid $primary-color;
}

.stopwatch-controls {
  .btn {
    cursor: pointer;
    appearance: none;
    border: none;
    background-color: $primary-color;
    color: white;
    font-size: 1.4rem;
    padding: 1em 3em;
    border-radius: 4px;
    overflow: hidden;
    border-top: 2px solid lighten($primary-color, 20%);
    border-right: 1px solid lighten($primary-color, 20%);
    border-left: 1px solid $border-color;
    border-bottom: .5rem solid $border-color;
    transform: translate3d(0,-10px,0);
    transition: all 0.25s ease-out;
    will-change: transform;
    font-weight: bold;
    &:focus,
    &:hover {
      outline: none;
      background-color: lighten($primary-color, 5%);
    }
    &.running {
      transform: translate3d(0,0px,0);
      border-bottom: .05rem solid $border-color;
    }
  }
}

</style>
