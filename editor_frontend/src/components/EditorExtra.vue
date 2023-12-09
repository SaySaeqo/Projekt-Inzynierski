<template>
    <div class="extra">
        <div class="item" v-for="x in extra" :key="x[0]">
        <p>{{ x[0] }}</p>
        <input type="text" v-model="x[1]" />
        <select v-model="selectedExhibit">
          <option v-for="exhibit in exhibits" :key="exhibit.id" :value="exhibit">
            {{ exhibit.name }}
          </option>
        </select>
        <div>
            <button @click="saveExtras(x[0], x[1])">SAVE</button>
            <button @click="removeExtras(x[0])">REMOVE</button>
        </div>
        </div>
        <div class="item">
            <input type="text" v-model="extra_key" />
            <input type="text" v-model="extra_value" />
            <select v-model="selectedExhibit">
              <option v-for="exhibit in exhibits" :key="exhibit.id" :value="exhibit">
                {{ exhibit.name }}
              </option>
            </select>
            <button class="add" @click="addExtras">ADD</button>
        </div>
        <p>{{ error_msg }}</p>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, PropType } from "vue";
  import BaseWidget from "./BaseWidget.vue";
  import dataService from "../services/DataService";
import { Exhibit } from "@/models/Exhibit";
  
  export default defineComponent({
    props: {
      extra: {
        type: Object as PropType<Map<string, string>>,
        required: true,
      },
    },
    components: {
      BaseWidget,
    },
    data() {
      return {
        extra_key: "",
        extra_value: "",
        error_msg: "",
        exhibits: [] as Exhibit[],
        selectedExhibit: "",
      };
    },
    created() {
      this.getExhibit();
    },
    methods: {
      async getExhibit() {
        this.exhibits = await dataService.getAll();
      },
      addExtras() {
        if (this.extra_key.length > 0 && this.extra_value.length > 0) {
          this.extra.set(this.extra_key, this.extra_value);
          this.extra_key = "";
          this.extra_value = "";
          this.selectedExhibit = "";
          this.error_msg = "";
        } else {
          this.error_msg = "Please fill in both fields";
        }
      },
      removeExtras(key: string) {
        this.extra.delete(key);
      },
      saveExtras(key: string, value: string) {
        this.extra.set(key, value);
      },
    },
  });
  </script>
  
  <style lang="scss" scoped>
  .extra {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    padding: 1em;
  
    .item {
      gap: 1em;
      align-items: center;
      display: grid;
      grid-template-columns: 1fr 1fr 1fr 2fr;
      max-width: 30em;

      input {
        max-width: 8em;
      }
      select {
        max-width: 10em;
      }
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
  