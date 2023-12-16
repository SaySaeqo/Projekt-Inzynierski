<template>
    <div class="insert-img">
          <label :for="name">Insert {{ name }}:</label>
          <input :id="name" type="file" ref="input" @change="update" />
          <img :src="imageSrc" alt="" @click="clickInput" />
        </div>
</template>

<script lang="ts">
import { PropType, defineComponent } from "vue";

export default defineComponent({
    props: {
        modelValue: {
            type: Object as PropType<File | null>,
            required: true,
        },
        name: {
            type: String,
            required: true,
        },
        src: String
    },
    emits: ["update:modelValue"],
    data() {
        return {
            imageSrc: this.src || "",
        };
    },
    methods: {
        update(e: Event) {
            const file = (e.target as HTMLInputElement).files?.[0];
            if (file) {
                this.$emit("update:modelValue", file);
                this.imageSrc = URL.createObjectURL(file);
            }
        },
        clickInput() {
            (this.$refs["input"] as HTMLElement).click();
        },
    },
});
</script>

<style scoped>
.insert-img {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 1em;
  padding: 1em;
  border: 1px solid black;
}

img {
    min-height: 10em;
    max-height: 15em;
    width: 20em;
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
</style>