<template>
  <div class="container2">
    <div @click="hidden = !hidden" class="main">
      <img src="../assets/exampleLink.png" />
      <p>{{ widget.title}}</p>
    </div>
    <div class="editor" :style="(hidden?'display:none':'')">
      <input type="text" v-model="widget.title" placeholder="Title" />
      <input type="text" v-model="widget.data" placeholder="Link" />
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
}
img {
  justify-self: flex-start;
  height: 3em;
  padding: 1em;
  display: block;
  transition: opacity 0.3s ease;
  object-fit: cover;
  object-position: center;
  width: 3em;
  height: 100%;
}
p {
  display: flex;
  align-items: center;
  flex: 1;
  font-size: 1.2em;
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
