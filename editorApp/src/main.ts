import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "./styles/main.scss";
import firebaseApp from "@/initfirestore";
import { getAuth } from "firebase/auth";

let app = false;
const auth = getAuth(firebaseApp);

auth.onAuthStateChanged((_) => {
  if (!app) {
    app = true;
    createApp(App).use(router).mount("#app");
  }
});
