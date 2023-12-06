<template>
  <div class="container">
    <WidgetGallery v-if="widget.type === 'gallery'" :exhibit="exhibit" :widget="widget" />
    <WidgetParagraph v-if="widget.type === 'paragraph'" :exhibit="exhibit" :widget="widget" />
    <WidgetLink v-if="widget.type === 'link'" :exhibit="exhibit" :widget="widget" />
    <div class="control">
        <button @click="$emit('up')">UP</button>
        <button @click="$emit('down')">DOWN</button>
        <button @click="$emit('remove')">REMOVE</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Exhibit, Widget } from "@/models/Exhibit";
import { defineComponent } from "vue";
import WidgetGallery from "./WidgetGallery.vue";
import WidgetParagraph from "./WidgetParagraph.vue";
import WidgetLink from "./WidgetLink.vue";

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
    components: { WidgetGallery, WidgetParagraph, WidgetLink },
});
</script>

<style lang="scss" scoped>


.control {
    display: flex;
    flex-direction: column;
    align-self: stretch;
    flex-basis: auto;
}

.container {
    display: flex;
}
</style>
