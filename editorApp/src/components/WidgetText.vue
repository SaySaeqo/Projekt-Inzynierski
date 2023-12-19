<template>
  <div class="container2">
    <div @click="hidden = !hidden" class="main">
      <div class="header">
        <img src="../assets/exampleText.png" />
        <p>{{ widget.title }}</p>
      </div>
      <p class="text">{{ widget.data }}</p>
      
    </div>
    <div class="editor" :style="(hidden?'display:none':'')">
      <input type="text" v-model="widget.title" placeholder="Title" />
      <textarea type="text" v-model="widget.data" placeholder="Text" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Widget } from "@/models/Exhibit";

export default defineComponent({
  props: {
    widget: {
      type: Widget,
      required: true,
    },
  },
  data() {
    return {
      hidden: true,
    };
  },
});
</script>

<style lang="scss" scoped>
.main {
  display: flex;
  flex-direction: column;
  height: 15em;
  padding: 1em;
  gap: 1em;
}

.header {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1em;
  img {
    object-fit: contain;
    height: 2em;
  }
  p {
    display: flex;
    flex: 1;
    font-size: 1.3em;
    margin: 0;
  }
}

.text {
  flex: 1;
  font-size: 1.2em;
  margin: 0;
  overflow: scroll;
}

.main:hover {
  background-color: #f0f0f0;
  cursor: pointer;
}
.editor {
  display: flex;
  flex-direction: column;
}

.container2 {
  display: flex;
  flex-direction: column;
  border: 1px solid black;
  flex: 1;
  justify-content: flex-start;
  align-items: stretch;
  height: fit-content;
}
</style>
