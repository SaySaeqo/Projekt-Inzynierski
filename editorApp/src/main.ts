import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "./styles/main.scss";
import firebaseApp from "@/initfirestore";
import { getAuth } from "firebase/auth";
import store from "./store";

let app = false;
const auth = getAuth(firebaseApp);

auth.onAuthStateChanged((_) => {
  if (!app) {
    app = true;
    store.commit(
      "setUsername",
      auth.currentUser?.displayName || auth.currentUser?.email
    );
    createApp(App).use(store).use(router).mount("#app");
  }
});
