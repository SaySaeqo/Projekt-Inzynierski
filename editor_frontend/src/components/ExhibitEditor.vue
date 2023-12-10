<template>
  <main>
    <section>
      <ul>
        <p>id: {{ exhibit.id }}</p>
        <div class="name">
          <label for="name">Name:</label>
          <input id="name" type="text" v-model="exhibit.name" class="name" />
        </div>
        <button @click="saveExhibit">Save</button>
      </ul>
    </section>
    <section>
      <div class="tools">
        <button @click="addGallery">Add Gallery</button>
        <button @click="addParagraph">Add Paragraph</button>
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
      <EditorExtra :exhibit="exhibit" />
    </section>
  </main>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit, Widget } from "@/models/Exhibit";
import BaseWidget from "./BaseWidget.vue";
import { useRoute } from "vue-router";
import dataService from "../services/DataService";
import EditorExtra from "./EditorExtra.vue";

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
  async created() {
    await this.getExhibit(useRoute().params.id as string);
    this.iconSrc = await dataService.getImage(this.exhibit.icon).catch(() => {
      return "";
    });
    this.locationSrc = await dataService
      .getImage(this.exhibit.location)
      .catch(() => {
        return "";
      });
  },
  beforeMount() {
    let id = useRoute().params.id;
    // this.exhibit = fromDb(id);
    this.exhibit.id = id as string;
    this.generatedId = this.exhibit.widgets.length;
  },
  methods: {
    addGallery() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "gallery"));
    },
    addParagraph() {
      this.exhibit.widgets.push(new Widget(this.generatedId++, "paragraph"));
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

.tools {
  display: flex;
  flex-direction: column;
  align-items: stretch;
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
    width: 20em;
    min-height: 10em;
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
</style>
