<template>
  <div id="app">
    <Stopwatch/>
  </div>
</template>

<script>
import Stopwatch from './components/Stopwatch'

export default {
  name: 'App',
  components: {
    Stopwatch
  }
}
</script>

<style lang="scss">
body {
  background-color: #1c1c1c;
  color: white;
}
* {
  -webkit-font-smoothing: antialiased;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;

  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;

  &:before {
    // position: absolute;
    // content: '';
    // display: block;
    // top: 50%;
    // left: 50%;
    // transform: translate3d(-50%,-50%,0);
    // width: 800px;
    // height: 800px;
    // // GO UCF KNIGHTS !!! 2018 NATIONAL CHAMPS BABY
    // background-image: url('http://business.ucf.edu/wp-content/uploads/sites/4/2017/08/UCF_Knightro_logo.png');
    // background-size: 100%;
    // background-repeat: no-repeat;
    // opacity: .0125;
    // z-index: 1;
  }

    .stopwatch-wrapper {
      position: relative;
      z-index: 10;
    }

}
</style>
