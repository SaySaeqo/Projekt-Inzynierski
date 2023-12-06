<template>
  <main>
    <section>
      <ul>
        <p>id: {{ exhibit.id }}</p>
        <div class="name">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="exhibit.name" class="name" />
        </div>
        <button>Save</button>
      </ul>
    </section>
    <section>
      <div class="tools">
        <h1>Tools:</h1>
        <button @click="addGallery">Add Gallery</button>
        <button @click="addParagraph">Add Paragraph</button>
        <button @click="addLink">Add Link</button>
      </div>
      <div class="preview">
        <BaseWidget v-for="widget in exhibit.widgets" :key="widget.data"
          :exhibit="exhibit" :widget="widget"
          @up="moveWidgetUp(widget)"
          @down="moveWidgetDown(widget)" 
          @remove="removeWidget(widget)" />
      </div>
      <div class="extra">
        <div class="item" v-for="extra in exhibit.extra" :key="extra[0]">
          <p>{{ extra[0] }}</p>
          <input type="text" v-model="extra[1]" />
          <div>
            <button @click="saveExtras(extra[0], extra[1])">SAVE</button>

            <button @click="removeExtras(extra[0])" >REMOVE</button>
          </div>
        </div>
        <div class="item">
          <input type="text" v-model="extra_key" />
          <input type="text" v-model="extra_value" />
          <button class="add" @click="addExtras">ADD</button>
        </div>
        <p>{{ error_msg }}</p>

      </div>
    </section>
  </main>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import {Exhibit, Widget} from "@/models/Exhibit";
import BaseWidget from "./BaseWidget.vue";
import { useRoute } from "vue-router";
import dataService from "../services/DataService.js";

export default defineComponent({
  components: {
    BaseWidget,
},
  data() {
    return {
      exhibit: new Exhibit(),
      generatedId: 0,
      extra_key: "",
      extra_value: "",
      error_msg: "",
    };
  },
  created() {
    this.getExhibit(useRoute().params.id);
  },
  beforeMount() {
    let id = useRoute().params.id;
    // this.exhibit = fromDb(id);
    this.exhibit.id = id as string;
    this.generatedId = this.exhibit.widgets.length;
  },
  methods: {
    addGallery() {
      this.exhibit.widgets.push({
        id: this.generatedId++,
        type: "gallery",
        data: ""
      });
    },
    addParagraph() {
      this.exhibit.widgets.push({
        id: this.generatedId++,
        type: "paragraph",
        data: ""
      });
    },
    addLink() {
      this.exhibit.widgets.push({
        id: this.generatedId++,
        type: "link",
        data: ""
      });
    },
    moveWidgetUp(widget: Widget) {
      let index = this.exhibit.widgets.indexOf(widget);
      if (index > 0) {
        this.exhibit.widgets.splice(index, 1);
        this.exhibit.widgets.splice(index - 1, 0, widget);
      }
    },
    moveWidgetDown(widget: Widget) {
      let index = this.exhibit.widgets.indexOf(widget);
      if (index < this.exhibit.widgets.length - 1) {
        this.exhibit.widgets.splice(index, 1);
        this.exhibit.widgets.splice(index + 1, 0, widget);
      }
    },
    removeWidget(widget: Widget) {
      let index = this.exhibit.widgets.indexOf(widget);
      this.exhibit.widgets.splice(index, 1);
    },
    async getExhibit(id) {
      let docData = await dataService.getOne(id);
      this.exhibit.id = id;
      this.exhibit.name = docData.name;
      this.exhibit.description = docData.description;
    },
    addExtras() {
      if (this.extra_key.length > 0 && this.extra_value.length > 0) {
        this.exhibit.extra.set(this.extra_key, this.extra_value);
        this.extra_key = "";
        this.extra_value = "";
        this.error_msg = "";
      } else {
        this.error_msg = "Please fill in both fields";
      }
    },
    removeExtras(key: string) {
      this.exhibit.extra.delete(key);
    },
    saveExtras(key: string, value: string) {
      this.exhibit.extra.set(key, value);
    },
  },

});
</script>

<style lang="scss" scoped>

main {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  border: 1px solid black;
}
section {
  display: flex;
  justify-content: center;
}
ul {
  flex: 1;
  display: flex;
  margin: 0;
  padding: 0;
  list-style: none;
}
p {
  margin: 0;
  padding: 1em;
  text-align: center;
}

.name {
  display: flex;
  flex: 1;
  align-items: center;
  padding: 0 1.5em;
  gap: 1em;

  label {
    font-weight: bold;
  }
  input {
    flex: 1;
    align-self: stretch;
    font-size: 1.2em;
  }
}

button {
  padding: 1em;
}
.preview {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 1em;
  align-items: stretch;
  border: 1px solid black;
  padding: 1em;
  width: 30em;
  height: 40em;
  overflow: auto;
}

.tools, .extra {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  padding: 1em;

  .item {
    gap: 1em;
    align-items: center;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
  }

  .add {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 1em;
    border: 1px solid black;
  }

  .add:hover {
    background-color: #f0f0f0;
    cursor: pointer;
  }
}
</style>
