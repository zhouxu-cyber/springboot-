<template>
  <div style="font-size: 60px;">
    <div>T1的中单是：{{ T1_mid }}</div>
    <div>T1的ADC是：{{ T1_Bot }}</div>
  </div>
  <router-view/>

</template>

<script>

import $ from 'jquery'
import { ref } from 'vue'


export default {
  name: "APP",
  setup: () => {
    let T1_mid = ref("");
    let T1_Bot = ref("");

    $.ajax({
      url:"http://localhost:3000/pk/getinfo/",
      type:"get",
      success: resp => {
        T1_Bot.value = resp.ADC,
        T1_mid.value = resp.mid
        
      }
    });

    return {
      T1_Bot,
      T1_mid 
    }

  }
}

</script>

<style>

 body {
  background-image: url("assets/background.png");
  background-size: 100%;
}

</style>
