import { createRouter, createWebHashHistory } from "vue-router";
import TheList from "@/components/TheList.vue";
import ExhibitEditor from "@/components/ExhibitEditor.vue";
import TheLogin from "@/components/TheLogin.vue";

export default createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      component: TheList,
    },
    {
      path: "/exhibit/:id",
      component: ExhibitEditor,
    },
    {
      path: "/login",
      component: TheLogin,
    },
  ],
});
