<template>
  <div>
    <p>gallery</p>
  </div>
</template>

<script lang="ts">
import { Exhibit } from "@/models/Exhibit";
import { defineComponent } from "vue";
import WidgetControl from "./WidgetControl.vue";

export default defineComponent({
    props: {
        exhibit: {
            type: Exhibit,
            required: true,
        },
        data: {
            type: String,
            required: true,
        },
    },
    components: { WidgetControl },
    methods: {
        up() {
            this.$emit("up");
        },
        down() {
            this.$emit("down");
        },
    },
});
</script>

<style lang="scss" scoped>
div {
  height: 20em;
  border: 1px solid black;
  display: flex;
  justify-content: center;
  align-items: center;
}
p{
  flex: 1;
}
.end {
  align-self: flex-end;
}
</style>
