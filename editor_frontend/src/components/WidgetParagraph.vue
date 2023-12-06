<template>
  <div class="container2">
    <div @click="edit" class="main">
      <div class="header">
        <img src="../assets/exampleParagraph.png" />
        <p>{{ title}}</p>
      </div>
      <p class="text">{{ text }}</p>
      
    </div>
    <div :class="'editor ' + hidden">
      <input type="text" v-model="title" />
      <textarea type="text" v-model="text" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit, Widget } from "@/models/Exhibit";

export default defineComponent({
  props: {
    exhibit: {
      type: Exhibit,
      required: true,
    },
    widget: {
      type: Widget,
      required: true,
    },
  },
  data() {
    return {
      icon: "",
      title: "",
      text: "",
      hidden: "hidden",
    };
  },
  beforeMount() {
    // get url from firebase
    this.icon = "img/exampleLink.png";
    // get title from firebase
    this.title = this.widget.data + "paragraph";
    // get link from firebase
    this.text = `Lorem ipsum dolor sit amet, consectetur adipiscing elit.
    Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium,
    totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.
    Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit,
    sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.
    Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit,
    sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.
    Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam,
    nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur,
    vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?`;
  },
  methods: {
    edit() {
      if (this.hidden == "hidden") this.hidden = "";
      else this.hidden = "hidden";
    },
  },
});
</script>

<style lang="scss" scoped>
.main {
  display: flex;
  flex-direction: column;
  height: 17em;
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

.hidden {
  display: none;
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
