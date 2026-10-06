<template>
  <!-- <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view/> -->
  <titleBar></titleBar>
  <router-view></router-view>
  <endBar></endBar>
  <AiAssistant></AiAssistant>
</template>
<script>
import titleBar from '@/components/titleBar.vue';
import endBar from '@/components/endBar.vue'
import AiAssistant from '@/components/AiAssistant.vue'

export default {
  components: {
    titleBar,
    endBar,
    AiAssistant
  }
}
</script>
<style>
body{
  margin: 0;
}

@import "./assets/css/main.css";
@import "./assets/css/color-dark.css";

</style>
