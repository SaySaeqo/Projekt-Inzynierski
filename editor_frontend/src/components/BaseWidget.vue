<template>
  <div class="container">
    <WidgetGallery v-if="widget.type === 'gallery'" :exhibit="exhibit" :data="widget.data" />
    <WidgetSection v-if="widget.type === 'paragraph'" :exhibit="exhibit" :data="widget.data" />
    <WidgetLink v-if="widget.type === 'link'" :exhibit="exhibit" :data="widget.data" />
    <div class="control">
        <button @click="up">UP</button>
        <button @click="down">DOWN</button>
        <button @click="remove">REMOVE</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Exhibit, Widget } from "@/models/Exhibit";
import { defineComponent } from "vue";
import WidgetGallery from "./WidgetGallery.vue";
import WidgetSection from "./WidgetSection.vue";
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
    components: { WidgetGallery, WidgetSection, WidgetLink },
    methods: {
        up() {
            this.$emit("up");
        },
        down() {
            this.$emit("down");
        },
        remove() {
            this.$emit("remove");
        },
    },
});
</script>

<style lang="scss" scoped>

WidgetGallery, WidgetSection, WidgetLink {
    flex: 1;
}
.control {
    display: flex;
    flex-direction: column;
    align-self: stretch;
    flex-basis: auto;
}

.container {
    display: flex;
    flex: 1;
}
</style>
