<template>
  <div class="container2">
    <div class="main">
      <img :src="gallery[0] || ''" class="big" />
      <div class="gallery">
        <img src="../assets/plus.png" @click="add" class="plus" />
        <div class="item-wrapper" v-for="image in gallery" :key="image" @click="remove(image)">
          <img class="item" :src="image" />
          <img class="bin-icon" src="../assets/bin.png" />
        </div>
      </div>
      <input type="file" ref="fileInput" @change="handleFileUpload" style="display: none" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Exhibit, Widget } from "@/models/Exhibit";
import dataService from "../services/DataService";

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
      gallery: [] as string[],
      hidden: "hidden",
    };
  },
  beforeMount() {
    // get images from firebase
    console.log(this.widget.imagesURLs);
    this.widget.imagesURLs.forEach(async (el) => {
      this.gallery.push(await dataService.getImage(el));
    });
  },
  methods: {
    async remove(image: string) {
      if (await dataService.removeImage(image)) {
        this.widget.removeImageURL(dataService.getNameFromLink(image) || "");
        this.gallery.splice(this.gallery.indexOf(image), 1);
        await dataService.update(this.exhibit);
      }
    },
    add() {
      (this.$refs.fileInput as HTMLInputElement).click();
    },
    handleFileUpload(event: Event) {
      const file = (event.target as HTMLInputElement).files?.[0];
      // Now you can do whatever you want with the file...
      // example
      if (file) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = async () => {
          const name = await dataService.addImage(this.exhibit.id, file);
          this.widget.addImageURL(name);
          await dataService.update(this.exhibit);
          const imageLink = await dataService.getImage(name);
          this.gallery.push(imageLink);
        };
      }
    },
  },
});
</script>

<style lang="scss" scoped>
.main {
  display: flex;
  flex-direction: column;
  height: 20em;
  padding: 1em;
  gap: 1em;
}

img {
  object-fit: contain;
  max-height: 3em;
}
.big {
  max-height: 15em;
  min-height: 15em;
}

.gallery {
  flex: 1;
  display: flex;
  overflow: auto;
  width: 22em;
}

.item-wrapper {
  position: relative;
  display: inline-block;
  max-width: 3em;
}

.item {
  display: block;
  transition: opacity 0.3s ease;
  object-fit: cover;
  object-position: center;
  width: 3em;
  height: 100%;
}

.bin-icon {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  object-fit: contain;
}

.item-wrapper:hover .item {
  opacity: 0.3;
}

.item-wrapper:hover .bin-icon {
  opacity: 1;
  cursor: pointer;
}

img.plus {
  opacity: 0.6;
  padding: 0.5em;
  box-sizing: border-box;
}

img.plus:hover {
  opacity: 1;
  cursor: pointer;
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
