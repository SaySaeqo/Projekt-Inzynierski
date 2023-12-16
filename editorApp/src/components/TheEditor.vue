<template>
  <main>
    <section class="top">
        <p>id: {{ exhibit.id }}</p>
        <div class="name">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="exhibit.name" class="name" />
        </div>
        <button @click="saveExhibit">Save</button>
    </section>
    <section class="bottom">
      <div class="tools">
        <button @click="consoleLog">Console log</button>
        <button @click="addGallery">Add Gallery</button>
        <button @click="addText">Add Text</button>
        <button @click="addLink">Add Link</button>
        <div class="insert-img">
          <label for="icon">Insert icon:</label>
          <input id="icon" type="file" ref="iconInput" @change="onIconChange" />
          <img :src="iconSrc" alt="" @click="triggerClick('iconInput')" />
        </div>
        <div class="insert-img">
          <label for="location">Insert location:</label>
          <input id="location" type="file" ref="locationInput" @change="onLocationChange" />
          <img :src="locationSrc" alt="" @click="triggerClick('locationInput')" />
        </div>
      </div>
      <div class="preview">
        <BaseWidget
          v-for="widget in exhibit.widgets"
          :key="widget.id"
          :exhibit="exhibit"
          :widget="widget"
          @up="moveWidgetUp(widget)"
          @down="moveWidgetDown(widget)"
          @remove="removeWidget(widget)"
        />
      </div>
      <EditorExtra v-model="exhibit.extra" v-if="exhibit.id" />
    </section>
  </main>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BaseWidget from "./BaseWidget.vue";
import { useRoute } from "vue-router";
import dataService from "../services/DataService";
import EditorExtra from "./EditorExtra.vue";
import { ExhibitPair, Widget, Exhibit } from "@/models/Exhibit";
export default defineComponent({
  components: {
    BaseWidget,
    EditorExtra,
  },
  data() {
    return {
      exhibit: new Exhibit(),
      generatedId: 0,
      extra_key: "",
      extra_value: "",
      error_msg: "",
      iconSrc: "",
      locationSrc: "",
    };
  },
  async beforeMount() {
    const id = useRoute().params.id as string;
    await this.getExhibit(id);
    this.iconSrc = await dataService.getImage(this.exhibit.icon).catch(() => {
      return "";
    });
    this.locationSrc = await dataService
      .getImage(this.exhibit.location)
      .catch(() => {
        return "";
      });
    this.generatedId = this.exhibit.widgets.length;
  },
  methods: {
    consoleLog() {
      console.log(this.exhibit);
    },
    addGallery() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "gallery"));
    },
    addText() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "text"));
    },
    addLink() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "link"));
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
      dataService.removeAllImages(widget);
    },
    async getExhibit(id: string) {
      this.exhibit = await dataService.getOne(id);
    },
    saveExhibit() {
      if (this.exhibit.name) {
        this.exhibit.description = "";
        dataService.update(this.exhibit);
      } else {
        this.error_msg = "Exhibit has to have a name";
      }
    },
    onIconChange(e: Event) {
      const file = (e.target as HTMLInputElement).files?.[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = async () => {
        const name = await dataService.updateImage(
          this.exhibit.id,
          file,
          this.iconSrc
        );
        this.exhibit.icon = name;
        await dataService.update(this.exhibit);
        this.iconSrc = await dataService.getImage(name);
      };
      reader.readAsDataURL(file);
    },
    onLocationChange(e: Event) {
      const file = (e.target as HTMLInputElement).files?.[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = async () => {
        const name = await dataService.updateImage(
          this.exhibit.id,
          file,
          this.locationSrc
        );
        this.exhibit.location = name;
        await dataService.update(this.exhibit);
        this.locationSrc = await dataService.getImage(name);
      };
      reader.readAsDataURL(file);
    },
    triggerClick(refName: string) {
      (this.$refs[refName] as HTMLElement).click();
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
  gap: 1em;
  padding: 1em;
}
section {
  display: grid;
}
.top {
  grid-template-columns: 1fr 4fr 1fr;
  justify-items: stretch;
}

.bottom {
  grid-template-columns: 2fr 3fr 2fr;
  justify-items: center;
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
.preview {
  justify-self: center;
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

.tools {
  display: flex;
  flex-direction: column;
  align-items: stretch;

  button {
  padding: 1em;
}

  .insert-img {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 1em;
  padding: 1em;
  border: 1px solid black;

  img {
    min-height: 10em;
    max-height: 15em;
    max-width: 20em;
    object-fit: contain;
    background-image: url("../assets/plus.png");
    background-size: auto;
    background-repeat: no-repeat;
    background-position: center;
    opacity: 0.7;
  }
  input {
    display: none;
  }

  img:hover {
    cursor: pointer;
    opacity: 1;
  }
}
}
</style>
