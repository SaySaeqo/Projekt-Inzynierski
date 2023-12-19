<template>
    <div class="insert-img">
        <label :for="name">Insert {{ name }}:</label>
        <input :id="name" type="file" ref="input" @change="update" />
        <div class="img-container">
            <img class="main-img" :src="imageSrc" alt="" @click="clickInput" />
            <button :style="'display:'+(imageSrc?'block':'none')" @click="remove"><img src="../assets/bin.png" /></button>
        </div>
    </div>
</template>

<script lang="ts">
import { PropType, defineComponent } from "vue";

export default defineComponent({
    props: {
        file: {
            type: Object as PropType<File|null>,
            required: true,
        },
        removed: {
            type: Boolean,
            required: true,
        },
        name: {
            type: String,
            required: true,
        },
        src: String
    },
    emits: ["update:file", "update:removed"],
    data() {
        return {
            imageSrc: this.src || "",
        };
    },
    methods: {
        update(e: Event) {
            const file = (e.target as HTMLInputElement).files?.[0];
            if (file) {
                this.$emit("update:file", file);
                this.$emit("update:removed", false);
                this.imageSrc = URL.createObjectURL(file);
            }
        },
        clickInput() {
            (this.$refs["input"] as HTMLElement).click();
        },
        remove() {
            this.$emit("update:file", null);
            this.$emit("update:removed", true);
            this.imageSrc = "";
        }
    },
});
</script>

<style lang="scss" scoped>
.insert-img {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 1em;
  padding: 1em;
  border: 1px solid black;
}

.main-img {
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

.main-img:hover {
    cursor: pointer;
    opacity: 1;
}

.img-container {
    position: relative;
}

button {
    height: 3em;
    width: 3em;
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    right: 0;
    opacity: 0.7;

    img {
        height: 100%;
        width: 100%;
        object-fit: contain;
        box-sizing: border-box;
    }
}

button:hover {
    cursor: pointer;
    opacity: 1;
}
</style>