<template>
  <div class="extra">
    <div class="item" v-for="x in [...extras, newExtra]" :key="x.key">
      <label v-if="x !== newExtra" :for="'extra-'+x.key">{{ x.key }}</label>
      <input v-else type="text" v-model.lazy="x.key" />
      <input :id="'extra-'+x.key" type="text" v-model.lazy="x.value" />
      <select v-model="x.linkId">
        <option v-for="exhibit in exhibits" :key="exhibit.id" :value="exhibit.id">
          {{ exhibit.name }}
        </option>
      </select>
      <button v-if="x !== newExtra" @click="remove(x)">REMOVE</button>
      <button v-else @click="add">ADD</button>
    </div>
    <p class="error">{{ errorMsg }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue";
import dataService from "../services/DataService";
import { Exhibit, ExhibitPair } from "@/models/Exhibit";

export default defineComponent({
  props: {
    modelValue: {
      type: Object as PropType<ExhibitPair[]>,
      required: true,
    },
  },
  emits: ["update:modelValue"],
  data() {
    return {
      newExtra: new ExhibitPair(),
      errorMsg: "",
      exhibits: [] as Exhibit[],
      extras: this.modelValue,
    };
  },
  async beforeMount() {
    this.exhibits = await dataService.getAll();
    this.exhibits = [new Exhibit(), ...this.exhibits]
  },
  methods: {
    add() {
      if (!this.newExtra.key || !this.newExtra.value) this.errorMsg = "Please fill in both fields";
      else if (this.extras.some(item => item.key === this.newExtra.key)) this.errorMsg = "An item with this key already exists";
      else {
        this.extras.push(this.newExtra);
        this.$emit("update:modelValue", this.extras);
        this.newExtra = new ExhibitPair();
        this.errorMsg = "";
      }
    },
    remove(extra: ExhibitPair) {
      this.extras = this.extras.filter(item => item.key !== extra.key);
      this.$emit("update:modelValue", this.extras);
    },
  },
});
</script>
  
<style lang="scss" scoped>
.extra {
  display: flex;
  flex-direction: column;
  min-width: 20em;
}

.item {
  gap: 1em;
  align-items: center;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
}

input {
  min-width: 0;
}

select {
  min-width: 0;
}

label:hover {
  cursor: pointer;
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

.error {
  color: red;
}
</style>
  